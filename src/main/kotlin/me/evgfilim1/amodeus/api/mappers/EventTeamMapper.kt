package me.evgfilim1.amodeus.api.mappers

import me.evgfilim1.amodeus.api.models.Person
import me.evgfilim1.amodeus.api.upstream.models.EventAttendee
import me.evgfilim1.amodeus.api.upstream.models.GetEventTeamResult
import me.evgfilim1.amodeus.api.upstream.models.ResultResponse
import me.evgfilim1.amodeus.api.upstream.models.SearchPeopleResult
import me.evgfilim1.amodeus.api.utils.fromUpstream
import me.evgfilim1.amodeus.api.upstream.models.Person as UpstreamPerson

object EventTeamMapper : Mapper<EventAttendee, GetEventTeamResult, Person> {
    override fun mapOne(obj: EventAttendee, res: ResultResponse<GetEventTeamResult>): Person? =
        res.result.persons.find { it.id.value == obj.getLink("person") }?.let(Person::fromUpstream)

    override fun mapMany(res: ResultResponse<GetEventTeamResult>): List<Person> =
        res.result.eventAttendees.mapNotNull { mapOne(it, res) }
}
