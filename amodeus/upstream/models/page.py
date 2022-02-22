from typing import Annotated

from pydantic import BaseModel, Field


class Page(BaseModel):
    size: int
    total_elements: Annotated[int, Field(alias="totalElements")]
    total_pages: Annotated[int, Field(alias="totalPages")]
    number: int
