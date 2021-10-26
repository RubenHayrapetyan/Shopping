package com.ruben.shopping.models.recipe

import android.os.Parcel
import android.os.Parcelable
import com.ruben.shopping.models.shop.IngredientsList
import com.squareup.moshi.Json

@Suppress("UNREACHABLE_CODE")
data class Recipe (
    @field:Json(name = "id") val recipeId: Int?,

    val recipeNumber: String?,

    @field:Json(name = "name_recept") val recipeTitle: String?,

    @field:Json(name = "reception") val reception: String?,

    @field:Json(name = "products") val ingredientsList: List<IngredientsList>?
        ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("ingredientsList")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(recipeId)
        parcel.writeString(recipeNumber)
        parcel.writeString(recipeTitle)
        parcel.writeString(reception)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}