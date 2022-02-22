__all__ = ["BaseIDModel"]

from uuid import UUID

from pydantic import BaseModel


class BaseIDModel(BaseModel):
    id: UUID
