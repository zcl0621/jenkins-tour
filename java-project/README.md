# java-project

Minimal Java backend for Jenkins pipeline practice.

## Run locally

```bash
mvn -q -DskipTests package
java -jar target/jenkins-tour-0.1.0.jar
```

Then open:
- http://localhost:8080/
- http://localhost:8080/health

## Docker

```bash
docker build -t jenkins-tour-java .
docker run --rm -p 8080:8080 jenkins-tour-java
```
