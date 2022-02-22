__all__ = [
    "router",
]

from datetime import datetime, timedelta, timezone
from typing import Optional, Union
from uuid import UUID

from fastapi import APIRouter, Depends, Query

from ..dependencies import default_modeus, modeus
from ..models import Person, TimetableElement
from ..parsers import parse_event_team, parse_events
from ..upstream.requests import Modeus

router = APIRouter()


async def actual_get_timetable(
    uuid: Union[UUID, str],
    from_: Optional[datetime] = Query(None, alias="from"),
    to: Optional[datetime] = Query(None),
    client: Modeus = Depends(default_modeus),
) -> list[TimetableElement]:
    today = datetime.utcnow().replace(hour=0, minute=0, second=0, microsecond=0)
    if from_ is None:
        from_ = today - timedelta(days=today.weekday())
    if to is None:
        to = from_ + timedelta(days=7)
    from_ = from_.astimezone(timezone(timedelta(hours=5))).replace(tzinfo=None)
    to = to.astimezone(timezone(timedelta(hours=5))).replace(tzinfo=None)
    events = await client.get_events(uuid, from_, to)
    return parse_events(events)


@router.get("/timetable", response_model=list[TimetableElement], operation_id="get_my_timetable")
async def get_timetable(
    from_: Optional[datetime] = Query(None, alias="from"),
    to: Optional[datetime] = Query(None),
    client: Modeus = Depends(modeus),
) -> list[TimetableElement]:
    return await actual_get_timetable(client.credentials.user_id, from_, to, client)


@router.get(
    "/person/{uuid}/timetable",
    response_model=list[TimetableElement],
    operation_id="get_person_timetable",
)
async def get_person_timetable(
    uuid: UUID,
    from_: Optional[datetime] = Query(None, alias="from"),
    to: Optional[datetime] = Query(None),
    client: Modeus = Depends(default_modeus),
) -> list[TimetableElement]:
    # TODO: rate-limit here
    return await actual_get_timetable(uuid, from_, to, client)


@router.get("/event/{event_id}/team", response_model=list[Person], operation_id="get_event_team")
async def get_event_team(
    event_id: UUID,
    client: Modeus = Depends(modeus),
) -> list[Person]:
    attendees = await client.get_event_team(event_id)
    return parse_event_team(attendees)
