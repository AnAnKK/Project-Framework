package com.runda.projectframework.app.repository.api;

import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.utils.TokenUtils;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author:  RD_YH
 * date:    2018/10/9
 * version: v1.0
 * description: 网络请求工具获取类
 */

@Singleton
public class APIServiceCreator {

    private static APIService apiService;
    private static OkHttpClient okHttpClient = null;
    private SSLSocketFactory sslSocketFactory;

    @Inject
    public APIServiceCreator() {
        setupOkHttp();
        setupRetrofit();
    }

    private void setupOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (Constants.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        //设置缓存
        Cache cache = new Cache(new File(Constants.PATH_CACHE), 1024 * 1024 * 100);

        final TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        // Install the all-trusting trust manager
        final SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }



        okHttpClient = builder
                .cache(cache)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS) //设置超时
                .retryOnConnectionFailure(true) //错误重连
                .sslSocketFactory(sslSocketFactory)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json; charset=UTF-8")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("Authorization","Bearer "+ TokenUtils.getToken())
                                .build();
                        return chain.proceed(request);

                    }
                })
                .build();
    }

    private void setupRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.WEB_HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
    }

    public APIService getRequester() {
        return apiService;
    }

    public OkHttpClient getAPIClient() {
        if (okHttpClient == null) {
            setupOkHttp();
            setupRetrofit();
        }
        return okHttpClient;
    }
}
