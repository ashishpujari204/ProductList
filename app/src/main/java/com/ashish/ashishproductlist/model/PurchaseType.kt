package com.ashish.ashishproductlist.model

data class PurchaseType(
    val cartQty: Int,
    val displayName: String,
    val maxQtyLimit: Int,
    val minQtyLimit: Int,
    val purchaseType: String,
    val unitPrice: Double
)