package com.katysh.groceryguru.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.databinding.FragmentProductListBinding
import com.katysh.groceryguru.model.ProductType
import com.katysh.groceryguru.model.ProductWithPortions
import com.katysh.groceryguru.presentation.recycleview.ProductAdapter
import com.katysh.groceryguru.presentation.viewmodel.ProductListViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class ProductListFragment : Fragment() {
    var showArchived = false
        set(value) {
            field = value
            updateProductList()
        }

    private var _binding: FragmentProductListBinding? = null
    private val binding: FragmentProductListBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    private val component by lazy {
        (requireActivity().application as GroceryGuruApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ProductListViewModel::class.java]
    }

    private val adapter = ProductAdapter()
    private var selectedType: ProductType? = null

    private lateinit var onProductClickListener: OnProductClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

        binding.productsRv.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.productsRv.adapter = adapter
        adapter.onClickListener = {
            onProductClickListener.onProductClick(it)
        }

        binding.searchEt.addTextChangedListener(textWatcher)
        adjustTypePane()
        updateProductList()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)

        if (context is OnProductClickListener) {
            onProductClickListener = context
        } else {
            throw RuntimeException("Activity must implement OnProductClickListener")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val textWatcher = object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            updateProductList()
        }
    }

    private fun observeViewModel() {
        viewModel.productsLD.observe(requireActivity()) {
            adapter.setProducts(it)
        }
    }

    fun updateProductList() {
        viewModel.updateProductList(binding.searchEt.text.toString(), selectedType, showArchived)
    }

    private fun adjustTypePane() {
        for (type in ProductType.entries) {
            val button = Button(requireActivity())
            button.text = type.name.substring(0, 1)
            button.setBackgroundResource(type.color)
            val params = LinearLayout.LayoutParams(0, WRAP_CONTENT, 1.0f)
            button.layoutParams = params
            button.setOnClickListener {
                selectedType = type
                updateProductList()
            }
            binding.typeLayout.addView(button)
        }
    }

    interface OnProductClickListener {
        fun onProductClick(product: ProductWithPortions)
    }

    companion object {
        fun newInstance() = ProductListFragment()
    }
}