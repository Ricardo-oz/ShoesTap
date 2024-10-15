package com.example.shoestap.view

import com.example.shoestap.model.Item

interface ProductContract {
    interface View {
        fun showProducts(products: List<Item>)
    }

    interface Presenter {
        fun getProducts()
    }
}

