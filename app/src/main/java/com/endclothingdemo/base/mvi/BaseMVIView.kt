package com.endclothingdemo.base.mvi

interface BaseMVIView<BaseState> {
    fun onCallBackState(state: BaseState)
}