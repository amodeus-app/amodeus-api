__all__ = ["GetTeamResult"]

from typing import Annotated
from uuid import UUID

from pydantic import BaseModel, Field

from .event_attendee import EventAttendee
from .person import Person


class _Embedded(BaseModel):
    event_attendees: Annotated[
        list[EventAttendee],
        Field(alias="event-attendees"),
    ]
    persons: list[Person]


class GetTeamResult(BaseModel):
    result: Annotated[_Embedded, Field(alias="_embedded")]
    event_id: Annotated[UUID, Field(alias="eventId")]
    size: int
