package com.ruben.shopping.menu.recipe.page1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.ruben.shopping.adapters.IngredientsRecyclerAdapter
import com.ruben.shopping.api.ResponseResult
import com.ruben.shopping.app.BaseFragment
import com.ruben.shopping.databinding.FragmentRecipe2Binding
import com.ruben.shopping.utils.hide
import com.ruben.shopping.utils.onThrottledClick
import com.ruben.shopping.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipeFragment2 : BaseFragment() {

    private lateinit var binding: FragmentRecipe2Binding
    private lateinit var ingredientsRecyclerAdapter: IngredientsRecyclerAdapter
    private val viewModel by viewModel<RecipeViewModel>()
    private val args: RecipeFragment2Args by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipe2Binding.inflate(inflater, container, false)
        clickListeners()
        initRecipesRecycler()
        setUI()
        return binding.root
    }

    private fun clickListeners() {
        binding.toUseBtn.onThrottledClick {
            args.recipieModel.recipeId?.let {
                checkRecipe(it)
            }
        }
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
                        } else {
                            messageGroup.visibility = View.GONE
                            messageGroup2.visibility = View.VISIBLE

                            Handler(Looper.getMainLooper()).postDelayed({
                                messageGroup2.visibility = View.GONE
                                saveRecipeId(recipeId)
                                val action = RecipeFragment2Directions.actionRecipeFragment2ToShopFragment(recipeId)
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

    private fun setUI() {
        binding.recipeTitle.text = args.recipieModel.reception
    }

    private fun initRecipesRecycler() {
        binding.ingredientsListRecycler.apply {
            val ingredientsList = args.recipieModel.ingredientsList
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            ingredientsRecyclerAdapter = IngredientsRecyclerAdapter()
            adapter = ingredientsRecyclerAdapter
            ingredientsList?.let { ingredientsRecyclerAdapter.setData(it) }
        }
    }
}