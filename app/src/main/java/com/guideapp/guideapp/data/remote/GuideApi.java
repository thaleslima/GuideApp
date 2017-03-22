package com.guideapp.guideapp.data.remote;

import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.model.SubCategory;
import com.guideapp.guideapp.model.wrapper.ListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GuideApi {

    @GET("local")
    Call<ListResponse<Local>> getLocals(
            @Query("idCity") Long idCity,
            @Query("idCategory") Long idCategory,
            @Query("idSubCategories") long[] idSubCategory);

    @GET("local")
    Call<ListResponse<Local>> getLocals(
            @Query("idCity") Long idCity);

    @GET("subcategory")
    Call<ListResponse<SubCategory>> getSubCategories(@Query("idCategory") long idCategory);
}
