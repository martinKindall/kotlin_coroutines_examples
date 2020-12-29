package org.example

fun plainSequence() {
    val someSeq = sequence<Int> {
        repeat(4) {
            Thread.sleep(100)
            yield(it)
        }
    }
    someSeq.forEach { println(it) }
}
