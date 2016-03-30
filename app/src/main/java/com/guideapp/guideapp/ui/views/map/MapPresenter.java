package com.guideapp.guideapp.ui.views.map;

import android.content.Context;
import android.util.Log;

import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.model.wrapper.ListResponse;
import com.guideapp.guideapp.network.GuideApi;
import com.guideapp.guideapp.network.RestClient;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by thales on 1/25/16.
 */
public class MapPresenter implements MapContract.UserActionsListener {
    private static final String TAG = MapPresenter.class.getName();
    private final MapContract.View mView;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param view ViewFragment
     */
    public MapPresenter(MapContract.View view, Context context) {
        this.mContext = context;
        this.mView = view;
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void loadLocals(long idCity, long idCategory, long[] idSubCategory) {
        GuideApi service = RestClient.getClient(mContext);

        Long idCategoryAux = idCategory == 0 ? null : idCategory;

        mCompositeSubscription.add(service.getLocals(idCity, idCategoryAux, idSubCategory)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ListResponse<Local>>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.i(TAG, e.getMessage());
                               }

                               @Override
                               public void onNext(ListResponse<Local> listResponse) {
                                   mView.showLocals(listResponse.getItems());
                               }
                           }
                )
        );
    }

    @Override
    public void loadLocalSummary(Local local) {
        mView.showLocalSummary(local);
    }

    @Override
    public void loadLocal(Local local) {
        mView.showLocal(local);
    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.unsubscribe();
    }
}
