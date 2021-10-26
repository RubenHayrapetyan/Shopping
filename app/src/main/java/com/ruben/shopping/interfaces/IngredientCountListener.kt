package com.ruben.shopping.interfaces

import com.ruben.shopping.models.refrigerator.RefrigeratorProductsList

interface IngredientCountListener {

    fun increaseCount(refProductList: RefrigeratorProductsList)
    fun decreaseCount(refProductList: RefrigeratorProductsList)
}