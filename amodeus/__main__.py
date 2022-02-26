from uvicorn import run  # type: ignore # https://github.com/encode/uvicorn/issues/998

from amodeus.app import app

run(app, host="0.0.0.0", log_config=None)
