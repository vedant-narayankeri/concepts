HTTP Verbs

GET -> read/fetch
- Idempotent
- No request body

POST -> creates new
- Needs request body
- Not idemptotent

PUT -> Full replace 
- Idempotent
- Needs request body

PATCH -> Partial update
- Idempotent
- Needs request body what to update specifically

DELETE - Remove data
- Idempotent
- No request body required, can be specified in URL


REST vs gRPC

Contract:
    - REST -  loose contract, refer docs/Swagger
        - Opetional Code generation with OpenAPI specs
    - gRPC -  strict `.proto` file
        - Built-in code generation 
        - protoc generates client+server

Speed: gRPC upto ~10X faster than REST

Streaming: 
- REST is request response only
- gRPC: request response, bi-directional streaming, client/server side streaming 

Human readable:
- REST JSON response is readable
- gRPC - Bindary response, need tools to debug

Browser:
- REST has native support
- gRPC: Needs grpc-web proxy

When to use REST?
- Public APIs: third parties consume them
- UI services: browsers can call directly
- Simple CRUD: standard resource operations
- When cacheability matters - HTTP caching headers work naturally

When to use gRPC?
- Service-to service communication: internal microservice communication
- Low latency: high frequency calls (trading, real-time systems)
- Streaming: live data feeds, chat, log tailing
- Polyglot: auto-generates clients in Java, Go, Python, C++ from one `.proto`
- Auth services, config services - internal, no browser