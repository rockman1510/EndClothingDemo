package com.endclothingdemo.utils

import android.content.Context
import com.endclothingdemo.R

class DialogUtils {
    companion object {
        fun loadingDialog(context: Context): CustomDialog {
            return CustomDialog.Builder(context).setLoadingContent(true)
                .setCancelable(false).setTitle(context.getString(R.string.loading))
                .setMessage(context.getString(R.string.please_wait)).setTimeOut(15000L).build()
        }

        fun messageDialog(context: Context, title: String, message: String): CustomDialog {
            return CustomDialog.Builder(context).setCancelable(true).setTitle(title)
                .setMessage(message).setNeuralButton(
                    context.getString(R.string.close),
                    object : CustomDialog.OnNeuralListener {
                    }).build()
        }
    }
}