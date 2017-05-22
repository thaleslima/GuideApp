package com.guideapp.data.remote

import com.guideapp.model.Local
import com.guideapp.model.SubCategory
import com.guideapp.model.wrapper.ListResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GuideApi {

    @GET("local")
    fun getLocals(
            @Query("idCity") idCity: Long?,
            @Query("idCategory") idCategory: Long?,
            @Query("idSubCategories") idSubCategory: LongArray): Call<ListResponse<Local>>

    @GET("local")
    fun getLocals(
            @Query("idCity") idCity: Long?): Call<ListResponse<Local>>

    @GET("subcategory")
    fun getSubCategories(@Query("idCategory") idCategory: Long): Call<ListResponse<SubCategory>>
}
