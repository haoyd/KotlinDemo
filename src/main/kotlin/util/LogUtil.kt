package util

fun log(msg: String?) {
    msg?.let {
        println("[${Thread.currentThread().name}] $msg")
    }
}