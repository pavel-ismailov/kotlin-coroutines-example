package com.tarrk.kotlin_couroutines_example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.experimental.*
import kotlin.concurrent.thread
import kotlin.coroutines.experimental.RestrictsSuspension
import kotlin.coroutines.experimental.SequenceBuilder
import kotlin.coroutines.experimental.buildSequence

class MainActivity : AppCompatActivity() {

    /***/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (L.treeCount() == 0) {
            L.plant(L.DebugTree())
        }

        six()

    }

    /***/
    private fun one() {
        L.i("one")

        async {

            L.i("one - 2")
            L.i("one - 2")
            L.i("one - 2")
            L.i("one - 2")
            L.i("one - 2")
            L.i("one - 2")

        }

        L.i("one - 3")
        L.i("one - 3")
        L.i("one - 3")
        L.i("one - 3")
        L.i("one - 3")
        L.i("one - 3")

    }

    private fun two() {
        L.i("two")
        async {
            delay(1000L)
            L.i("two", "World!")
            doSomething("test")
        }
        L.i("two", "Hello,")
    }

    private fun three() {
        L.i("three")

        async {
            val result: String = doSomething("test").await()
            L.i("three", result)
        }

    }

    private fun four() {
        L.i("four")

        //doSomething("test")

        async {
            doSomething("test")
        }

        /*thread {
            doSomething("test")
        }*/

    }

    private fun five() {
        L.i("five")
        val fibonacciSeq = buildSequence {
            var a = 0
            var b = 1

            yield(1)

            while (true) {
                yield(a + b)

                val tmp = a + b
                a = b
                b = tmp
            }
        }

        // Print the first five Fibonacci numbers
        L.printList("five", fibonacciSeq.take(8).toList())
    }

    private fun six() {
        L.i("six")
        val lazySeq = buildSequence {
            L.i("six", "START")
            for (i in 1..5) {
                yield(i)
                L.i("six", "STEP")
            }
            L.i("six", "END")
        }

        // Print the first three elements of the sequence
        lazySeq.take(3).forEach { L.i("six", it) }
    }

    private fun seven() {
        L.i("seven")
        val lazySeq = buildSequence {
            yield(0)
            yieldAll(1..10)
        }

        lazySeq.forEach { L.i("seven", it) }
    }

    private fun eight() {
        L.i("eight")
        val lazySeq = buildSequence {
            for (i in 1..10) yieldIfOdd(i)
        }
        lazySeq.forEach { L.i("eight", it) }
    }

    private suspend fun doSomething(foo: String): Deferred<String> {
        delay(1000L)
        L.i("World!")
        return CompletableDeferred("something")
    }

    private fun <T> asyncCustom(block: suspend () -> T) {

    }

    private suspend fun kotlin.coroutines.experimental.SequenceBuilder<Int>.yieldIfOdd(x: Int) {
        if (x % 2 != 0) yield(x)
    }

    /***/
    interface Base {

        suspend fun foo()
    }

    class Derived : Base {

        override suspend fun foo() {
            //...
        }

    }

    @RestrictsSuspension
    abstract class SequenceBuilder<in T> {
        //...
    }

}




