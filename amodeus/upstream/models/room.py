from typing import Annotated

from pydantic import Field

from .base import IDModeusModel
from .building import Building


class Room(IDModeusModel):
    name: str
    name_short: Annotated[str, Field(alias="nameShort")]
    building: Building
    projector_available: Annotated[bool, Field(alias="projectorAvailable")]
    total_capacity: Annotated[int, Field(alias="totalCapacity")]
    working_capacity: Annotated[int, Field(alias="workingCapacity")]
