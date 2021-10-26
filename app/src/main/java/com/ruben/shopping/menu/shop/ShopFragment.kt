package com.ruben.shopping.menu.shop

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.ruben.shopping.R
import com.ruben.shopping.adapters.MissingIngredientsRecAdapter
import com.ruben.shopping.api.ResponseResult
import com.ruben.shopping.app.BaseFragment
import com.ruben.shopping.databinding.FragmentShopBinding
import com.ruben.shopping.interfaces.ItemClickListener
import com.ruben.shopping.models.shop.IngredientsList
import com.ruben.shopping.utils.hide
import com.ruben.shopping.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class ShopFragment : BaseFragment(), ItemClickListener {

    private lateinit var binding: FragmentShopBinding
    private lateinit var missingIngredientsRecAdapter: MissingIngredientsRecAdapter
    private val viewModel by viewModel<ShopViewModel>()
//    private val args: ShopFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_shop, container, false)

        initIngredientsRecycler()
        getShopProducts()

        return binding.root
    }

    private fun initIngredientsRecycler() {
        binding.missingIngredientsRecycler.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            missingIngredientsRecAdapter = MissingIngredientsRecAdapter()
            missingIngredientsRecAdapter.setListener(this@ShopFragment)
            adapter = missingIngredientsRecAdapter
        }
    }

    private fun getSavedRecipeId() = viewModel.getRecipeId()

    private fun getShopProducts() {
      //  val recipeId = args.recipeId
        val recipeId = getSavedRecipeId()
        if (recipeId != 0) {
            viewModel.getProducts(recipeId).observe(viewLifecycleOwner, {
                when (it) {
                    is ResponseResult.Success -> {
                        val data = it.data.refProductsModel as ArrayList
                        missingIngredientsRecAdapter.setData(data)
                    }
                    is ResponseResult.Failure -> {
                        val error = it.errorMessage
                        Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
                    }
                    is ResponseResult.Loading -> {
                        binding.loadingPb.show()
                    }
                    is ResponseResult.Complete -> {
                        binding.loadingPb.hide()
                    }
                }
            })
        }
    }

    override fun onItemClick(item: Any) {
        if (item is IngredientsList) {
            viewModel.addProductToRefrigerator(item.ingredientTitle!!, item.ingredientWeight!!, item.ingredientCount!!)
                    .observe(viewLifecycleOwner, {
                        when (it) {
                            is ResponseResult.Success -> {
                                if (it.data.isUpdated == "true") {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        getShopProducts()
                                    }, 400)
                                }
                            }
                            is ResponseResult.Failure -> {
                                val error = it.errorMessage
                                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
                            }
                            is ResponseResult.Loading -> {
                                binding.loadingPb.show()
                            }
                            is ResponseResult.Complete -> {
                                binding.loadingPb.hide()
                            }
                        }
                    })
        }
    }
}