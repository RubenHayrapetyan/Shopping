package com.ruben.shopping.menu.recipe.page1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.ruben.shopping.R
import com.ruben.shopping.adapters.RecipeRecyclerAdapter
import com.ruben.shopping.api.ResponseResult
import com.ruben.shopping.app.BaseFragment
import com.ruben.shopping.databinding.FragmentRecipeBinding
import com.ruben.shopping.interfaces.RecipeClickListener
import com.ruben.shopping.models.recipe.Recipe
import com.ruben.shopping.utils.hide
import com.ruben.shopping.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipeFragment : BaseFragment(), RecipeClickListener {

    private lateinit var binding: FragmentRecipeBinding
    private lateinit var recipeRecyclerAdapter: RecipeRecyclerAdapter
    private val viewModel by viewModel<RecipeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        initRecipesRecycler()
        getRecipes()
        return binding.root
    }

    private fun getRecipes() {
        viewModel.getRecipes().observe(viewLifecycleOwner, {
            when (it) {
                is ResponseResult.Success -> {
                    val data = it.data.data
                    data?.let { it1 -> recipeRecyclerAdapter.setData(it1) }
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

    private fun saveRecipeId(recipeId: Int) = viewModel.setRecipeId(recipeId)

    private fun checkRecipe(recipeId: Int) {
        viewModel.checkRecipe(recipeId).observe(viewLifecycleOwner, {
            with(binding){
                when (it) {
                    is ResponseResult.Success -> {
                        if (it.data.refProductsModel.isEmpty()) {
                            messageGroup.visibility = View.VISIBLE
                            messageGroup2.visibility = View.GONE
                            Handler(Looper.getMainLooper()).postDelayed({
                                messageGroup.visibility = View.GONE
                            }, 2000)
                        }else{
                            messageGroup.visibility = View.GONE
                            messageGroup2.visibility = View.VISIBLE

                            Handler(Looper.getMainLooper()).postDelayed({
                                messageGroup2.visibility = View.GONE
                                saveRecipeId(recipeId)
                                val action = RecipeFragmentDirections.actionRecipeFragmentToShopFragment(recipeId)
                                navController.navigate(action)
                            }, 2000)
                        }
                    }
                    is ResponseResult.Failure -> {
                        val error = it.errorMessage
                        Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
                    }
                    is ResponseResult.Loading -> {
                        loadingPb.show()
                    }
                    is ResponseResult.Complete -> {
                        loadingPb.hide()
                    }
                }
            }
        })
    }

    private fun initRecipesRecycler() {
        binding.recipeRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            recipeRecyclerAdapter = RecipeRecyclerAdapter()
            adapter = recipeRecyclerAdapter
            recipeRecyclerAdapter.setListener(this@RecipeFragment)
        }
    }

    override fun onRecipeClick(recipe: Recipe) {
        val action = RecipeFragmentDirections.actionRecipeFragmentToRecipeFragment2(recipe)
        navController.navigate(action)
    }

    override fun onToUseBtnClick(recipe: Recipe) {
        recipe.recipeId?.let {
            checkRecipe(recipe.recipeId)
        }
    }
}