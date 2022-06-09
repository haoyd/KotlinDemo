package lambda
object InlineDemo {

    @JvmStatic
    fun main(args: Array<String>) {
        invoke2()
    }

    // -------------------------------内联函数基本使用 start-----------------------------------

    fun invoke1() {
        for (i in 0..9) {
            block1 { println("hello") }
        }
    }

    inline fun block1(block: () -> Unit) {
        block.invoke()
    }

    // -------------------------------内联函数基本使用 end-----------------------------------


    // -------------------------------内联导致问题 start-----------------------------------

    fun invoke2() {
        sum(1, 2) {
            println("result = $it")
            return
        }

        println("done")
    }

    inline fun sum(a: Int, b: Int, lambda: (result: Int) -> Unit) {
        val r = a + b
        lambda.invoke(r)
        println("计算完毕")
    }


    // -------------------------------内联导致问题 end-----------------------------------


    // -------------------------------crossinline start-----------------------------------

    fun invoke3() {
        sum1(1, 2) {
            println("result = $it")
        }

        println("done")
    }

    inline fun sum1(a: Int, b: Int, crossinline lambda: (result: Int) -> Unit) {
        val r = a + b
        lambda.invoke(r)
        println("计算完毕")
    }

    // -------------------------------crossinline end-----------------------------------


    // -------------------------------noinline start-----------------------------------

    inline fun sum2(lambda1: () -> Unit, noinline lambda2: () -> Unit): () -> Unit {
        lambda1.invoke()
        lambda2.invoke()
        return lambda2
    }

    // -------------------------------内noinline end-----------------------------------

}