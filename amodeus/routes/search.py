__all__ = [
    "router",
]

from uuid import UUID

from fastapi import APIRouter, Depends, HTTPException

from ..dependencies import default_modeus
from ..models import Person
from ..parsers import parse_people
from ..upstream.requests import Modeus

router = APIRouter()


@router.get("/search", response_model=list[Person], operation_id="search")
async def search(
    person_name: str,
    client: Modeus = Depends(default_modeus),
) -> list[Person]:
    people = await client.search_person_by_name(person_name)
    return parse_people(people)


@router.get("/person/{uuid}", response_model=Person, operation_id="get_person")
async def get_person(
    uuid: UUID,
    client: Modeus = Depends(default_modeus),
) -> Person:
    people = await client.search_person_by_id(uuid)
    res = parse_people(people)
    if len(res) > 0:
        return res[0]
    raise HTTPException(status_code=404, detail="No users found")
