package com.guideapp.guideapp.ui.activities.local;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.guideapp.guideapp.model.wrapper.ListResponse;
import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.network.GuideApi;
import com.guideapp.guideapp.network.RestClient;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by thales on 1/25/16.
 */
public class LocalPresenter implements LocalContract.UserActionsListener {
    private static final String TAG = LocalPresenter.class.getName();
    private final LocalContract.View mLocalView;
    private CompositeSubscription mCompositeSubscription;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param localView View
     */
    public LocalPresenter(LocalContract.View localView) {
        this.mLocalView = localView;
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void loadLocals(boolean forceUpdate) {
        mLocalView.showProgressBar();

        GuideApi service = RestClient.getClient();

        mCompositeSubscription.add(service.getLocals(5737664527466496L)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ListResponse<Local>>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onNext(ListResponse<Local> listResponse) {
                                   mLocalView.showLocals(listResponse.getItems());
                                   mLocalView.hideProgressBar();
                               }
                           }
                )
        );

        /*
        mCompositeSubscription.add(new LocalBusiness().getLocals(5737664527466496L)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Local>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Local> locals) {
                        mLocalView.showLocals(locals);
                        mLocalView.hideProgressBar();
                    }
                })
        );

        /*
        mCompositeSubscription.add(service.getLocals(5737664527466496L)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ListResponse<Local>>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onNext(ListResponse<Local> listResponse) {
                                   mLocalView.showLocals(listResponse.getItems());
                                   mLocalView.hideProgressBar();
                               }
                           }
                )
        );

        /*
        Single<List<String>> tvShowSingle = Single.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                Thread.sleep(10000);
                return null;
            }
        });

        mCompositeSubscription.add(tvShowSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<List<String>>() {
                    @Override
                    public void onSuccess(List<String> tvShows) {
                        Log.d(TAG, "onSuccess: ");
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "onError: ");
                    }
                })
        );

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

        */
    }

    @Override
    public void openLocalDetails(@NonNull Local local, ImageView view) {
        mLocalView.showLocalDetailUi(local, view);
    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.unsubscribe();
    }
}
