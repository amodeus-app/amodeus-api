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
    # is_held: bool
