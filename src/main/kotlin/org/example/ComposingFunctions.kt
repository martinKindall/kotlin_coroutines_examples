package org.example

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.async
import kotlin.system.measureTimeMillis

suspend fun doSomethingOne(): Int {
    delay(1000L)
    return 30
}

suspend fun doSomethingTwo(): Int {
    delay(2000L)
    return 40
}

suspend fun calculation() {
    val time = measureTimeMillis {
        val res1 = doSomethingOne()
        val res2 = doSomethingTwo()
        println("The result is ${res1+res2}")
    }
    println("The measured time is $time [ms]")
}

suspend fun parallelCalculation() = coroutineScope {
    val time = measureTimeMillis {
        val def1 = async { doSomethingOne() }
        val def2 = async { doSomethingTwo() }
        println("The result is ${def1.await() + def2.await()}")
    }
    println("The elapsed time is $time [ms]")
}
