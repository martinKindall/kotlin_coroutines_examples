package org.example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

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
