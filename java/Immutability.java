package java;
/***
 * Why do we need immutability?
 * 
 * An immutable object cannot be changed after creation
 * All fields are set in constructor and never modified
 * 
 * Reason:
 * Thread safety - 100 threads can read simultaneously. no locks, volattile, etc
 * Safe HashMap keys - String is immutable. hashCode never changes and lookup always correct
 *  - If key for hashmap is mutable, then you can override data potentially or bucket can change for same data. Querying & persistence both broken
 * Predictable state for object
 * Enables caching/reuse
 * 
 * Arrays are not inherently immutable, we need to use defensive copy of collections
*/