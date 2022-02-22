from typing import Annotated

from pydantic import Field

from .base import IDModeusModel


class CycleRealization(IDModeusModel):
    name: str
    name_short: Annotated[str, Field(alias="nameShort")]
    code: str
    course_unit_realization_name_short: Annotated[
        str,
        Field(alias="courseUnitRealizationNameShort"),
    ]
