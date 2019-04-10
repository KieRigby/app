package com.example.bountyhunterapi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class that acts as a factory method and creates a retrofit REST client instance
 */
public class RetrofitClientInstance {

    //Declare the retrofit object
    private static Retrofit retrofit;
    //URl of the site the JSON will be retrieved from
    private static final String BASE_URL ="https://api.bountyhunt.me";

    /**
     * Creates the instance of the retrofit client
     * @return The generated retrofit client
     */
    public static Retrofit getRetrofitInstance(Context context){
        /**
         * if statement that creates a new retrofit instance if there isn't one already
         * (ensure there is only ever one instance of the retrofit client)
         */
        if (retrofit==null){
            //Creates new retrofit instance
            retrofit= new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(provideOkHttpClient(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

    private static OkHttpClient provideOkHttpClient(final Context context) {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);

        okhttpClientBuilder.addInterceptor(new NetworkConnectionInterceptor() {

            @Override
            public boolean isInternetAvailable() {
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }

        });

        return okhttpClientBuilder.build();
    }
}
