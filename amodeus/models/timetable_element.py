__all__ = ["TimetableElement"]

from datetime import datetime

from .base import BaseIDModel
from .lesson import Lesson
from .location import Location
from .person import Person


class TimetableElement(BaseIDModel):
    lesson: Lesson
    start: datetime
    end: datetime
    location: Location | None
    teachers: list[Person]
    team_name: str | None
    # is_held: bool
