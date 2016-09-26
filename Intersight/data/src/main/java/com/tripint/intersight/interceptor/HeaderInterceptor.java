package com.tripint.intersight.interceptor;

import android.content.Context;

import com.tripint.intersight.common.cache.ACache;
import com.tripint.intersight.common.enumkey.EnumKey;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lirichen on 16/9/12.
 */
public class HeaderInterceptor implements Interceptor {

    private Context context;

    public HeaderInterceptor(Context context) {
        this.context = context;
    }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("Token", ACache.get(context).getAsString(EnumKey.User.USER_TOKEN))
                    .header("Client", "App/V1")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);

    }
}
