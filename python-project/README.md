# python-project

HTTP service using FastAPI, Postgres, and Alembic.

## Quick start

```bash
UV_CACHE_DIR=.uv-cache uv sync
UV_CACHE_DIR=.uv-cache uv run serve
```

## Database migrations

```bash
alembic upgrade head
```

## Tests

```bash
UV_CACHE_DIR=.uv-cache uv run pytest
```

## Docker Compose for containerized tests

```bash
# Unique project name per build isolates containers and volumes
COMPOSE_PROJECT_NAME=python-project-123 \
  docker compose -f docker-compose.test.yml up --build --exit-code-from app

# Cleanup
COMPOSE_PROJECT_NAME=python-project-123 \
  docker compose -f docker-compose.test.yml down -v
```
