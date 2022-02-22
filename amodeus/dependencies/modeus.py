from fastapi import Depends, Request

from ..upstream import Modeus, ModeusCredentials
from .modeus_credentials import default_credentials, user_credentials


def default_modeus(request: Request) -> Modeus:
    return Modeus(default_credentials(request))


def modeus(creds: ModeusCredentials = Depends(user_credentials)) -> Modeus:
    return Modeus(creds)
