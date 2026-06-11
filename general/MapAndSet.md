HashMap internals:

- Every put()
    - hash = key.hashCode()
    - Index = hash & (capacity-1) - get bucket index
    - Empty bucket then inser node
    - Collision -> walk the chain, check equals()
        - If key exists then update value
        - Else append node
    - Check resize: if size > capacity * loadFactor(0.75)
        - breach then double array and re-hash all entries
    - Treeify when a single bucket has >=8 nodes AND total capacity >=64
        - O(log(N)) complexity for that bucket traversl, practical for large buckets where O(N) is in-efficient
    - O(1) normally, worsens in-case of treeify

TreeMap - 

- Self balancing Binary Search tree
- Every node = one key-value entry
- Sorted by Key (comparable or comparator)

- Every put()
    - start at root
        - Compare key
        - Go left if smaller
        - Go right if smaller
    - Find position and insert node
    - Reblanace - rotations + recolor to maintain Red-black properties
    - No resizing - tree grows node by node. No arrray, load factor
    - O(log(n)) always