package com.example.shoestap
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        binding.btnBuy.setOnClickListener {
            if (cartItems.isEmpty()) {
                // Mostrar mensaje cuando el carrito esté vacío
                showEmptyCartMessage()
            } else {
                // Mostrar diálogo de confirmación de compra
                showPurchaseConfirmationDialog()
            }
        }

        // Resto del código...
    }

    private fun showEmptyCartMessage() {
        Toast.makeText(this, "Por favor agregue un producto a su canasta de compras.", Toast.LENGTH_SHORT).show()
    }

    private fun showPurchaseConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Desea comprar estos productos?")
            .setPositiveButton("Sí") { dialog, _ ->
                // Lógica para proceder con la compra
                Toast.makeText(this, "Compra realizada con éxito.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                // Lógica si el usuario cancela la compra
                Toast.makeText(this, "Compra cancelada.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .create()
            .show()
    }


    fun updateCartItemCount() {
        val count = cartItems.sumOf { it.quantity }
        binding.cartCount.text = "$count Productos"

        val totalPrice = calculateTotalPrice()
        binding.cartTotalPrice.text = "Total: $${String.format("%.3f", totalPrice)}"
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
