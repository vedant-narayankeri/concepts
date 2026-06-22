Rate Limiter - Control how many requests clients can send in a particular time-frame.
If beyond the limit then return 429 HTTP code

Need:
    - To prevent abuse
    - Protect service from burst of traffic
    - Ensures fair usage across all users (shouldn't be biased)


Entities -
    - Rules: rate limiting policy
        - Ex: requests/hour allowed or search API allows 10 request/minute for each IP
    - Client: the entity being rate limited
        - Ex: can be user-id or IP Address, or API-keys, can also be combination of these
    - Requests: Carry context like endpoint accessed, timestamp, client-identity
        - Helps to determine which rules to apply, how to track usage

High Level Design -

Q. Where to put the RL logic?

- Ideally it should be integrated with the API Gateway or Load balancer
- Every request should hit RL before it reaches app-servers
- Main challenge is limited context (basic auth token, url, http headers & IP address)
    - Hence cannot apply complex logic like "Premimum users get 10X more limits"
    - No metadata

Q. How to identify Clients?

- User Id: Present in the Authorization header as JWT token
    - Good for authenticated APIs
    - Each user get's own rate limit allocation
- IP Address
    - Good for public APIs or when we don't have user accounts
    - Should be present in `X-Forwarded-for-` header
- API Key
    - Common for developer APIs
    - Each key holder gets their own limits
    - Mostly denoted in `X-API-Key` header



Algorithms:

Fixed Window Counter

Sliding Window Log

Sliding Window Counter

Token Bucket





Some adhoc q&as -

How would you enforce limits consistently across multiple servers without a single point of failure?
TODO - Which algorithm would you choose between token bucket, leaky bucket, and sliding window and why?
TODO - How would you handle bursts without punishing legitimate high-frequency users?