import re

from ..models import Building, Lesson, Location, Person, Subject, TimetableElement
from ..upstream.models.get_team_result import GetTeamResult
from ..upstream.models.search_events_result import SearchEventsResult
from .base import _lookup_object, _lookup_objects


def parse_events(resp: SearchEventsResult) -> list[TimetableElement]:
    events = []
    for event in resp.result.events:
        upstream_subject = _lookup_object(
            resp.result.course_unit_realizations,
            event.get_link("course-unit-realization"),
        )
        if upstream_subject is None:
            continue  # upstream bug
        upstream_location = _lookup_object(
            resp.result.event_locations,
            event.id,
            "event_id",
        )
        full_location = upstream_location.custom_location
        building, room = None, None
        if full_location is None:
            event_room = _lookup_object(
                resp.result.event_rooms,
                f"/{event.id}",
                "links.event.href",
            )
            if event_room is not None:  # None = location not yet defined
                room_id = event_room.get_link("room")
                upstream_room = _lookup_object(
                    resp.result.rooms,
                    room_id,
                )
                building = Building(
                    number=int(upstream_room.building.name.lower().removeprefix("улк-")),
                    address=upstream_room.building.address,
                )
                room = re.sub(r"\s*\((?:улк|УЛК)-.+\)\s*", "", upstream_room.name_short)
                full_location = upstream_room.name
        upstream_attendees = list(
            _lookup_objects(
                resp.result.event_attendees,
                f"/{event.id}",
                "links.event.href",
            )
        )
        events.append(
            TimetableElement(
                id=event.id,
                lesson=Lesson(
                    subject=Subject.parse_obj(upstream_subject),
                    name=event.name,
                    name_short=event.name_short,
                    description=event.description,
                    type=event.get_link("type"),
                    format=event.get_link("format"),
                ),
                start=event.start,
                end=event.end,
                location=Location(
                    building=building,
                    room=room,
                    full=full_location,
                )
                if full_location is not None
                else None,
                teachers=[
                    Person.parse_obj(
                        _lookup_object(
                            resp.result.persons,
                            a.get_link("person"),
                        )
                    )
                    for a in upstream_attendees
                    if a.get_link("event-attendee-role") == "TEACH"  # Just in case
                ],
            )
        )
    return events


def parse_event_team(resp: GetTeamResult) -> list[Person]:
    return [
        Person.parse_obj(_lookup_object(resp.result.persons, a.get_link("person")))
        for a in resp.result.event_attendees
    ]
