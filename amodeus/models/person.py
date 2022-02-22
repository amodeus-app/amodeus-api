from typing import Optional

from amodeus.models.base import BaseIDModel


class Person(BaseIDModel):
    last_name: str
    first_name: str
    middle_name: Optional[str]
    full_name: str
