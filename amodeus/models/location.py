from pydantic import BaseModel

from .building import Building


class Location(BaseModel):
    building: Building | None
    room: str | None
    full: str
