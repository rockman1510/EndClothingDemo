package com.endclothingdemo.base.mvi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<BaseIntent, BaseState> : ViewModel(),
    MVIViewModelContract<BaseIntent> {
    protected open val baseState: MutableLiveData<BaseState> = MutableLiveData()

    fun getBaseState(): LiveData<BaseState> = baseState

}