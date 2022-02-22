from typing import Optional

from pydantic import BaseModel

from .subject import Subject


class Lesson(BaseModel):
    subject: Subject
    name: str
    name_short: str
    description: Optional[str]
    type: str
    format: Optional[str]
