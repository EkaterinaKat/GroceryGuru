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
import androidx.lifecycle.ViewModelProvider
import com.katysh.groceryguru.R
import com.katysh.groceryguru.presentation.viewmodel.IngredientViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory

class IngredientDialog(
    private val context: AppCompatActivity,
    private val viewModelFactory: ViewModelFactory,
    private val onSaveListener: () -> Unit
) : DialogFragment() {

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[IngredientViewModel::class.java]
    }

    private lateinit var proteinEt: EditText
    private lateinit var fatEt: EditText
    private lateinit var carbEt: EditText
    private lateinit var weightEt: EditText
    private lateinit var okButton: Button
    private lateinit var titleEt: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val itemView: View = inflater.inflate(R.layout.dialog_ingredient, null)

        proteinEt = itemView.findViewById(R.id.proteinEt)
        fatEt = itemView.findViewById(R.id.fatEt)
        carbEt = itemView.findViewById(R.id.carbEt)
        weightEt = itemView.findViewById(R.id.weightEt)
        okButton = itemView.findViewById(R.id.okButton)
        titleEt = itemView.findViewById(R.id.titleEt)

        observeViewModel()

        okButton.setOnClickListener {
            viewModel.validateAndSaveIngredient(
                proteinEt.text.toString(),
                fatEt.text.toString(),
                carbEt.text.toString(),
                weightEt.text.toString(),
                titleEt.text.toString()
            )
        }

        return itemView
    }

    private fun observeViewModel() {
        viewModel.errorLD.observe(this) {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        }
        viewModel.shouldFinishDialogLD.observe(this) {
            onSaveListener()
            dismiss()
        }
    }


}