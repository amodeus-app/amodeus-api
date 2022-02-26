__all__ = [
    "Modeus",
]

import urllib.parse
from datetime import datetime, timedelta
from functools import reduce
from typing import Any
from uuid import UUID

from httpx import Response

from .credentials import ModeusCredentials
from .models import SearchEventsResult, SearchPersonResult
from .models.get_team_result import GetTeamResult


class Modeus:
    """Object-oriented wrapper for MODEUS requests"""

    def __init__(self, credentials: ModeusCredentials):
        self._credentials = credentials

    @property
    def credentials(self) -> ModeusCredentials:
        return self._credentials

    async def get_event_team(
        self,
        event_id: str | UUID,
    ) -> GetTeamResult:
        resp = await self._timetable_api_request(
            "GET",
            "calendar",
            "events",
            str(event_id),
            "team",
        )
        return GetTeamResult.parse_obj(resp.json())

    async def get_events(
        self,
        person: str | UUID,
        start: datetime,
        end: timedelta | datetime = timedelta(days=7),
        *,
        size: int = 500,
    ) -> SearchEventsResult:
        if isinstance(end, timedelta):
            end = start + end
        resp = await self._timetable_api_request(
            "POST",
            "calendar",
            "events",
            "search",
            json={
                "attendeePersonId": [str(person)],
                "size": size,
                "timeMin": start.isoformat() + "Z",
                "timeMax": end.isoformat() + "Z",
            },
        )
        return SearchEventsResult.parse_obj(resp.json())

    async def search_person_by_id(
        self,
        uuid: str | UUID,
        *,
        page: int = 0,
        size: int = 25,
    ) -> SearchPersonResult:
        resp = await self._timetable_api_request(
            "POST",
            "people",
            "persons",
            "search",
            json={"id": [str(uuid)], "page": page, "size": size, "sort": "+fullName"},
        )
        return SearchPersonResult.parse_obj(resp.json())

    async def search_person_by_name(
        self,
        query: str,
        *,
        page: int = 0,
        size: int = 25,
    ) -> SearchPersonResult:
        resp = await self._timetable_api_request(
            "POST",
            "people",
            "persons",
            "search",
            json={"fullName": query, "page": page, "size": size, "sort": "+fullName"},
        )
        return SearchPersonResult.parse_obj(resp.json())

    async def _timetable_api_request(
        self,
        method: str,
        *path_segments: str,
        **kwargs: Any,
    ) -> Response:
        return await self._make_request(
            method,
            "schedule-calendar-v2",
            "api",
            *path_segments,
            **kwargs,
        )

    async def _make_request(
        self,
        method: str,
        *path_segments: str,
        **kwargs: Any,
    ) -> Response:
        path = reduce(urllib.parse.urljoin, map(lambda s: s + "/", path_segments), "/")
        resp = await self._credentials.session.request(
            method=method,
            url=path,
            headers={"Authorization": f"Bearer {self._credentials.token}"},
            **kwargs,
        )
        resp.raise_for_status()
        return resp
