package com.ruben.shopping.menu.refrigerator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ruben.shopping.R
import com.ruben.shopping.adapters.RefrigeratorIngredientsRecAdapter
import com.ruben.shopping.api.ResponseResult
import com.ruben.shopping.app.BaseFragment
import com.ruben.shopping.databinding.FragmentRefrigeratorBinding
import com.ruben.shopping.interfaces.IngredientCountListener
import com.ruben.shopping.models.refrigerator.RefrigeratorProductsList
import com.ruben.shopping.utils.hide
import com.ruben.shopping.utils.onThrottledClick
import com.ruben.shopping.utils.show
import okhttp3.MediaType
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class RefrigeratorFragment : BaseFragment(), IngredientCountListener {

    private lateinit var binding: FragmentRefrigeratorBinding
    private lateinit var refrigeratorIngredientsRecAdapter: RefrigeratorIngredientsRecAdapter
    private val viewModel by viewModel<RefrigeratorViewModel>()
    private val changedIngredientsJsonArray = JsonArray()
    private val needToBeRemovedIngredientsJsonArray = JsonArray()
    private val refProductsArr = ArrayList<RefrigeratorProductsList>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_refrigerator, container, false)

        initIngredientsRecycler()
        getRefrigeratorProducts()

        clickListeners()
        return binding.root
    }

    private fun clickListeners(){
        binding.saveChangesBtn.onThrottledClick {
            filterList()
        }

        binding.clearBtn.onThrottledClick {
            deleteAllProducts()
            hideBtn()
        }
    }

    private fun getRefrigeratorProducts() {
        viewModel.getProducts().observe(viewLifecycleOwner, {
            when (it) {
                is ResponseResult.Success -> {
                    val data = it.data.refProductsModel as ArrayList
                    refrigeratorIngredientsRecAdapter.setData(data)
                    if (data.isNotEmpty()){
                        binding.clearBtn.visibility = View.VISIBLE
                    }else{
                        binding.clearBtn.visibility = View.GONE
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

    private fun deleteAllProducts() {
        viewModel.deleteAllProducts().observe(viewLifecycleOwner, {
            when (it) {
                is ResponseResult.Success -> {
                    if (it.data.result == "true") {
                        Toast.makeText(context, "Удалён!", Toast.LENGTH_SHORT).show()
                        refreshFragment()
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

    private fun initIngredientsRecycler() {
        binding.missingIngredientsRecycler.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            refrigeratorIngredientsRecAdapter = RefrigeratorIngredientsRecAdapter()
            adapter = refrigeratorIngredientsRecAdapter
            refrigeratorIngredientsRecAdapter.setListener(this@RefrigeratorFragment)
        }
    }

    private fun showBtn() {
        binding.saveChangesBtn.visibility = View.VISIBLE
    }

    private fun hideBtn() {
        binding.saveChangesBtn.visibility = View.GONE
    }

    private fun filterList() {
        val removeSameItems = refProductsArr.distinct()
        removeSameItems.forEach {
            if (it.ingredientCount == 0f){
                val jsonObject = JsonObject()
                jsonObject.addProperty("id", it.ingredientId)
                needToBeRemovedIngredientsJsonArray.add(jsonObject)
            }else{
                val jsonObject = JsonObject()
                jsonObject.addProperty("id", it.ingredientId)
                jsonObject.addProperty("all", it.ingredientCount)
                changedIngredientsJsonArray.add(jsonObject)
            }
        }

        if (needToBeRemovedIngredientsJsonArray.size() > 0){
            removeSelectedIngredients()
        }else{
            update()
        }
    }

    private fun update() {
        val body = RequestBody.create(MediaType.parse("application/json"), changedIngredientsJsonArray.toString())

        viewModel.updateRefrigerator(body).observe(viewLifecycleOwner, {
            when (it) {
                is ResponseResult.Success -> {
                    if (it.data.isUpdated == "true") {
                        refreshFragment()
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

    private fun refreshFragment() = navController.navigate(R.id.action_refrigeratorFragment_self)

    private fun removeSelectedIngredients() {
        val body = RequestBody.create(MediaType.parse("application/json"), needToBeRemovedIngredientsJsonArray.toString())
        Log.i("myTestTt", "${needToBeRemovedIngredientsJsonArray.size()} = size")
        viewModel.deleteSelectedIngredients(body).observe(viewLifecycleOwner, {
            when (it) {
                is ResponseResult.Success -> {
                    if (it.data.result == "true") {
                        if (changedIngredientsJsonArray.size() > 0){
                            update()
                        }else{
                            binding.clearBtn.visibility = View.GONE
                            refreshFragment()
                        }
                        Toast.makeText(context, "Удалён", Toast.LENGTH_SHORT).show()
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

    override fun increaseCount(refProductList: RefrigeratorProductsList) {
        refProductList.ingredientCount = refProductList.ingredientCount?.plus(1f)
        refrigeratorIngredientsRecAdapter.notifyDataSetChanged()
        showBtn()
        refProductsArr.add(refProductList)
    }

    override fun decreaseCount(refProductList: RefrigeratorProductsList) {
        refProductList.ingredientCount?.let {
            if (refProductList.ingredientCount!!.minus(1f) >= 0f ) {
                refProductList.ingredientCount = refProductList.ingredientCount?.minus(1f)
            }else{
                refProductList.ingredientCount = 0f
            }
            refrigeratorIngredientsRecAdapter.notifyDataSetChanged()
            showBtn()
            refProductsArr.add(refProductList)
        }
    }
}