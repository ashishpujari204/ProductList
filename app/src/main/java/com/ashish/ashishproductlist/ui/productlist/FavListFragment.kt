package com.ashish.ashishproductlist.ui.productlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ashish.ashishproductlist.databinding.FragmentProductListBinding
import com.ashish.ashishproductlist.db.DatabaseHelperImpl
import com.ashish.ashishproductlist.db.FavClass
import com.ashish.ashishproductlist.util.Constants.Companion.PRODUCT_ID
import com.ashish.ashishproductlist.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var dbHelper: DatabaseHelperImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initial()
    }

    override fun onStart() {
        super.onStart()
        dbHelper = Util.getDBHelper(requireContext())
    }

    private fun initial() {
        getFavProductList()
    }

    private fun getFavProductList() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = dbHelper.getAllFav()
            productAdapter = ProductAdapter(
                result as ArrayList<FavClass>,
                onItemClick = { productDto, _ ->
                    goToDetails(productDto)
                },
                onFavImageClick = { productDto, _ ->
                    updateFavProduct(productDto)
                }
            )
            binding.productList.adapter = productAdapter
        }
    }

    private fun updateFavProduct(productDetails: FavClass) {
        productDetails.isFav = !productDetails.isFav
        CoroutineScope(Dispatchers.Main).launch {
            dbHelper.update(productDetails)
        }
        getFavProductList()
    }

    private fun goToDetails(productDetails: FavClass) {
        Intent(requireActivity(), ProductDetails::class.java).apply {
            putExtra(PRODUCT_ID, productDetails.productId)
            startActivity(this)
        }
    }
}