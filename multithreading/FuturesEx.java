package multithreading;
//TODO - Futures, CompletableFuture, ExecutorService. Compare and contrast

/***
 * Futures - blocking & limited
 * 
 * ExecutorService exec = Executors.newFixedThreadPool(4);
 * Future<Integer> future = exec.submit(() -> //Do something);
 * int result = future.get();// Blocks forever
 * int result = future.get(5, TimeUnit.SECONDS);// Blocks with timeout
 * 
 * Functions
 * get() - block and wait
 * isDone() - poll
 * cancel() - attempt cancelled
 * 
 * 
 * CompletableFuture - Non blocking, chainable
 * 
 * CompleatableFuture<User> userF = CompletableFuture.supplyAsync(() => fetchUser(42));
 * 
 * We can chain callbacks with thenApply()/thenCompose()
 * We can combine paralle tasks with allOf()/thenCombine
 * Handle elrrors with exceptionally()
 * 
 * We can do all of above without blocking a thread
 * **/