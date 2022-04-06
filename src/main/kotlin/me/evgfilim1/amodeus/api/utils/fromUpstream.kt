package me.evgfilim1.amodeus.api.utils

import me.evgfilim1.amodeus.api.models.Person
import me.evgfilim1.amodeus.api.models.Subject
import me.evgfilim1.amodeus.api.upstream.models.Person as UpstreamPerson
import me.evgfilim1.amodeus.api.upstream.models.CourseUnitRealization

fun Person.Companion.fromUpstream(upstream: UpstreamPerson) = Person(
    id = upstream.id,
    last_name = upstream.lastName,
    first_name = upstream.firstName,
    middle_name = upstream.middleName,
    full_name = upstream.fullName,
)

fun Subject.Companion.fromUpstream(upstream: CourseUnitRealization) = Subject(
    id = upstream.id,
    name = upstream.name,
    name_short = upstream.nameShort,
)
