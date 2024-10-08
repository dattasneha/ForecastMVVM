package com.example.forecastmvvm.internal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

fun<T> lazyDeffered(block:suspend CoroutineScope.()-> T):Lazy<Deferred<T>>{
    return lazy{
        GlobalScope.async(start = CoroutineStart.LAZY){
            block.invoke(this)
        }
    }

}