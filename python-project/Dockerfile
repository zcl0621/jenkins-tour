FROM python:3.12-slim

WORKDIR /app

RUN pip install --no-cache-dir uv

COPY . /app

RUN uv venv /app/.venv \
    && . /app/.venv/bin/activate \
    && uv pip install -e .

ENV PATH="/app/.venv/bin:${PATH}"

CMD ["python", "-m", "python_project.main"]
