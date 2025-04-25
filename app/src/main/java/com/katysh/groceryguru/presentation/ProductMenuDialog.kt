package com.katysh.groceryguru.presentation

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.katysh.groceryguru.R
import com.katysh.groceryguru.model.Portion
import com.katysh.groceryguru.presentation.viewmodel.ProductsViewModel

class ProductMenuDialog(
    private val context: AppCompatActivity,
    private val viewModel: ProductsViewModel,
    private val activityUpdateKnob: () -> Unit //todo разве нам это нужно? нах нам тогда ld?
) : DialogFragment() {
    private lateinit var titleTv: TextView
    private lateinit var archiveButton: Button
    private lateinit var editButton: Button
    private lateinit var addPortionButton: Button
    private lateinit var portionLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val itemView: View = inflater.inflate(R.layout.dialog_product_menu, null)

        titleTv = itemView.findViewById(R.id.title_text_view)
        archiveButton = itemView.findViewById(R.id.archive_button)
        editButton = itemView.findViewById(R.id.edit_button)
        addPortionButton = itemView.findViewById(R.id.add_portion_button)
        portionLayout = itemView.findViewById(R.id.portion_layout)

        observeViewModel()

        return itemView
    }

    private fun observeViewModel() {
        viewModel.selectedProductLD.observe(this) { pwp ->
            titleTv.text = pwp.product.title
            archiveButton.setOnClickListener {
                //todo
            }
            editButton.setOnClickListener {
                startActivity(ProductEditActivity.newIntent(context, pwp.product))
            }
            addPortionButton.setOnClickListener {
                val dialog = PortionDialog(context, viewModel)
                dialog.show(context.supportFragmentManager, "TaskMenuDialog")
            }
            fillPortionLayout(pwp.portions)
        }
    }

    private fun fillPortionLayout(portions: List<Portion>) {
        portionLayout.removeAllViews()
        for (portion in portions) {

            val textView = TextView(context).apply {
                text = "${portion.title} ${portion.weight}"
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER
                }
            }
            portionLayout.addView(textView)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        activityUpdateKnob()
    }
}