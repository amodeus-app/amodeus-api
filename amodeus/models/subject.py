__all__ = ["Subject"]

from .base import BaseIDModel


class Subject(BaseIDModel):
    name: str
    name_short: str
