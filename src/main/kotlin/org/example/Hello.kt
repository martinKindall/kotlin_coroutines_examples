package org.example

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.coroutines.*

fun main() {

    GlobalScope.launch {
        delay(1000L)
        println("World!")
    }

    thread {
        Thread.sleep(500L)
        println("Hi from other thread!")
    }

    println("Hello ")
    Thread.sleep(2000L) // block main thread to keen JVM alive
    println("Learning Coroutines in Kotlin")
}
