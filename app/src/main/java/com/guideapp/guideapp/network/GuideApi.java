package com.guideapp.guideapp.network;

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

    @Headers("Cache-Control: max-age=114400")
    @GET("local")
    Observable<ListResponse<Local>> getLocals(@Query("idCity") Long idCity);
}
