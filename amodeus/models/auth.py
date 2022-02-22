from typing import Literal

from pydantic import BaseModel


class Auth(BaseModel):
    access_token: str
    auth_id: str
    token_type: Literal["Bearer"] = "Bearer"
