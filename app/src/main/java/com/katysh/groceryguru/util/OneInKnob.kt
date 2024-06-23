package com.katysh.groceryguru.util


fun interface OneInKnob<T> {
    fun execute(t: T)
}
