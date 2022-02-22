from ..models import Person
from ..upstream.models import SearchPersonResult


def parse_people(resp: SearchPersonResult) -> list[Person]:
    return [Person.parse_obj(p) for p in resp.result.persons]
