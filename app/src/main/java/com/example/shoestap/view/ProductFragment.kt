package com.example.shoestap.view


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoestap.R
import com.example.shoestap.databinding.FragmentProductBinding
import com.example.shoestap.model.Item
import com.example.shoestap.presenter.ProductPresenter
import com.example.shoestap.view.ProductContract
import com.example.shoestap.model.ProductAdapter

class ProductFragment : Fragment(), ProductContract.View, ProductAdapter.OnItemClickListener {
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var presenter: ProductContract.Presenter
    private lateinit var allProducts: List<Item>
    private lateinit var filteredProducts: MutableList<Item>
    private lateinit var tvNoResults: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        productAdapter = ProductAdapter()
        tvNoResults = binding.tvNoResults

        productAdapter.setOnItemClickListener(this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }

        presenter = ProductPresenter(this)
        presenter.getProducts()


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se requiere acción antes de cambiar el texto
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Filtrar los productos según el texto ingresado
                filterProducts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // No se requiere acción después de cambiar el texto
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onItemClick(item: Item) {
        val detailsFragment = DetailsFragment.newInstance(item)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun showProducts(products: List<Item>) {
        allProducts = products
        filteredProducts = products.toMutableList()
        productAdapter.setProducts(filteredProducts)
        if (filteredProducts.isEmpty()) {
            tvNoResults.visibility = View.VISIBLE
        } else {
            tvNoResults.visibility = View.GONE
        }
    }


    private fun filterProducts(text: String) {
        val lowercaseText = text.lowercase()

        filteredProducts.clear()
        filteredProducts.addAll(allProducts.filter {
            it.name.lowercase().contains(lowercaseText)
        })

        productAdapter.setProducts(filteredProducts)

        if (filteredProducts.isEmpty() && lowercaseText.isNotEmpty()) {
            tvNoResults.visibility = View.VISIBLE
        } else {
            tvNoResults.visibility = View.GONE
        }
    }









}


