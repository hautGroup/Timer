package com.lolpremade.timer.Model.API.Internal;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by cjk on 16/9/27.
 */

class APICallAdapterFactory extends CallAdapter.Factory {
    private final RxJavaCallAdapterFactory original;

    private APICallAdapterFactory() {
        original = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
    }

    public static CallAdapter.Factory create() {
        return new APICallAdapterFactory();
    }

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper implements CallAdapter<Observable<?>> {
        private final Retrofit retrofit;
        private final CallAdapter<?> wrapped;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<?> wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R> Observable<?> adapt(Call<R> call) {

            return ((Observable) wrapped.adapt(call)).onErrorResumeNext(e -> {
                if (e instanceof HttpException) {
                    HttpException httpException = (HttpException) e;
                    try {
                        JSONObject x = new JSONObject(httpException.response().errorBody().string());
                        String state = x.optString("state", "");
                        String message = x.optString("reason", "网络好像有问题");
                        Throwable throwable = new Throwable(state);
                        return Observable.error(new Throwable(message, throwable));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                return Observable.error(new Throwable("网络好像有问题!", (Throwable) e));
            });
        }
    }
}
