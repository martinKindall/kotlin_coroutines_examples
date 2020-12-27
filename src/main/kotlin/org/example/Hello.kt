package org.example

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

fun main() = runBlocking<Unit> {

    GlobalScope.launch {
        delay(1000L)
        println("World!")
    }

    thread {
        Thread.sleep(500L)
        println("Hi from other thread!")
    }

    println("Hello ")
//    Thread.sleep(2000L) // block main thread to keen JVM alive
    delay(2000L)
    println("Learning Coroutines in Kotlin")
}
