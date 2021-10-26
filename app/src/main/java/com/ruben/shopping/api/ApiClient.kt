package com.ruben.shopping.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ruben.shopping.models.IsUpdated
import com.ruben.shopping.models.recipe.RecipeModel
import com.ruben.shopping.models.refrigerator.RefrigeratorProductsModel
import com.ruben.shopping.models.refrigerator.Result
import com.ruben.shopping.models.shop.IngredientsModel
import com.ruben.shopping.utils.AppURLs
import okhttp3.RequestBody
import org.json.JSONArray
import retrofit2.Response
import retrofit2.http.*

interface ApiClient {

    @FormUrlEncoded
    @POST(AppURLs.ADD_PRODUCT_TO_REFRIGERATOR)
    suspend fun addProductToRefrigerator(@Field("name") productName: String,
                                 @Field("weight") productWeightName: String,
                                 @Field("all") productWeightCount: Float
    ): Response<IsUpdated>

    @GET(AppURLs.RECIPES)
    suspend fun getRecipes(): Response<RecipeModel>

    @DELETE(AppURLs.DELETE_ALL)
    suspend fun deleteSelectedProducts(): Response<Result>

    @HTTP(method = "DELETE", path = AppURLs.DELETE_SELECTED, hasBody = true)
    suspend fun deleteSelectedProducts(@Body jsonArray: RequestBody): Response<Result>

    @GET(AppURLs.REFRIGERATOR_PRODUCTS)
    suspend fun getRefrigeratorIngredients(): Response<RefrigeratorProductsModel>

    @PUT(AppURLs.REFRIGERATOR_UPDATE)
    suspend fun updateRefrigerator(@Body jsonArray: RequestBody): Response<IsUpdated>

    @GET(AppURLs.CHECK_RECIPE)
    suspend fun checkRecipe(
        @Path(value = "recipe_id", encoded = true) recipeId: Int
    ): Response<IngredientsModel>
}