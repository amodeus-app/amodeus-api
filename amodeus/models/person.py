from amodeus.models.base import BaseIDModel


class Person(BaseIDModel):
    last_name: str
    first_name: str
    middle_name: str | None
    full_name: str
