package com.guideapp.guideapp.ui.views.localdetail;

import com.guideapp.guideapp.R;
import com.guideapp.guideapp.infrastructure.ValidationUtil;
import com.guideapp.guideapp.model.Local;
import com.guideapp.guideapp.model.LocalDetail;
import com.guideapp.guideapp.ui.adapters.LocalDetailAdapter;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Observer;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by thales on 1/25/16.
 */
public class LocalDetailPresenter implements LocalDetailContract.UserActionsListener {
    private static final String TAG = LocalDetailPresenter.class.getName();
    private final LocalDetailContract.View mLocalView;
    private CompositeSubscription mCompositeSubscription;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param localView ViewFragment
     */
    public LocalDetailPresenter(LocalDetailContract.View localView) {
        this.mLocalView = localView;
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void loadLocal(Local local) {
        Observable<List<LocalDetail>> listObservable = Observable.just(createListLocalDetail(local));
        listObservable.subscribe(new Observer<List<LocalDetail>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<LocalDetail> localDetails) {
                mLocalView.showLocalDetail(localDetails);
            }
        });
    }

    /**
     * Create items of the recycle view
     * @param local Local Object
     * @return Return item of the list
     */
    private static List<LocalDetail> createListLocalDetail(Local local) {
        List<LocalDetail> list = new ArrayList<>();


        list.add(new LocalDetail(R.drawable.ic_info_grey_72_24dp,
                local.getDescriptionSubCategories(), true, LocalDetailAdapter.LOCAL_DETAIL));

        if (!ValidationUtil.nullOrEmpty(local.getSite())) {
            list.add(new LocalDetail(R.drawable.ic_language_grey_72_24dp,
                    local.getSite(), true, LocalDetailAdapter.LOCAL_DETAIL));
        }

        if (!ValidationUtil.nullOrEmpty(local.getPhone())) {
            list.add(new LocalDetail(R.drawable.ic_call_grey_72_24dp,
                    local.getPhone(), true, LocalDetailAdapter.LOCAL_DETAIL));
        }

        if (!ValidationUtil.nullOrEmpty(local.getDetail())) {
            list.add(new LocalDetail(R.drawable.ic_apps_grey_72_24dp,
                    local.getDetail(), true, LocalDetailAdapter.LOCAL_DETAIL));
        }

        if (!ValidationUtil.nullOrEmpty(local.getAddress())) {
            list.add(new LocalDetail(R.drawable.ic_location_on_grey_72_24dp,
                    local.getAddress(), false,
                    LocalDetailAdapter.LOCAL_DETAIL));
        }

        list.add(new LocalDetail(true, LocalDetailAdapter.LOCAL_DETAIL_MAP,
                local.getLatitude(), local.getLongitude()));


        list.add(new LocalDetail(true, LocalDetailAdapter.LOCAL_DETAIL_TITLE_OPINION));

        return list;
    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.unsubscribe();
    }
}
