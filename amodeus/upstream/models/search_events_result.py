__all__ = ["SearchEventsResult"]

from typing import Annotated

from pydantic import BaseModel, Field

from .building import Building
from .course_unit_realization import CourseUnitRealization
from .cycle_realization import CycleRealization
from .event import Event
from .event_attendance import EventAttendance
from .event_attendee import EventAttendee
from .event_location import EventLocation
from .event_organizer import EventOrganizer
from .event_room import EventRoom
from .event_team import EventTeam
from .lesson_realization import LessonRealization
from .lesson_realization_team import LessonRealizationTeam
from .page import Page
from .person import Person
from .room import Room


class _Embedded(BaseModel):
    events: Annotated[list[Event], Field(default_factory=list)]
    course_unit_realizations: Annotated[
        list[CourseUnitRealization],
        Field(alias="course-unit-realizations"),
    ]
    cycle_realizations: Annotated[
        list[CycleRealization],
        Field(alias="cycle-realizations"),
    ]
    lesson_realization_teams: Annotated[
        list[LessonRealizationTeam],
        Field(alias="lesson-realization-teams"),
    ]
    lesson_realizations: Annotated[
        list[LessonRealization],
        Field(alias="lesson-realizations"),
    ]
    event_locations: Annotated[
        list[EventLocation],
        Field(alias="event-locations"),
    ]
    event_rooms: Annotated[
        list[EventRoom],
        Field(alias="event-rooms"),
    ]
    rooms: list[Room]
    buildings: list[Building]
    event_teams: Annotated[
        list[EventTeam],
        Field(alias="event-teams"),
    ]
    event_organizers: Annotated[
        list[EventOrganizer],
        Field(alias="event-organizers"),
    ]
    event_attendances: Annotated[
        list[EventAttendance],
        Field(alias="event-attendances"),
    ]
    person_mid_check_results: Annotated[  # type: ignore[type-arg]
        list[dict],
        Field(alias="person-mid-check-results"),
    ]
    event_attendees: Annotated[
        list[EventAttendee],
        Field(alias="event-attendees"),
    ]
    persons: list[Person]


class SearchEventsResult(BaseModel):
    result: Annotated[_Embedded, Field(alias="_embedded")]
    page: Page
