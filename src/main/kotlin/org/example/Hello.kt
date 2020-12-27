package org.example

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// runBlocking doesnt complete until
// every coroutine launched in its scope
// is completed
fun main() = runBlocking<Unit> {

    launch {
        delay(1000L)
        println("World!")
    }

    println("Hello ")
}
