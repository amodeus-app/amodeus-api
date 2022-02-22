from pydantic import BaseModel

from .person import Person


class Attendee(BaseModel):
    person: Person
    role: str
