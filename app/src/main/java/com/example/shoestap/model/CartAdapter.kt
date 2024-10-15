package com.example.shoestap.model

import com.example.shoestap.model.CartItemListener

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoestap.R


class CartAdapter(private var cartItems: List<CartItem>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var cartItemListener: CartItemListener? = null

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productImageView: ImageView = itemView.findViewById(R.id.product_image)
        private val productTitleTextView: TextView = itemView.findViewById(R.id.product_title)
        private val productDescriptionTextView: TextView =
            itemView.findViewById(R.id.product_description)
        private val productPriceTextView: TextView = itemView.findViewById(R.id.product_price)
        private val deleteButton: Button = itemView.findViewById(R.id.delete_button)
        private val productCantTextView: TextView = itemView.findViewById(R.id.product_cant)


        fun bind(cartItem: CartItem) {
            Glide.with(itemView)
                .load(cartItem.item.img)
                .placeholder(R.drawable.ic_launcher_background)
                .into(productImageView)
            productTitleTextView.text = cartItem.item.name
            productDescriptionTextView.text = cartItem.item.des
            productPriceTextView.text = cartItem.item.price.toString()
            productCantTextView.text = "${cartItem.quantity} unidad(s)"
            deleteButton.setOnClickListener {
                Toast.makeText(itemView.context, "Eliminando Unidad", Toast.LENGTH_SHORT).show()
                cartItemListener?.onCartItemDeleted(cartItem)


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cartproduct, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun setCartItems(cartItems: List<CartItem>) {
        this.cartItems = cartItems.toMutableList()
        notifyDataSetChanged()
    }


    fun setCartItemListener(listener: CartItemListener) {
        cartItemListener = listener
    }

}


