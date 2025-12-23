import uvicorn


def run() -> None:
    uvicorn.run("python_project.app:app", host="0.0.0.0", port=8000, reload=False)
