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
import com.katysh.groceryguru.model.EntryWithProduct
import com.katysh.groceryguru.presentation.viewmodel.MainActivityViewModel

class EntryMenuDialog(
    private val context: AppCompatActivity,
    private val entryWithProduct: EntryWithProduct,
    private val viewModel: MainActivityViewModel,
    private val activityUpdateKnob: () -> Unit
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val itemView: View = inflater.inflate(R.layout.dialog_entry_menu, null)

        itemView.findViewById<TextView>(R.id.title_text_view).text = entryWithProduct.product.getFullInfo()
        itemView.findViewById<Button>(R.id.archive_button).setOnClickListener {
            openDeleteDialog()
        }

        return itemView
    }

    private fun openDeleteDialog() {
        val dlg1: DialogFragment = QuestionDialog("Delete?") {
            if (it) {
                viewModel.delete(entryWithProduct.entry)
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
