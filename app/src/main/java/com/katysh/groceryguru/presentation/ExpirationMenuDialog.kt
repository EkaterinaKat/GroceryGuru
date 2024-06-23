package com.katysh.groceryguru.presentation

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.katysh.groceryguru.R
import com.katysh.groceryguru.model.ExpirationEntryWithProduct
import com.katysh.groceryguru.presentation.viewmodel.ExpirationViewModel

class ExpirationMenuDialog(
    private val context: AppCompatActivity,
    private val entry: ExpirationEntryWithProduct,
    private val viewModel: ExpirationViewModel,
    private val activityUpdateKnob: () -> Unit
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val itemView: View = inflater.inflate(R.layout.dialog_expiration_menu, null)

        itemView.findViewById<TextView>(R.id.title_text_view).text = entry.product.title
        itemView.findViewById<Button>(R.id.delete_button).setOnClickListener {
            openDeleteDialog()
        }
        itemView.findViewById<View>(R.id.edit_task_button).setOnClickListener {
            //todo
        }

        return itemView
    }

    private fun openDeleteDialog() {
        val dlg1: DialogFragment = QuestionDialog("Delete?") {
            if (it) {
                viewModel.delete(entry.expirationEntry)
                dismiss()
            }
        }
        dlg1.show(context.supportFragmentManager, "dialog")
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        activityUpdateKnob()
    }
}
