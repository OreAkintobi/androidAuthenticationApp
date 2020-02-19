package com.ore.mvvm.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Defines all functions needed for Coroutines
object Coroutines {
    // This function takes suspending functions as a parameter that will be executed on the Main thread
    fun main(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }
}