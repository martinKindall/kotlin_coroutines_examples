package org.example

import kotlinx.coroutines.*

// runBlocking doesnt complete until
// every coroutine launched in its scope
// is completed

fun main() = runBlocking {
    doesCancelExample()
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
    GlobalScope.launch {
        repeat(1000) { i ->
            println("Im sleeping $i...")
            delay(500L)
        }
    }

    delay(1300L)
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
