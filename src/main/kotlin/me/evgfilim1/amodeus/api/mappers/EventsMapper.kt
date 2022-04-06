package me.evgfilim1.amodeus.api.mappers

import me.evgfilim1.amodeus.api.models.*
import me.evgfilim1.amodeus.api.upstream.models.Event
import me.evgfilim1.amodeus.api.upstream.models.ResultResponse
import me.evgfilim1.amodeus.api.upstream.models.SearchEventsResult
import me.evgfilim1.amodeus.api.utils.fromUpstream

object EventsMapper : Mapper<Event, SearchEventsResult, TimetableElement> {
    override fun mapOne(obj: Event, res: ResultResponse<SearchEventsResult>): TimetableElement? {
        val subject = res.result.courseUnitRealizations.find {
            it.id.value == obj.getLink("course-unit-realization")!!
        } ?: return null
        val location = res.result.eventLocations.find {
            it.eventID.value == obj.id.value
        }!!.customLocation
        val upstreamRoom = if (location == null) {
            res.result.eventRooms.find {
                it.getLink("event") == obj.id.value
            }?.let { eventRoom ->
                res.result.rooms.find { it.id.value == eventRoom.getLink("room")!! }
            }
        } else null
        val building = upstreamRoom?.let {
            Building(
                number = it.building.name.lowercase().removePrefix("улк-").toInt(),
                address = it.building.address,
            )
        }
        val room = upstreamRoom?.nameShort?.replace("\\s*\\((?:улк|УЛК)-.+\\)\\s*".toRegex(), "")
        val fullLocation = location ?: upstreamRoom?.name
        val attendees = res.result.eventAttendees.filter { it.getLink("event") == obj.id.value }
        val teamName = res.result.cycleRealizations.find {
            it.id.value == obj.getLink("cycle-realization")!!
        }?.code
        return TimetableElement(
            id = obj.id,
            lesson = Lesson(
                subject = Subject.fromUpstream(subject),
                name = obj.name,
                name_short = obj.nameShort,
                description = obj.description,
                type = obj.getLink("type")!!,
                format = obj.getLink("format"),
            ),
            start = obj.start,
            end = obj.end,
            team_name = teamName,
            location = fullLocation?.let {
                Location(
                    building = building,
                    room = room,
                    full = it,
                )
            },
            teachers = attendees.filter {
                it.getLink("event-attendee-role") == "TEACH"
            }.map { attendee ->
                val upstreamPerson = res.result.persons.find {
                    it.id.value == attendee.getLink("person")!!
                }!!
                Person.fromUpstream(upstreamPerson)
            },
        )
    }

    override fun mapMany(res: ResultResponse<SearchEventsResult>): List<TimetableElement> =
        res.result.events.mapNotNull { mapOne(it, res) }
}
