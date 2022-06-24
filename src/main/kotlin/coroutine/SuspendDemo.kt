package coroutine

import kotlinx.coroutines.*
import util.log

object SuspendDemo {
    @JvmStatic
    fun main(args: Array<String>) {
        GlobalScope.launch {
            eat()
        }
    }

    private suspend fun eat() {
        delay(100)
        println("eat")
//        run()
    }

    private suspend fun run() {
        println("run")
    }
}