package com.ruben.shopping.adapters

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ruben.shopping.databinding.ItemShopMissingIngredientsBinding
import com.ruben.shopping.interfaces.ItemClickListener
import com.ruben.shopping.models.shop.IngredientsList
import kotlin.collections.ArrayList

class MissingIngredientsRecAdapter : RecyclerView.Adapter<MissingIngredientsRecAdapter.MyViewHolder>() {

    private val recipesArr = ArrayList<IngredientsList>()
    private var listener: ItemClickListener? = null

    fun setListener(listener: ItemClickListener?) {
        this.listener = listener
    }

    fun setData(recipesArr: List<IngredientsList>) {
        this.recipesArr.clear()
        this.recipesArr.addAll(recipesArr)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShopMissingIngredientsBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(recipesArr[position], position)
    }

    override fun getItemCount(): Int {
        return recipesArr.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemShopMissingIngredientsBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }

        fun bind(item: IngredientsList?, position: Int) {

            binding?.missingIngredientTitleTxt?.text = "${position + 1}. ${item?.ingredientTitle}"
            binding?.missingIngredientCount?.text = item?.ingredientCount.toString()+ " " + item?.ingredientWeight
            binding?.missingIngredientCheckbox?.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    item?.let { it1 -> listener?.onItemClick(it1) }
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding?.missingIngredientCheckbox?.isChecked = false
                    }, 400)
                }
            }
        }
    }
}