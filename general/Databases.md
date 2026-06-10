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
            - Storage: B-tree index -> leaf page -> head tuple. Lookup -  `(Olog(n))` with multiple pointer chases
            - Steps:
                - Parse SQL
                - Plan Query
                - Check perms
                - Access index
                - Fetch row
                - Serialize
    - No ACID overhead
        - Redis is single-threaded event loop, no transactions. no versioning
        - Postgres: ACID overhead
            - MVCC (multi-version concurrency control)
            - It keeps multiple version of the same row, so readers/writes don't block each other
            - Exentially snapshot management
            - MVCC + WAL combinations slows queries - Tradefoff for correctness
    - Connections
        - Redis - Single threaded event loop, handles 100K+ connections cheaply
        - Postgress:
            - One OS process per connection (5-10MB)
            - Context switching overhead (500 connections -> 500 processes)

DB Partitioning
- Split table into pieces within same database server

- Strategies
    - Range based partitioning
        - Split by some range
            - `create table orders_2024_q1 PARTITION of orders for values from ('2024-01-01') to ('2024-04-01')`
        - Best - Time-series, logs, archival data (drop old partitioin -> instant delete)
        - New value automatically falls into a range
        - Continous values, anything fits
        - Query optimiation (`where date between x & y`)
            - Prune to 1-2 partitioins
    - Hash partitioning
        - Spread data evenly on a key
            - `PARTITION by HASH (user_id) PARTITIONS 8`
        - Best - Even distritbution when no natural range
    - List Partitioning
        - `PARTITION by LIST (region)`
          `('US', 'CA') -> partition_americas`
          `('IN', 'JP') -> partition_apac `    
        - Best - Categories, regions, status
        - New value must be in list or is rejected
        - Discrete values - explicit memebership is required
        - Query optimiation (`where region = 'US'`)
            - Prune to 1 partition

- Benefits
    - Faster queries: Prunes relevant partitions only for Query
    - Faster deletex - `DROP PARTITION` instead of deleting millions of rows
    - Parallel scans - DB can scan partitions concurrently
    - Maintenence: We can reindex/vacuum one partition wihtout locking entire table
- Limitations
    - Still one DB server - Bounded by single machine's CPU, RAM, disk
    - Cross-partition queries can be slower (I think with JOINS)
    - Useful generally when we `exceed ~100Million+ rows`


DB Sharding
- Split data across multiple separate database servers

- Strategies
    - Key-based (hash)
        - Usage - even distribution
        - shard = has(user_id)%3 
        - `user_id=123 in shard 3`
        - `user_id=124 in shard 2`
    - Range-based
        - Simple, but hotspots 
            - All new user queries hit latest shard
        - `user_id (1-1Million) -> shard 1`
        - `user_id (1M - 2 Million) -> shard 2`
    - Directory-based
        - Need Lookup Service
        - Persist mapping of user_id and shard - dependent on user we can specify routing logic
            - Ex: capacity based (shard with most free space), business rule (premimum users SHARD 1) etc
            - Hence, very flexible
        - But it is `Single point of failure`, common problem in complex system
    - Consistent hashing (BEST practice)
        - Hash ring: Nodes placed on a c=ircle
        - Key hashes to a point -> walk clockwise -> first node = owner
        - Adding/removing node 
            - reshuffles only ~1/N keys
        - Used by DynamoDB, Redis cluster, etc.

- Limitations
    - Cross-shard queries
        - `SELECT .. JOIN` across shards is very slow
        - We scattered data across different servers and have to gather it
        - Added complexity as well
    - Cross-shard transactions
        - 2PC is very complex and slow
    - Resharding
        - Redistribution data everytime shard added/deleted
    -= 

- Benefits