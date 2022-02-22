from typing import Annotated

from pydantic import BaseModel, Field


class Building(BaseModel):
    name: str
    name_short: Annotated[str, Field(alias="nameShort")]
    address: str
    display_order: Annotated[int, Field(alias="displayOrder")]
