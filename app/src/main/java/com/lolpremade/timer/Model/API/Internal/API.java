package com.lolpremade.timer.Model.API.Internal;

import com.lolpremade.timer.Model.API.Converter.JsonConverterFactory;

import org.json.JSONObject;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sia on 16/9/26.
 */

public class API {

    protected static final BaseAPI base;

    static {
        base = base();
    }

    private static BaseAPI base() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.dynacno.com")
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(APICallAdapterFactory.create())
                .build();
        return retrofit.create(BaseAPI.class);
    }

    protected interface BaseAPI {

        @GET("/register/post")
        Observable<JSONObject> register(@Query("mobile") String mobile, @Query("nickname") String nickname,
                                        @Query("password") String password, @Query("email") String email);

        @GET("/validateEmail")
        Observable<JSONObject> y(@Query("email") String email);
    }
}