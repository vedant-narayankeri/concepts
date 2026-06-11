TODO - Same event on multiple consumers possible?
TODO - counsumer/partition count relationship
TOTO - Rebalance strategy (Save partitions, event of registration/deregistration take actions)

Consumers vs Partitions

- Topic: orders (4 partitions P0, P1, P2, P3)
- Consumer group: "order-service"

Consumers < Partitions (most common)
- 2 Consumers, 4 partitions
    - Consumer A -> p0, p1
    - Consumer B -> p2, p3

Each consumer handles multiple partitions

Consumers = Partitions (Ideal)
- Consumer A -> p0
- Consumer B -> p1
- Consumer C -> p2
- Consumer D -> p3

Max Parallelism, Each consumer gets exactly one partition

Consumers > Partition

- 6 Consumers, 4 Partitions
- 2 Consumers will be idle, wasted resources
- Partitions cannot be split further


Q. Can 2 consumers in same group get the same message?
- No, never. Kafka guarantees that - within each consumer group, each partition is assigned to exactly one Consumer. No sharing

- Note possible in an edge scenario:
    - During rebalancing, there is a window where duplicate processing can happen
    - Rebalance triggered when 
        - Consumer joins/leaves group
        - Consumer misses heartbeat
        - New Partitions added to topic
    - Window
        - Consumers A, B and 2 partitions
        - If Consumer B processed messages 100-110 from P2
            - But only committed offset upto 105
            - Consumer B crashes and rebalance triggered
            - Consumer A resumes P2 from offset 105
            - Problem - Messages between 105-110 processed TWICE
    - How to handle it?
        - Idempotent processing, ops should be safe
        - Commit frequency: setup auto-commit and, reduce interval or commit manually after each batch
        - Cooperative rebalancing - Only revokes affected partitions for a consumer, rest keep running 
            - Eager: default re-assigns all paritions, all partitions are revoked
                - Entire group stops for seconds
    
