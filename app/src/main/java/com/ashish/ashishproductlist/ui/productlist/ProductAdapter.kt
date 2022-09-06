package com.ashish.ashishproductlist.ui.productlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashish.ashishproductlist.R
import com.ashish.ashishproductlist.databinding.ProductItemViewBinding
import com.ashish.ashishproductlist.db.FavClass
import com.bumptech.glide.Glide

class ProductAdapter(
    items: ArrayList<FavClass>,
    private val onItemClick: (product: FavClass, view: View) -> Unit,
    private val onFavImageClick: (product: FavClass, index: Int) -> Unit
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var items = ArrayList<FavClass>()

    init {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ProductItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(private val binding: ProductItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var productDtoObject: FavClass

        fun bind(fav: FavClass, index: Int) = with(itemView) {
            productDtoObject = fav
            with(binding) {
                fav.apply {
                    productNameText.text = context.getString(R.string.product_name, productName)
                    productPriceText.text =
                        context.getString(R.string.product_price, productPrice)
                }
                setOnClickListener {
                    onItemClick(fav, itemView)
                }
                if (fav.isFav) {
                    favImage.setBackgroundResource(R.drawable.ic_baseline_favorite_red)
                } else {
                    favImage.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                }
                favImage.setOnClickListener { onFavImageClick(productDtoObject, index) }
            }
            Glide.with(this)
                .load(fav.productImage)
                .placeholder(R.drawable.ic_baseline_android_24)
                .into(binding.productImage)
        }
    }
}