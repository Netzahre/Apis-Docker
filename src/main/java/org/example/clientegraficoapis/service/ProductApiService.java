package org.example.clientegraficoapis.service;

import org.example.clientegraficoapis.model.Product;
import org.example.clientegraficoapis.model.Purchases;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ProductApiService {
    @GET("product")
    Call<List<Product>> getProducts();

    @POST("purchase")
    Call<Purchases> buy(@Body Purchases purchases);
}
