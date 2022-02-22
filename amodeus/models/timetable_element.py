from datetime import datetime
from typing import Optional

from .base import BaseIDModel
from .lesson import Lesson
from .location import Location
from .person import Person


class TimetableElement(BaseIDModel):
    lesson: Lesson
    start: datetime
    end: datetime
    location: Optional[Location]
    teachers: list[Person]
    # is_held: bool
