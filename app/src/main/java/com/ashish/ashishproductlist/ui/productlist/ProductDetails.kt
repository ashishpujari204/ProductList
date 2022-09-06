package com.ashish.ashishproductlist.ui.productlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ashish.ashishproductlist.R
import com.ashish.ashishproductlist.databinding.ActivityProductDetailsBinding
import com.ashish.ashishproductlist.db.DatabaseHelperImpl
import com.ashish.ashishproductlist.util.Constants.Companion.PRODUCT_ID
import com.ashish.ashishproductlist.util.Util
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetails : AppCompatActivity() {
    private lateinit var detailsBinding: ActivityProductDetailsBinding
    private lateinit var dbHelper: DatabaseHelperImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsBinding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(detailsBinding.root)
        initial()
    }

    private fun initial() {
        dbHelper = Util.getDBHelper(this)
        with(detailsBinding) {
            val productId = intent.getLongExtra(PRODUCT_ID, 1)
            CoroutineScope(Dispatchers.Main).launch {
                val favClass = dbHelper.getProduct(productId)
                productNameText.text = getString(R.string.product_name, favClass.productName)
                productPriceText.text = getString(R.string.product_price, favClass.productPrice)
                productRatingBar.isEnabled = false
                productRatingBar.rating = favClass.productRating.toFloat()
                if (favClass.isFav) {
                    favImage.setBackgroundResource(R.drawable.ic_baseline_favorite_red)
                } else {
                    favImage.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                }
                Glide.with(this@ProductDetails)
                    .load(favClass.productImage)
                    .placeholder(R.drawable.ic_baseline_android_24)
                    .into(productImage)
            }
        }
    }
}