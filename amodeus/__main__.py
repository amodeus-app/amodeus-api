from uvicorn import run

from amodeus.app import app

run(app, host="0.0.0.0", log_config=None)
