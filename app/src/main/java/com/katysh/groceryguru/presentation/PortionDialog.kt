package com.katysh.groceryguru.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.katysh.groceryguru.R
import com.katysh.groceryguru.presentation.viewmodel.ProductsViewModel

class PortionDialog(
    private val context: AppCompatActivity,
    private val viewModel: ProductsViewModel
) : DialogFragment() {

    private lateinit var titleEt: EditText
    private lateinit var weightEt: EditText
    private lateinit var okButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val itemView: View = inflater.inflate(R.layout.dialog_portion, null)

        titleEt = itemView.findViewById(R.id.title_et)
        weightEt = itemView.findViewById(R.id.weight_et)
        okButton = itemView.findViewById(R.id.ok_button)

        observeViewModel()

        okButton.setOnClickListener {
            viewModel.validateAndSavePortion(titleEt.text.toString(), weightEt.text.toString())
            dismiss()
        }

        return itemView
    }

    private fun observeViewModel() {
        viewModel.errorLD.observe(this) {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        }
    }


}