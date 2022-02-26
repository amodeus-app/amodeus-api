from typing import AsyncIterable, cast

from fastapi import Depends, Request
from fastapi.security import OAuth2PasswordBearer

from ..upstream import ModeusCredentials

token = OAuth2PasswordBearer(tokenUrl="auth", description="Upstream authentication")


def default_credentials(request: Request) -> ModeusCredentials:
    return cast(ModeusCredentials, request.app.state.root_creds)


async def user_credentials(
    id_token: str = Depends(token),
) -> AsyncIterable[ModeusCredentials]:
    creds = ModeusCredentials(id_token, None)
    yield creds
    await creds.session.aclose()
