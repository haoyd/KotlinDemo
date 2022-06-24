package coroutine

import kotlinx.coroutines.*
import util.log

object CoroutineDemo {

    @JvmStatic
    fun main(args: Array<String>) {
//        useJob()
        useDispatchers()

        Thread.sleep(5000)
        log("end")
    }

    private fun useGlobalScope() {
        GlobalScope.launch(context = Dispatchers.IO) {
            delay(1000)
            log("launch")
        }
    }

    /**
     * 等到内部的协程都执行完才会执行自己，会阻塞线程
     */
    private fun useBlocking() {
        runBlocking {
            // 请求接口，拿到用户信息
            launch {
                delay(1000)
            }

            // 请求接口，拿到订单信息
            launch {
                delay(1500)
            }

            // 在订单信息中显示用户信息
        }
    }

    /**
     * 等到内部的协程都执行完才会执行自己，不会阻塞线程，它是一个挂起函数
     */
    private suspend fun useCoroutineScope() {
        coroutineScope {
            // 请求接口，拿到用户信息
            launch {
                delay(1000)
            }

            // 请求接口，拿到订单信息
            launch {
                delay(1500)
            }

            // 在订单信息中显示用户信息
        }
    }

    /**
     * 抛出的异常不会影响到作用域内的其它函数
     */
    private suspend fun useSupervisorScope() {
        supervisorScope {
            // 请求接口，拿到用户信息
            launch {
                delay(500)
                log("Task throw Exception")
                throw Exception("failed")
            }

            // 请求接口，拿到订单信息
            launch {
                delay(1500)
            }
        }
    }

    /**
     * async 与 await 可以结合使用
     */
    private fun useAsync() {
        runBlocking {
            val a = async {
                delay(100)
                100
            }

            val b = async {
                delay(200)
                200
            }

            log((a.await() + b.await()).toString())
        }
    }

    /**
     * Job 的用法
     */
    private fun useJob() {
        runBlocking {
            val job = Job()
            val scope = CoroutineScope(job + Dispatchers.IO)

            log("job is $job")

            scope.launch {
                try {
                    delay(3000)
                } catch (e: CancellationException) {
                    log("job is cancelled")
                    throw e
                }
                log("end")
            }

            delay(1000)
            log("scope job is ${scope.coroutineContext[Job]}")
            scope.coroutineContext[Job]?.cancel()
        }

    }

    /**
     * 配置执行线程
     */
    private fun useDispatchers() {
        runBlocking {
            // 在主线程
            launch {
                log("main runBlocking")
            }

            // 默认
            launch(Dispatchers.Default) {
                log("Default")
                launch(Dispatchers.Unconfined) {
                    log("Unconfined 1")
                }
            }

            // 子线程
            launch(Dispatchers.IO) {
                log("IO")
                launch(Dispatchers.Unconfined) {
                    log("Unconfined 2")
                }
            }

            // 单独的线程池
            launch(newSingleThreadContext("MyOwnThread")) {
                log("newSingleThreadContext")
                launch(Dispatchers.Unconfined) {
                    log("Unconfined 4")
                }
            }

            // 依赖上层环境
            launch(Dispatchers.Unconfined) {
                log("Unconfined 3")
            }

            // 默认
            GlobalScope.launch {
                log("GlobalScope")
            }
        }
    }
}