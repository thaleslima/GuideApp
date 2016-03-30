package com.guideapp.guideapp.network;

import com.guideapp.guideapp.model.SubCategory;
import com.guideapp.guideapp.model.wrapper.ListResponse;
import com.guideapp.guideapp.model.Local;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by thales on 3/16/16.
 */
public interface GuideApi {

    @GET("local")
    Observable<ListResponse<Local>> getLocals(
            @Query("idCity") Long idCity,
            @Query("idCategory") Long idCategory,
            @Query("idSubCategories") long[] idSubCategory);

    @GET("subcategory")
    Observable<ListResponse<SubCategory>> getSubCategories(@Query("idCategory") long idCategory);
}
