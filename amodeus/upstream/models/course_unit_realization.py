from typing import Annotated

from pydantic import Field

from .base import IDModeusModel


class CourseUnitRealization(IDModeusModel):
    name: str
    name_short: Annotated[str, Field(alias="nameShort")]
