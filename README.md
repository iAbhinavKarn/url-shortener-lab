# url-shortener-lab
A Java Spring Boot URL shortener built as a hands-on learning project.

The goal is not only to read about algorithms, but to implement them, run them, and understand how they work in practice. The project is being developed to explore:

- Hash-based and UUID short-code generation
- Base62 encoding
- Collision detection and resolution
- PostgreSQL persistence
- URL redirects
- Performance monitoring
- System design trade-offs

## Current implementation

- Accepts the original URL from a JSON request body using the `url` field.
- Generates a six-character hash-based short code using SHA-256 and Base62 encoding.
- Detects short-code collisions and regenerates a code using a nonce and attempt number.
- Stores the original URL, short code, generation method, hash salt, and attempt count in PostgreSQL.
- Exposes the URL-shortening endpoint at `POST /api/v1/url/get-short-url`.

### Why Base62 and six characters?

A SHA-256 hash is too long to use directly as a short URL. It does not satisfy the main purpose of this project: creating a URL that is compact and easy to share.

The hash is therefore converted to Base62, using only the characters `0-9`, `a-z`, and `A-Z`. Base62 stores more information in fewer characters than hexadecimal while keeping the result URL-friendly. We then use the first six Base62 characters as the short code.

Six characters keep the URL short, but reducing the hash space increases the chance of two original URLs producing the same code. That is why the application checks for collisions and regenerates the code with a nonce and attempt number when necessary.

Example request:

```json
{
  "url": "https://example.com/long-url"
}
```

The endpoint returns the generated short code.

## API documentation

Swagger UI is provided by Springdoc OpenAPI. After adding or changing the dependency, refresh the Gradle project in VS Code, then stop and restart the application so the new runtime classpath is loaded. In the Gradle sidebar, use **Refresh Gradle Project**. If the dependency still does not appear, run **Java: Clean Java Language Server Workspace** from the Command Palette and reopen the project.

Then open Swagger UI at:

```text
http://localhost:8080/swagger-ui/index.html
```

The generated OpenAPI specification is available at:

```text
http://localhost:8080/v3/api-docs
```
