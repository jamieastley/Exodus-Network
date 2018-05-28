package com.jastley.warmindfordestiny2.Utils;

import android.content.Context;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class NetworkInterceptor implements Interceptor {

    private Context mContext;

    public NetworkInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        if(!NetworkUtil.isOnline(mContext)) {
            throw new NoNetworkException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
