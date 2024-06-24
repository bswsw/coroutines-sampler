package chapter3

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.newSingleThreadContext

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
val singleThreadDispatcher = newSingleThreadContext(name = "SingleThreadDispatcher")

@OptIn(DelicateCoroutinesApi::class)
val multiThreadDispatcher = newFixedThreadPoolContext(name = "MultiThreadDispatcher", nThreads = 2)
