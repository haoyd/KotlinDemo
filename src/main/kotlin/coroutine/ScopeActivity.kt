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
 * ιθΏε§ζ
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