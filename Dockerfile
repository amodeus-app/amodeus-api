FROM python:3.10-slim AS build

RUN pip install --no-cache-dir 'build~=0.6.0.post1'
WORKDIR /build
COPY pyproject.toml setup.cfg ./
COPY amodeus ./amodeus/
RUN python -m build -w \
    && python -m venv /app \
    && /app/bin/python -m pip install $(find dist/ -iname '*.whl')[server]

FROM python:3.10-slim AS runtime
EXPOSE 8000

WORKDIR /app
COPY --from=build /app ./
RUN useradd -MrUd / -s /usr/sbin/nologin app

USER app
ENTRYPOINT ["/app/bin/python", "-m", "uvicorn", "amodeus.app:app", "--host", "0.0.0.0", "--port", "8000", "--proxy-headers"]
