__all__ = ["Config", "RootAccountConfig", "load_config"]

from pathlib import Path
from typing import Union

from pydantic import BaseModel, SecretStr
from yaml import safe_load


class RootAccountConfig(BaseModel):
    login: str
    password: SecretStr


class Config(BaseModel):
    root_account: RootAccountConfig


def load_config(path: Union[Path, str] = "config.yaml") -> Config:
    if isinstance(path, str):
        path = Path(path)
    with path.open() as f:
        raw_config = safe_load(f)
    return Config.parse_obj(raw_config)
