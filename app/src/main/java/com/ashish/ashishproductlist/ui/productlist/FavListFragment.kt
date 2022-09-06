package com.ashish.ashishproductlist.ui.productlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ashish.ashishproductlist.R
import com.ashish.ashishproductlist.databinding.FragmentProductListBinding
import com.ashish.ashishproductlist.db.DatabaseHelperImpl
import com.ashish.ashishproductlist.db.FavClass
import com.ashish.ashishproductlist.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavListFragment : Fragment() {

    lateinit var binding: FragmentProductListBinding
    lateinit var productAdapter: ProductAdapter
    lateinit var dbHelper: DatabaseHelperImpl

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
        if (Util.verifyAvailableNetwork(requireActivity())) {
            getProductList()
        } else {
            showToast(getString(R.string.internet))
        }
    }

    private fun getProductList() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = dbHelper.getAllFav()
            productAdapter = ProductAdapter(
                result as ArrayList<FavClass>,
                onItemClick = { productDto, _ ->
                    goToDetails(productDto)
                },
                onFavImageClick = { productDto, index ->
                    updateFavProduct(productDto, index)
                }
            )
            binding.productList.adapter = productAdapter
        }
    }

    private fun updateFavProduct(productDetails: FavClass, index: Int) {
        if (productDetails.isFav) {
            productDetails.isFav = false
            CoroutineScope(Dispatchers.Main).launch {
                dbHelper.update(productDetails)
            }
        } else {
            productDetails.isFav = true
            CoroutineScope(Dispatchers.Main).launch {
                dbHelper.update(productDetails)
            }
        }
        productAdapter.notifyItemChanged(index)
    }

    private fun goToDetails(productDetails: FavClass) {
        Intent(requireActivity(), ProductDetails::class.java).apply {
            putExtra(Util.PRODUCT_ID, productDetails.productId)
            startActivity(this)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }
}