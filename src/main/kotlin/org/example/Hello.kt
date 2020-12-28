package org.example

import kotlinx.coroutines.*

// runBlocking doesnt complete until
// every coroutine launched in its scope
// is completed

fun main() = runBlocking<Unit> {
    childrenDoNotNeedToBeJoined()
}

fun main2() = runBlocking<Unit> {

    launch {
        emitMsg()
    }

    coroutineScope {
        launch {
            delay(2000L)
            println("Task from nested launch")
        }

        delay(1000L)
        println("Task from Coroutine Scope")
    }

    println("Coroutine Scope is over")
    launchManyIsSafe()
    globalScopeIsLikeADaemonThread()
}

suspend fun emitMsg() {
    delay(500L)
    println("Task from runBlocking")
}

suspend fun launchManyIsSafe() = coroutineScope {
    repeat(100_000) {
        launch {
            delay(2000L)
            print(".")
        }
    }
}

suspend fun globalScopeIsLikeADaemonThread() {
    val job = GlobalScope.launch {
        repeat(1000) { i ->
            println("Im sleeping $i...")
            delay(500L)
        }
    }

    job.join()  // unlike coroutineScope and launch,
                // globalScope.launch() needs a join() in order
                // to be waited
}

suspend fun cancellingExample() = coroutineScope {
    val job = launch {
        println("Heavy calculation has begun...")
        delay(5000L)
        println("Calculation has finished")
    }
    delay(2000L)
    println("User has cancelled")
    job.cancel()
    job.join()
    println("Finished")
}

suspend fun doesNotCancelExample() = coroutineScope {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 10) {
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I am sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L)
    println("main: I am tired of waiting")
    job.cancelAndJoin()
    println("main: Now I can quit")
}

suspend fun doesCancelExample() = coroutineScope {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) {
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I am sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(5000L)
    println("main: I am tired of waiting")
    job.cancelAndJoin()
    println("main: Now I can quit")
}

suspend fun finallyClause() = coroutineScope {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("I am sleeping: $i ...")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                println("job: I am finished.")
                delay(2000L)
                println("job: I delayed because I am non-cancellable")
            }
        }
    }
    println("main: let us wait")
    delay(3000L)
    println("main: I am tired of waiting")
    job.cancelAndJoin()
    println("main: Now I can quit")
}

suspend fun timeOutExample() = coroutineScope {
    withTimeoutOrNull(3000L) {
        repeat(1000) { i ->
            println("job: I am sleeping $i ...")
            delay(500L)
        }
    }

    println("Waiting for timeout completion")
}

suspend fun timeOutInsideTryFinally() = coroutineScope {
    val job = launch {
        var notAsignedInt: Int? = null
        try {
            withTimeout(2000L) {
                delay(1000L)
                println("Hello there, just testing some timeouts")
                notAsignedInt = 15
            }
        } finally {
            notAsignedInt?.let {
                println("The int is $it")
            }
        }
    }

    println("Just waiting")
}
