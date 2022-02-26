__all__ = ["app"]

import logging
from asyncio import create_task, sleep

from fastapi import FastAPI, Request
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import JSONResponse
from httpx import HTTPStatusError

from .config import Config, load_config
from .routes import auth, search, timetable
from .upstream import ModeusCredentials

logging.basicConfig(level=logging.INFO)
_log = logging.getLogger(__name__)

app = FastAPI(
    title="AModeus",
    version="0.1.0-alpha.3",
    description="Alternative MODEUS API",
    servers=[
        {"url": "https://api.amodeus.evgfilim1.me", "description": "Production server"},
        {"url": "http://localhost:8000", "description": "Local development server"},
    ],
    openapi_tags=[
        {"name": "auth", "description": "Authentication"},
        {"name": "marks", "description": "[WIP] Marks and current results"},
        {"name": "search", "description": "Search people"},
        {"name": "timetable", "description": "Timetable"},
    ],
)


async def _update_creds(creds: ModeusCredentials, config: Config) -> None:
    _log.info("Task loop started")
    while True:
        await sleep(600)  # 10 minutes
        _log.debug("Tick")
        try:
            await creds.refresh_login()
        except Exception as e:
            _log.warning("Failed to update root creds, getting new ones", exc_info=e)
            await creds.relogin(
                config.root_account.login,
                config.root_account.password.get_secret_value(),
            )
        else:
            _log.debug("Creds updated")


@app.on_event("startup")
async def _root_login() -> None:
    config = load_config()
    app.state.config = config
    app.state.root_creds = await ModeusCredentials.login(
        config.root_account.login,
        config.root_account.password.get_secret_value(),
    )
    app.state.root_creds_update = create_task(_update_creds(app.state.root_creds, app.state.config))


@app.on_event("shutdown")
async def _stop_tasks() -> None:
    app.state.root_creds_update.cancel()


@app.exception_handler(HTTPStatusError)
async def on_upstream_request_failed(request: Request, exc: HTTPStatusError) -> JSONResponse:
    _log.exception("Upstream error", exc_info=exc, extra={"request": request})
    return JSONResponse({"detail": "Something wrong with MODEUS :/"}, status_code=500)


@app.exception_handler(500)
async def on_500(request: Request, exc: Exception) -> JSONResponse:
    _log.exception("Internal server error", exc_info=exc, extra={"request": request})
    return JSONResponse({"detail": "Internal server error"}, status_code=500)


app.include_router(auth.router, tags=["auth"])
app.include_router(search.router, tags=["search"])
app.include_router(timetable.router, tags=["timetable"])

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)
