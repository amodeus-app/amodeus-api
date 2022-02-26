from pydantic import BaseModel

from .subject import Subject


class Lesson(BaseModel):
    subject: Subject
    name: str
    name_short: str
    description: str | None
    type: str
    format: str | None
