package com.lolpremade.timer.Model.API;

import com.lolpremade.timer.Model.API.Internal.API;

import org.json.JSONObject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Sia on 2017/5/9.
 */

public class LoginApi extends API {

    public static Observable<JSONObject> register() {
        return base.register("15993172584", "yaya", "123456", "2542756175@qq.com");
    }

    public static Observable<JSONObject> y() {
        return base.y("123456").observeOn(AndroidSchedulers.mainThread());
    }
}
