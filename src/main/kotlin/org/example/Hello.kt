package org.example

import kotlinx.coroutines.*

// runBlocking doesnt complete until
// every coroutine launched in its scope
// is completed
fun main() = runBlocking<Unit> {

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
