from typing import Annotated, Optional

from pydantic import Field

from .base import IDModeusModel


class Person(IDModeusModel):
    first_name: Annotated[str, Field(alias="firstName")]
    last_name: Annotated[str, Field(alias="lastName")]
    middle_name: Annotated[Optional[str], Field(alias="middleName")]
    full_name: Annotated[str, Field(alias="fullName")]
