package org.example

import kotlinx.coroutines.*

fun showThreadsNames() = runBlocking {
    launch {
        println("main runBlocking: I am working on ${Thread.currentThread()}")
    }
    launch(Dispatchers.Unconfined) {
        println("Unconfined: I am working on ${Thread.currentThread()}")
    }
    launch(Dispatchers.Default) {
        println("Default: I am working on ${Thread.currentThread()}")
    }
    launch(newSingleThreadContext("My own thread")) {
        println("Default: I am working on ${Thread.currentThread()}")
    }
}

suspend fun childrenDoNotNeedToBeJoined() = coroutineScope {
    val request = launch {
        repeat(3) { i ->
            launch {
                delay((i + 1) * 200L)
                println("Coroutine $i is done")
            }
        }
        println("request: I dont explicitly join the children")
    }

    println("I am done")
}
