package com.endclothingdemo.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.endclothingdemo.R

class CustomDialog() {
    var builder: Builder? = null
    var alertDialog: AlertDialog? = null
    var llRootView: LinearLayout? = null
    var tvTitle: TextView? = null
    var tvMessage: TextView? = null
    var progressBar: ProgressBar? = null
    var btPositive: Button? = null
    var btNegative: Button? = null
    var btNeural: Button? = null
    var layoutButton: LinearLayout? = null
    val MSG_TIME_OUT = 1

    constructor(context: Context, builder: Builder) : this() {
        this.builder = builder
        if (alertDialog == null) {
            val view =
                LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, null, false)
            llRootView = view.findViewById(R.id.ll_dialog)
            tvTitle = view.findViewById(R.id.tv_title_dialog)
            tvMessage = view.findViewById(R.id.tv_message)
            progressBar = view.findViewById(R.id.progressBar)
            layoutButton = view.findViewById(R.id.ll_button_action)
            btPositive = view.findViewById(R.id.tv_positive)
            btNegative = view.findViewById(R.id.tv_negative)
            btNeural = view.findViewById(R.id.tv_neutral)
            if (builder.isLoading) {
                progressBar?.visibility = View.VISIBLE
            }
            if (!TextUtils.isEmpty(builder.title)) {
                tvTitle?.text = builder.title
            }
            if (!TextUtils.isEmpty(builder.message)) {
                tvMessage?.text = builder.message
            }

            if (builder.positiveListener != null) {
                layoutButton?.visibility = View.VISIBLE
                btPositive?.text = builder.contentPositive
                btPositive?.visibility = View.VISIBLE
                btPositive?.setOnClickListener {
                    dismiss()
                    builder.positiveListener?.onCallback(alertDialog!!)
                }
            }
            if (builder.negativeListener != null) {
                layoutButton?.visibility = View.VISIBLE
                btNegative?.text = builder.contentNegative
                btNegative?.visibility = View.VISIBLE
                btNegative?.setOnClickListener {
                    dismiss()
                    builder.negativeListener?.onCallback(alertDialog!!)
                }
            }
            if (builder.neuralListener != null) {
                layoutButton?.visibility = View.VISIBLE
                btNeural?.text = builder.contentNeural
                btNeural?.visibility = View.VISIBLE
                btNeural?.setOnClickListener {
                    dismiss()
                    builder.neuralListener?.onCallback(alertDialog!!)
                }
            }
            alertDialog =
                AlertDialog.Builder(context).setView(view).setCancelable(builder.isCancelable)
                    .create()
            alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog?.setCanceledOnTouchOutside(builder.isCancelable)
            if (builder.timeOut > 0) {
                object : Handler(Looper.getMainLooper()) {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        if (msg.what == MSG_TIME_OUT) {
                            dismiss()
                        }
                    }
                }.sendEmptyMessageDelayed(MSG_TIME_OUT, builder.timeOut)
            }
        }
    }


    fun show() {
        if (!alertDialog?.isShowing!!) {
            alertDialog?.show()
        }
    }

    fun isShowing(): Boolean {
        return alertDialog?.isShowing!!
    }

    fun dismiss() {
        if (alertDialog?.isShowing!!)
            alertDialog?.dismiss()
    }


    class Builder(var context: Context) {
        var title: String = ""
        var message: String = ""
        var isLoading: Boolean = false
        var positiveListener: OnPositiveListener? = null
        var negativeListener: OnNegativeListener? = null
        var neuralListener: OnNeuralListener? = null
        var isCancelable: Boolean = true
        var contentPositive = context.getString(R.string.ok)
        var contentNegative = context.getString(R.string.cancel)
        var contentNeural = context.getString(R.string.close)
        var timeOut: Long = -1

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun setLoadingContent(isLoading: Boolean): Builder {
            this.isLoading = isLoading
            return this
        }

        fun setPositiveButton(
            content: String? = contentPositive,
            listener: OnPositiveListener
        ): Builder {
            this.positiveListener = listener
            this.contentPositive = content!!
            return this
        }

        fun setNegativeButton(
            content: String? = contentNegative,
            listener: OnNegativeListener
        ): Builder {
            this.negativeListener = listener
            this.contentNegative = content!!
            return this
        }

        fun setNeuralButton(
            content: String? = contentNeural,
            listener: OnNeuralListener
        ): Builder {
            this.neuralListener = listener
            this.contentNeural = content!!
            return this
        }

        fun setCancelable(isCancel: Boolean): Builder {
            this.isCancelable = isCancel
            return this
        }

        fun setTimeOut(timeOut: Long): Builder {
            this.timeOut = timeOut
            return this
        }

        fun build(): CustomDialog {
            return CustomDialog(context, this)
        }
    }

    interface OnPositiveListener {
        fun onCallback(dialog: AlertDialog) {}
    }

    interface OnNegativeListener {
        fun onCallback(dialog: AlertDialog) {}
    }

    interface OnNeuralListener {
        fun onCallback(dialog: AlertDialog) {}
    }
}