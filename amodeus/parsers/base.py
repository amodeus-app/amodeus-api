__all__ = ["_lookup_object", "_lookup_objects"]

from typing import Any, Iterator, Mapping, TypeVar, cast
from uuid import UUID

_T = TypeVar("_T", Mapping[str, Any], Any)


def _lookup_objects(
    data: list[_T],
    value: Any,
    strategy: str = "id",
) -> Iterator[_T]:
    lookup_chain = strategy.split(".")
    for el in data:
        attr: _T | None = el
        for k in lookup_chain:
            if hasattr(attr, "get"):
                attr = cast(Mapping[str, Any], attr)
                attr = attr.get(k, None)
            else:
                attr = getattr(attr, k, None)
        if isinstance(attr, UUID) and isinstance(value, str):
            attr = str(attr)
        if attr and attr == value:
            yield el
    return


def _lookup_object(
    data: list[_T],
    value: Any,
    strategy: str = "id",
) -> _T | None:
    return cast(_T | None, next(_lookup_objects(data, value, strategy), None))
