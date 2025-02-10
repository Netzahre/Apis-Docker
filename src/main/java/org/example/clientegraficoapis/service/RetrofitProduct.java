package org.example.clientegraficoapis.service;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProduct {
    private static final String BASE_URL = "http://localhost:8081/";
    private static Retrofit retrofit = null;

    /**
     * Method to get the Retrofit client
     * @return the Retrofit client
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
