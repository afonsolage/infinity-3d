package com.lagecompany.infinity.debug

import java.util.*


/**
 * Iterates provided by [callback] code [ITERATIONS]x[TEST_COUNT] times.
 * Performs warming by iterating [ITERATIONS]x[WARM_COUNT] times.
 */
fun simpleMeasureTest(
        ITERATIONS: Int = 1,
        TEST_COUNT: Int = 40,
        WARM_COUNT: Int = 4,
        prepare: () -> Unit = {},
        dispose: () -> Unit = {},
        callback: () -> Unit

) {
    val results = ArrayList<Long>()
    var totalTime = 0L
    var t = 0

    println("$PRINT_REFIX -> go")

    while (++t <= TEST_COUNT + WARM_COUNT) {

        var acctTime = 0L
        var i = 0
        while (i++ < ITERATIONS) {
            prepare()
            val startTime = System.currentTimeMillis()
            callback()
            acctTime += System.currentTimeMillis() - startTime
            dispose()
        }

        if (t <= WARM_COUNT) {
            println("$PRINT_REFIX Warming $t of $WARM_COUNT")
            continue
        }

        println(PRINT_REFIX + " " + acctTime.toString() + "ms")

        results.add(acctTime)
        totalTime += acctTime
    }

    results.sort()

    val average = totalTime / TEST_COUNT
    val median = results[results.size / 2]
    val max = results.last()
    val min = results.first()

    println("$PRINT_REFIX -> average=${average}ms / median=${median}ms / max=${max}ms / min=${min}ms / delta=${max - min}ms")
}

/**
 * Used to filter console messages easily
 */
private val PRINT_REFIX = "[TimeTest]"