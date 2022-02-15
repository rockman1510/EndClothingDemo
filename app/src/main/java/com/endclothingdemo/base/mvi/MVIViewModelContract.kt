package com.endclothingdemo.base.mvi

interface MVIViewModelContract<BaseIntent> {
    fun processIntent(intent: BaseIntent)
}