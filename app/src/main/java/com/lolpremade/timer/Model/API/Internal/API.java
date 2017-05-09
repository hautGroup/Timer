package com.lolpremade.timer.Model.API.Internal;

import com.lolpremade.timer.BuildConfig;
import com.lolpremade.timer.Model.API.Converter.JsonConverterFactory;

import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by cjk on 16/9/26.
 */

public class API {

    protected static final BaseAPI base;

    static {
        base = base();
    }

    private static BaseAPI base() {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
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