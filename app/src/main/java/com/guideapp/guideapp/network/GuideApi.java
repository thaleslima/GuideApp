package com.guideapp.guideapp.network;

import com.guideapp.guideapp.model.ListResponse;
import com.guideapp.guideapp.model.Local;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by thales on 3/16/16.
 */
public interface GuideApi {

    @GET("local")
    Call<ListResponse<Local>> getLocals(@Query("idCity") Long idCity);

}
