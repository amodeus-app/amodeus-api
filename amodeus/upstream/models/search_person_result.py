from typing import Annotated

from pydantic import BaseModel, Field

from .page import Page
from .person import Person


class _Embedded(BaseModel):
    persons: list[Person]


class SearchPersonResult(BaseModel):
    result: Annotated[_Embedded, Field(alias="_embedded")] = _Embedded(persons=[])
    page: Page
