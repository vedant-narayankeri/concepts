SQL Databases

- Standardized SQL 
- Priorititzes correctness (ACID properties)
- Storing structure data, relational data, JOINs, foreign keys
- Fixed schema (predefined)
- CAP Sacrifices - Availability
- Vertical Scalabilty
    - JOINs would otherwise require data from multiple DB servers
        - If tables distributed across DB servers, very high network latency
    - ACID transactions require 2 Phase Commit
        - Increased complexity and slowness
        - Slower writes 
    - Foreign keys might require cross-DB server validations
    - Scaling
        - Read replicas: Separate read/write paths, reduce load
            - Asynchronous replication (For faster writes, but can get potential conflicts)
            - Synchronous replication (correntness preserved by slow writes)
        - Problem with multiple DB write servers
            - Slowness due to 2PC
            - Conflict resolution complext (Ex: autoincrement key)

- Where to use
    - Transactions across tables, whenever we need to prioritize consistency
    - Ex: Banking system
    - Relational data (Joins)
        - E-commerce orders (order, items, payment, shipping, relational joins needed)

No-SQL Databases

- DB Specific APIs
- Prioritizes performance (Throughput)
- Storing unstructured data, separate queries & manual joins required
- Flexible/Dynamic schema
- CAP Sacrifices - Availability
- Horizontal Scalability
    - Enabled by Denormalized data 
        - No joins required
        -  Single read from a DB server
    - No distributed transactions/2PC overhead
    - No foreign keys, no cross-server checks required
    - High throughput: No locks & faster writes
- Where to use - 
    - High throughput requirements 
        - Ex: store logs
    - Unstructured data & frequent schema changes (MongoDB)
    - Write heavy, append-only, time-sereries data


- Why is Redis KV lookup faster than Postgres?
    - In-memory vs Disk
        - Redis - all data in RAM alwasy
        - Postgres - data might be in RAM, but requires I/O to read from disk
    - No query parsing overhead
        - Redis is single hash-map lookup `(O(1))`
        - Postgres
            - Parse SQL
            - Plan Query
            - Check perms
            - Access index
            - Fetch row
            - Serialize

DB Sharding

DB Partitioning