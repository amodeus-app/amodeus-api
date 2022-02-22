from typing import Optional

from pydantic import BaseModel

from .building import Building


class Location(BaseModel):
    building: Optional[Building]
    room: Optional[str]
    full: str
