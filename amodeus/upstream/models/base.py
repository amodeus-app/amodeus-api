__all__ = ["IDModeusModel", "ModeusModel"]

from typing import Annotated, Any, cast
from uuid import UUID

from pydantic import BaseModel, Field


class ModeusModel(BaseModel):
    links: Annotated[dict[str, Any], Field(alias="_links")]

    def get_link(self, key: str, attr: str = "href") -> str | None:
        if isinstance(link := self.links.get(key), dict):
            if (href := cast(dict[str, str], link).get(attr)) is not None:
                return href.removeprefix("/")
        return None


class IDModeusModel(ModeusModel):
    id: UUID | str
