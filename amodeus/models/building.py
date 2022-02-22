from pydantic import BaseModel


class Building(BaseModel):
    number: int
    address: str
