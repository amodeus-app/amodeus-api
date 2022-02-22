__all__ = [
    "router",
]

from fastapi import APIRouter, Depends, Header, HTTPException
from fastapi.security import OAuth2PasswordRequestForm

from ..dependencies import user_credentials
from ..exceptions import LoginFailed
from ..models.auth import Auth
from ..upstream import ModeusCredentials

router = APIRouter()


@router.post("/auth", response_model=Auth, operation_id="login")
async def login(data: OAuth2PasswordRequestForm = Depends()) -> Auth:
    try:
        creds = await ModeusCredentials.login(data.username, data.password)
    except LoginFailed as e:
        raise HTTPException(401, e.args[0]) from e
    return Auth(
        access_token=creds.token,
        auth_id=creds.common_auth_id,
    )


@router.post("/reauth", response_model=Auth, operation_id="reauth")
async def reauth(
    creds: ModeusCredentials = Depends(user_credentials),
    auth_id: str = Header(..., alias="X-AuthID"),
) -> Auth:
    creds.common_auth_id = auth_id
    await creds.refresh_login()
    return Auth(
        access_token=creds.token,
        auth_id=auth_id,
    )
