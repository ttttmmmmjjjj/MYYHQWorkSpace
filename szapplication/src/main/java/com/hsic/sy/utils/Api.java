package com.hsic.sy.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by Administrator on 2018/12/26.
 */

public interface Api {
    @Headers("")
    @GET("")
    Call<HsicMessage> getRespMsg();
}
