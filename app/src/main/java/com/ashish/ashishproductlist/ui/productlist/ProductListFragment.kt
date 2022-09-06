package com.ashish.ashishproductlist.ui.productlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ashish.ashishproductlist.R
import com.ashish.ashishproductlist.databinding.FragmentProductListBinding
import com.ashish.ashishproductlist.db.DatabaseHelperImpl
import com.ashish.ashishproductlist.db.FavClass
import com.ashish.ashishproductlist.model.toFavDto
import com.ashish.ashishproductlist.rest.Status
import com.ashish.ashishproductlist.util.Constants.Companion.PRODUCT_ID
import com.ashish.ashishproductlist.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding
    private val productListViewModel by viewModel<ProductListViewModel>()
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
        if (Util.verifyAvailableNetwork(requireActivity())) {
            getProductList()
        } else {
            showToast(getString(R.string.internet))
        }
    }

    private fun getProductList() {
        productListViewModel.getProductList().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    hideProgressBar()
                    resource.data?.let { products ->
                        val productList = products.products
                        productList.forEach {
                            CoroutineScope(Dispatchers.Default).launch {
                                dbHelper.insertOrUpdate(it.toFavDto())
                            }
                        }
                        CoroutineScope(Dispatchers.Main).launch {
                            val favClass = dbHelper.getFav()
                            productAdapter = ProductAdapter(
                                favClass as ArrayList<FavClass>,
                                onItemClick = { favClassObject, _ ->
                                    goToDetails(favClassObject)
                                },
                                onFavImageClick = { favClassObject, index ->
                                    updateFavProduct(favClassObject, index)
                                }
                            )
                            binding.productList.adapter = productAdapter
                        }
                    }
                }
                Status.LOADING -> showProgressBar()
                Status.ERROR -> {
                    hideProgressBar()
                    resource.message?.let { message -> showToast(message) }
                }
            }
        }
    }

    private fun updateFavProduct(productDetails: FavClass, index: Int) {
        productDetails.isFav = !productDetails.isFav
        CoroutineScope(Dispatchers.Main).launch {
            dbHelper.update(productDetails)
        }
        productAdapter.notifyItemChanged(index)
    }

    private fun goToDetails(productDetails: FavClass) {
        Intent(requireActivity(), ProductDetails::class.java).apply {
            putExtra(PRODUCT_ID, productDetails.productId)
            startActivity(this)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }

    private fun showProgressBar() {
        binding.progressBar.isVisible = true
    }

    private fun hideProgressBar() {
        binding.progressBar.isVisible = false
    }
}