package com.app.turingrobot.helper.rx;


import com.app.turingrobot.entity.CoreEntity;
import com.app.turingrobot.multitype.base.ViewModel;
import com.app.turingrobot.ui.fragment.chat.model.LinkModel;
import com.app.turingrobot.ui.fragment.chat.model.TextMsgTargetModel;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Rx处理服务器返回
 * Created by YoKey.
 */
public class RxResultHelper {

    @SuppressWarnings("unchecked")
    public static <T extends CoreEntity> ObservableTransformer<T, ViewModel> transform() {

        return new ObservableTransformer<T, ViewModel>() {
            @Override
            public ObservableSource<ViewModel> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).flatMap(new Function<T, ObservableSource<ViewModel>>() {
                    @Override
                    public Observable<ViewModel> apply(T result) throws Exception {

                        final int code = result.getCode();

                        if (code == 200000) {
                            //链接类
                            return Observable.just(new LinkModel(result));
                        } else if (code == 100000) {
                            //文字类
                            return Observable.just(new TextMsgTargetModel(result));
                        }

                        return Observable.error(new RuntimeException("has no code found"));
                    }
                }).observeOn(AndroidSchedulers.mainThread());
            }
        };

    }


}