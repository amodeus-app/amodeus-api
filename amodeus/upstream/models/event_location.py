from typing import Annotated, Optional

from pydantic import UUID4, Field

from .base import ModeusModel


class EventLocation(ModeusModel):
    event_id: Annotated[UUID4, Field(alias="eventId")]
    custom_location: Annotated[Optional[str], Field(alias="customLocation")]
