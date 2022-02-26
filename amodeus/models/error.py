__all__ = ["Error"]

from pydantic import BaseModel


class Error(BaseModel):
    detail: str
