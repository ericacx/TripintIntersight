package com.tripint.intersight.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lirichen on 16/9/12.
 */
public class HeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("Token", "Token-With-App-Name")
                    .header("Client", "App/V1")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);

    }
}
