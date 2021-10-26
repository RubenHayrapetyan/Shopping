package com.ruben.shopping.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ruben.shopping.databinding.ItemRefrigeratorMissingIngredientsBinding
import com.ruben.shopping.interfaces.IngredientCountListener
import com.ruben.shopping.interfaces.ItemClickListener
import com.ruben.shopping.models.refrigerator.RefrigeratorProductsList

class RefrigeratorIngredientsRecAdapter :
    RecyclerView.Adapter<RefrigeratorIngredientsRecAdapter.MyViewHolder>() {

    private val recipesArr = ArrayList<RefrigeratorProductsList>()

    private var listener: IngredientCountListener? = null

    fun setListener(listener: IngredientCountListener?) {
        this.listener = listener
    }

    fun setData(recipesArr: ArrayList<RefrigeratorProductsList>) {
        this.recipesArr.clear()
        this.recipesArr.addAll(recipesArr)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRefrigeratorMissingIngredientsBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(recipesArr[position], position)
    }

    override fun getItemCount(): Int {
        return recipesArr.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemRefrigeratorMissingIngredientsBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: RefrigeratorProductsList?, position: Int) {

            binding?.missingIngredientTitleTxt?.text = "${position + 1}. ${item?.ingredientTitle}"
            binding?.missingIngredientCount?.text =
                item?.ingredientCount.toString() + " " + item?.ingredientWeight

            binding?.addIngredientCountIcon?.setOnClickListener {
                item?.let { it1 -> listener?.increaseCount(it1) }
            }
            binding?.decreaseIngredientCountIcon?.setOnClickListener {
                    item?.let { it1 -> listener?.decreaseCount(it1) }
            }
        }
    }
}