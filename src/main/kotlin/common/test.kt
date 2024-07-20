package common

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun invokeLambda(lambdaFunc: () -> Unit) {
    contract{
        callsInPlace(lambdaFunc, kotlin.contracts.InvocationKind.EXACTLY_ONCE)
    }

    lambdaFunc()
}

fun main() {
    val stringConstant: String
    invokeLambda {
        // captured values init is forbidden 에러
        stringConstant = "hello world"
    }

    //variable must be initilized 에러
    println(stringConstant)
}
