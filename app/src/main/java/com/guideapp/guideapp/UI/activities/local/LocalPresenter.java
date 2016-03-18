package com.guideapp.guideapp.ui.activities.local;

import android.support.annotation.NonNull;
import android.util.Log;
import com.guideapp.guideapp.model.ListResponse;
import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.network.GuideApi;
import com.guideapp.guideapp.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thales on 1/25/16.
 */
public class LocalPresenter implements LocalContract.UserActionsListener {
    private static final String TAG = LocalPresenter.class.getName();
    private final LocalContract.View mLocalView;

    /**
     * Simple constructor to use when creating a view from code.
     * @param localView View
     */
    public LocalPresenter(LocalContract.View localView) {
        this.mLocalView = localView;
    }

    @Override
    public void loadLocals(boolean forceUpdate) {
        GuideApi service = RestClient.getClient();
        Call<ListResponse<Local>> call = service.getLocals(5737664527466496L);

        call.enqueue(new Callback<ListResponse<Local>>() {
            @Override
            public void onResponse(Call<ListResponse<Local>> call, Response<ListResponse<Local>> response) {
                mLocalView.showLocals(response.body().getItems());
            }

            @Override
            public void onFailure(Call<ListResponse<Local>> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void openLocalDetails(@NonNull Local local) {
        mLocalView.showLocalDetailUi(local);
    }
}
