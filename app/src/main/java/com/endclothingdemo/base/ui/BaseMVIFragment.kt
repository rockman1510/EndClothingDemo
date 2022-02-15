package com.endclothingdemo.base.ui

import android.os.Bundle
import android.view.View
import com.endclothingdemo.base.mvi.BaseMVIView
import com.endclothingdemo.base.mvi.BaseViewModel

abstract class BaseMVIFragment<BaseIntent, BaseState> : BaseFragment(),
    BaseMVIView<BaseState> {
    abstract val viewModel: BaseViewModel<BaseIntent, BaseState>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBaseState().observe(viewLifecycleOwner, ::onCallBackState)
    }

}