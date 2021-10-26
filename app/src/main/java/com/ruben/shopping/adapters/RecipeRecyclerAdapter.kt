package com.ruben.shopping.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ruben.shopping.databinding.ItemRecipeBinding
import com.ruben.shopping.interfaces.ItemClickListener
import com.ruben.shopping.interfaces.RecipeClickListener
import com.ruben.shopping.models.recipe.Recipe
import com.ruben.shopping.models.recipe.RecipeModel
import com.ruben.shopping.utils.onThrottledClick

class RecipeRecyclerAdapter : RecyclerView.Adapter<RecipeRecyclerAdapter.MyViewHolder>() {

    private val recipesArr = ArrayList<Recipe>()
    private var listener: RecipeClickListener? = null

    fun setListener(listener: RecipeClickListener?) {
        this.listener = listener
    }

    fun setData(recipesArr: List<Recipe>) {
        this.recipesArr.clear()
        this.recipesArr.addAll(recipesArr)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //  holder.binding?.coursePositionLinear?.id = coursesArray[position].id
        //  holder.binding?.courseHeaderTxt?.text = coursesArray[position].courseName
        holder.bind(recipesArr[position], position)
    }

    override fun getItemCount(): Int {
        return recipesArr.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemRecipeBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }

        fun bind(item: Recipe?, position: Int) {
            binding?.recipeTitle?.onThrottledClick {
                item?.let { it1 -> listener?.onRecipeClick(it1) }
            }
            binding?.recipeBtn?.onThrottledClick {
                item?.let { it1 -> listener?.onToUseBtnClick(it1) }
            }
            binding?.recipeNumberTxt?.text = "${position + 1}."
            binding?.recipeTitle?.text = item?.recipeTitle
        }
    }
}