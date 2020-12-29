package org.example

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach

fun plainSequence() {
    val someSeq = sequence<Int> {
        repeat(4) {
            Thread.sleep(100)
            yield(it)
        }
    }
    someSeq.forEach { println(it) }
}

suspend fun flowStream() {
    val someFlow = flow {
        repeat(4) {
            delay(100L)
            emit(it)
        }
    }

    someFlow.collect {println("flow: $it")}
}
