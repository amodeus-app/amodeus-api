__all__ = ["_lookup_object", "_lookup_objects"]

from typing import Any, Iterator, Optional, TypeVar
from uuid import UUID

_CT = TypeVar("_CT")


def _lookup_objects(
    data: list[_CT],
    value: Any,
    strategy: str = "id",
) -> Iterator[_CT]:
    lookup_chain = strategy.split(".")
    for el in data:
        attr = el
        for k in lookup_chain:
            if hasattr(attr, "get"):  # it is a dict or dict-like
                attr = attr.get(k, None)
            else:
                attr = getattr(attr, k, None)
        if isinstance(attr, UUID) and isinstance(value, str):
            attr = str(attr)
        if attr and attr == value:
            yield el
    return


def _lookup_object(
    data: list[_CT],
    value: Any,
    strategy: str = "id",
) -> Optional[_CT]:
    return next(_lookup_objects(data, value, strategy), None)
