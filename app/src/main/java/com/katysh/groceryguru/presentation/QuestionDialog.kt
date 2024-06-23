package com.katysh.groceryguru.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.katysh.groceryguru.R

class QuestionDialog(
    private val message: String,
    private val answerHandler: (Boolean) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val adb: AlertDialog.Builder = AlertDialog.Builder(activity)
            .setPositiveButton(R.string.yes, listener)
            .setNegativeButton(R.string.no, listener)
            .setMessage(message)
        return adb.create()
    }

    private val listener = DialogInterface.OnClickListener { dialog, which ->
        when (which) {
            Dialog.BUTTON_POSITIVE -> answerHandler(true)
            Dialog.BUTTON_NEGATIVE -> answerHandler(false)
        }
    }
}
