from typing import Annotated

from pydantic import UUID4, Field

from .base import ModeusModel


class EventOrganizer(ModeusModel):
    event_id: Annotated[UUID4, Field(alias="eventId")]
