from datetime import datetime
from typing import Annotated, Optional

from pydantic import Field

from .base import IDModeusModel


class Event(IDModeusModel):
    name: str
    name_short: Annotated[str, Field(alias="nameShort")]
    description: Optional[str]
    start: datetime
    end: datetime
