package com.spek.demo.domain.game

inline fun <A, B, R> ifNotNull(a: A?, b: B?, code: (A, B) -> R?): R? {
    return if (a != null && b != null) {
        code(a, b)
    } else {
        null
    }
}
