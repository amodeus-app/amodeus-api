package me.evgfilim1.amodeus.api.mappers

import me.evgfilim1.amodeus.api.models.Person
import me.evgfilim1.amodeus.api.upstream.models.ResultResponse
import me.evgfilim1.amodeus.api.upstream.models.SearchPeopleResult
import me.evgfilim1.amodeus.api.utils.fromUpstream
import me.evgfilim1.amodeus.api.upstream.models.Person as UpstreamPerson

object PeopleMapper : Mapper<UpstreamPerson, SearchPeopleResult, Person> {
    override fun mapOne(obj: UpstreamPerson, res: ResultResponse<SearchPeopleResult>): Person {
        return Person.fromUpstream(obj)
    }

    override fun mapMany(res: ResultResponse<SearchPeopleResult>): List<Person> =
        res.result.persons.map { mapOne(it, res) }
}
