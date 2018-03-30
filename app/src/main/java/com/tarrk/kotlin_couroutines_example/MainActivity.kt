package com.tarrk.kotlin_couroutines_example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlin.concurrent.thread
import kotlin.coroutines.experimental.RestrictsSuspension

class MainActivity : AppCompatActivity() {

    /***/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (L.treeCount() == 0) {
            L.plant(L.DebugTree())
        }

        four()

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

    private suspend fun doSomething(foo: String): Deferred<String> {
        delay(1000L)
        L.i("World!")
        return CompletableDeferred("something")
    }

    private fun <T> asyncCustom(block: suspend () -> T) {

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
