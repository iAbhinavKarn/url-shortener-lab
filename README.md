# url-shortener-lab
A Java Spring Boot URL shortener built to explore system design, Base62 encoding, hash-based and UUID short-code generation, collision handling, PostgreSQL, redirects, and performance monitoring.

## API documentation

After adding the dependency, refresh the Gradle project in VS Code, then stop and restart the application so the new runtime classpath is loaded. In the Gradle sidebar, use **Refresh Gradle Project**. If the dependency still does not appear, run **Java: Clean Java Language Server Workspace** from the Command Palette and reopen the project.

Then open Swagger UI at:

```text
http://localhost:8080/swagger-ui/index.html
```

The generated OpenAPI specification is available at:

```text
http://localhost:8080/v3/api-docs
```
