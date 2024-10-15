package com.example.shoestap.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoestap.R

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productList: MutableList<Item> = mutableListOf()
    private var listener: OnItemClickListener? = null

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImageView: ImageView = itemView.findViewById(R.id.product_image)
        val productTitleTextView: TextView = itemView.findViewById(R.id.product_title)
        val productDescriptionTextView: TextView = itemView.findViewById(R.id.product_description)
        val productPriceTextView: TextView = itemView.findViewById(R.id.product_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        // Cargar la imagen con Glide
        Glide.with(holder.itemView)
            .load(product.img)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.productImageView)
        holder.productTitleTextView.text = product.name
        holder.productDescriptionTextView.text = product.des
        holder.productPriceTextView.text = product.price.toString()

        holder.itemView.setOnClickListener {
            listener?.onItemClick(product)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setProducts(products: List<Item>) {
        productList.clear()
        productList.addAll(products)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(item: Item)
    }
}
