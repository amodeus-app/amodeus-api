from typing import Annotated

from pydantic import Field

from .base import IDModeusModel


class LessonRealization(IDModeusModel):
    name: str
    name_short: Annotated[str, Field(alias="nameShort")]
    ordinal: int
