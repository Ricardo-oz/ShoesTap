package com.example.shoestap.presenter

import com.example.shoestap.model.Item
import com.example.shoestap.view.ProductContract

class ProductPresenter(private val view: ProductContract.View) : ProductContract.Presenter {
    private val itemList: MutableList<Item> = mutableListOf()

    override fun getProducts() {
        itemList.addAll(creaDatos())
        view.showProducts(itemList)
    }

    private fun creaDatos(): List<Item> {
        val dataList = mutableListOf<Item>()

        dataList.add(Item("https://home.ripley.cl/store/Attachment/WOP/D311/2000388731655/2000388731655_2.jpg", "Botines hombre Guante", "Botines negros linea Villarica Waterproof", 59.9f))
        dataList.add(Item("https://home.ripley.cl/store/Attachment/WOP/D309/2000403382787/2000403382787_2.jpg", "Zapatilla Mujer Converse", " Zapatilla Converse mujer CHUCK TAYLOR", 47.0f))
        dataList.add(Item("https://store.bluepadel.cl/cdn/shop/files/ezgif-2-3ddad07dfe.jpg", "Zapatilla Asicx Negras", "Padel Unisex Negras con Azul", 35.9f))
        dataList.add(Item("https://home.ripley.cl/store/Attachment/WOP/D311/2000402207593/2000402207593-2.jpg", "Zapatilla Puma", "Zapatilla Hombre Graviton Pro", 59.0f))
        dataList.add(Item("https://traukochile.cl/cdn/shop/products/Milano_Negro_1_647c07db-3f03-4eda-8b42-1b08ee797714_700x.png", "Zapato Mujer Milano ", "Zapato Cuero Alto Negro", 89.0f))
        dataList.add(Item("https://traukochile.cl/cdn/shop/files/DSC07191_700x.png", "Zapato Confort Line", "Zapato Mujer Cafe ", 50.0f))
        dataList.add(Item("https://traukochile.cl/cdn/shop/files/DSC07194_86c28f57-1ed9-4633-9bfd-1e65a3a163f1_1482x1482.png", "Botin Mujer Gamuza", "Botin Alto Color Marron de Cuero ", 79.0f))
        dataList.add(Item("https://traukochile.cl/cdn/shop/files/DSC07193_700x.png", "Botin Torino Mujer", "Botin Cafe caña media", 67.0f))
        dataList.add(Item("https://rimage.ripley.cl/home.ripley/Attachment/MKP/3036/MPM10000708500/full_image-7.jpg", "Zapatilla Skechers Niña ", "Zapatilla Urbanio Negro Nina", 42.0f))
        dataList.add(Item("https://home.ripley.cl/store/Attachment/WOP/D312/2000403095649/2000403095649_2.jpg", "Zapatilla Infantil Adidas ", "Zapatilla Unisex Infantil Blanco", 45.0f))

        return dataList
    }

}

