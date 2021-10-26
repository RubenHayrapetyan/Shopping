package com.ruben.shopping.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ruben.shopping.databinding.ItemIngredientsBinding
import com.ruben.shopping.models.shop.IngredientsList

class IngredientsRecyclerAdapter: RecyclerView.Adapter<IngredientsRecyclerAdapter.MyViewHolder>() {

    private val recipesArr = ArrayList<IngredientsList>()

    fun setData(recipesArr: List<IngredientsList>) {
        this.recipesArr.clear()
        this.recipesArr.addAll(recipesArr)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIngredientsBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(recipesArr[position], position)
    }

    override fun getItemCount(): Int {
        return recipesArr.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemIngredientsBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }

        fun bind(item: IngredientsList?, position: Int) {
            binding?.ingredientTitleTxt?.text = "${position + 1}. ${item?.ingredientTitle}"
        }
    }
}