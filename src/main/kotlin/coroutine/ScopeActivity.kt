package coroutine

import kotlinx.coroutines.*

class ScopeActivity {

    private val mainScope = MainScope()

    fun onCreate() {
        mainScope.launch {
            repeat(5) {
                delay(1000L * it)
            }
        }
    }

    fun onDestroy() {
        mainScope.cancel()
    }
}

/**
 * 通过委托
 */
class Scope2Activity : CoroutineScope by CoroutineScope(Dispatchers.Default) {
    fun onCreate() {
        launch {
            repeat(5) {
                delay(1000L * it)
            }
        }
    }

    fun onDestroy() {
        cancel()
    }
}