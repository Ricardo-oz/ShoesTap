package com.example.shoestap.view
import android.content.Context
import android.content.SharedPreferences
import com.example.shoestap.model.CartItemListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoestap.MainActivity
import com.example.shoestap.databinding.FragmentCartBinding
import com.example.shoestap.model.CartAdapter
import com.example.shoestap.model.CartItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CartFragment : Fragment(), CartItemListener {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartItems: List<CartItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("CartPreferences", Context.MODE_PRIVATE)
        recyclerView = binding.recyclerView
        cartItems = getCartItemsFromSharedPreferences()

        val mainActivity = requireActivity() as MainActivity
        cartItems = mainActivity.getCartItems()

        cartAdapter = CartAdapter(cartItems)
        cartAdapter.setCartItemListener(this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCartItemDeleted(item: CartItem) {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.removeFromCart(item.item)
        val newCartItems = mainActivity.getCartItems()
        cartAdapter.setCartItems(newCartItems)
        cartAdapter.notifyDataSetChanged()
        mainActivity.updateCartItemCount()
    }

    private fun getCartItemsFromSharedPreferences(): List<CartItem> {
        val cartItemsJson = sharedPreferences.getString("cartItems", null)
        if (cartItemsJson != null) {
            val type = object : TypeToken<List<CartItem>>() {}.type
            return Gson().fromJson(cartItemsJson, type)
        }
        return emptyList()
    }



}
