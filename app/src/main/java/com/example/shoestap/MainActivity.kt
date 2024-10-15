package com.example.shoestap
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shoestap.databinding.ActivityMainBinding
import com.example.shoestap.model.CartItem
import com.example.shoestap.model.Item
import com.example.shoestap.view.CartFragment
import com.example.shoestap.view.ProductFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var cartItems: MutableList<CartItem> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("CartPreferences", Context.MODE_PRIVATE)

        val cartItemsJson = sharedPreferences.getString("cartItems", null)
        cartItems = if (!cartItemsJson.isNullOrEmpty()) {

            Gson().fromJson(cartItemsJson, object : TypeToken<MutableList<CartItem>>() {}.type)
        } else {
            mutableListOf()
        }
        if (cartItems.isNotEmpty()){
            Toast.makeText(this, "Tienes ${cartItems.size} en tu carrito", Toast.LENGTH_SHORT).show()
        }

        updateCartItemCount()

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, ProductFragment())
            .commit()

        binding.cartButton.setOnClickListener {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "El carrito está vacío!", Toast.LENGTH_SHORT).show()
            } else {
                val cartFragment = CartFragment()
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, cartFragment)
                    .addToBackStack(null)
                    .commit()
            }

        }

        binding.btnHome.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, ProductFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.exitButton.setOnClickListener{
           finish()
        }
    }

    fun updateCartItemCount() {
        val count = cartItems.sumOf { it.quantity }
        binding.cartCount.text = "$count item"

        val totalPrice = calculateTotalPrice()
        binding.cartTotalPrice.text = "Total: $${String.format("%.2f", totalPrice)}"
    }

    private fun calculateTotalPrice(): Float {
        var totalPrice = 0f
        for (cartItem in cartItems) {
            val itemPrice = cartItem.item.price * cartItem.quantity
            totalPrice += itemPrice
        }
        return totalPrice
    }

    fun addToCart(item: Item) {
        val existingCartItem = cartItems.find { it.item == item }
        if (existingCartItem != null) {
            existingCartItem.quantity++
        } else {
            val newCartItem = CartItem(item, 1, item.price)
            cartItems.add(newCartItem)
        }

        saveCartItems()
        updateCartItemCount()
    }

    private fun saveCartItems() {
        val editor = sharedPreferences.edit()
        val cartItemsJson = Gson().toJson(cartItems)
        editor.putString("cartItems", cartItemsJson)
        editor.commit()
    }


    fun removeFromCart(item: Item) {
        val existingCartItem = cartItems.find { it.item == item }
        if (existingCartItem != null) {
            if (existingCartItem.quantity > 1) {
                existingCartItem.quantity--
            } else {
                cartItems.remove(existingCartItem)
            }
        }

        saveCartItems()

        updateCartItemCount()
    }





    fun getCartItems(): List<CartItem> {
        return cartItems.toList()
    }
}
