package com.guideapp.guideapp.ui.views.local;

import android.content.Context;
import com.guideapp.guideapp.model.SubCategory;
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
public class LocalActivityPresenter implements LocalContract.UserActionsActivityListener {
    private static final String TAG = LocalActivityPresenter.class.getName();
    private final LocalContract.ViewActivity mLocalViewActivity;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param localViewActivity ViewActivity
     * @param context The Context the view is running in
     */
    public LocalActivityPresenter(LocalContract.ViewActivity localViewActivity, Context context) {
        this.mLocalViewActivity = localViewActivity;
        this.mCompositeSubscription = new CompositeSubscription();
        this.mContext = context;
    }


    @Override
    public void loadFilters(long idCategory) {
        GuideApi service = RestClient.getClient(mContext);

        mCompositeSubscription.add(service.getSubCategories(idCategory)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ListResponse<SubCategory>>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onNext(ListResponse<SubCategory> listResponse) {
                                   mLocalViewActivity.showFilter(listResponse.getItems());
                               }
                           }
                )
        );
    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.unsubscribe();
    }
}
