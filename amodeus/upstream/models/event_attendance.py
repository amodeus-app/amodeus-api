from typing import Annotated

from pydantic import Field

from .base import IDModeusModel


class EventAttendance(IDModeusModel):
    result_id: Annotated[str, Field(alias="resultId")]
