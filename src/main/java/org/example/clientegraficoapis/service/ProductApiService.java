package org.example.clientegraficoapis.service;

import org.example.clientegraficoapis.model.Product;
import org.example.clientegraficoapis.model.Purchases;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ProductApiService {
    @GET("product")
    Call<List<Product>> getProducts();

    @POST("purchase")
    Call<Purchases> buy(@Body Purchases purchases);

    @POST("product")
    Call<Product> createProduct(@Body Product product);

    @DELETE("product")
    Call<Void> deleteProduct(@Body Product product);

    @PUT("product")
    Call<Product> updateProduct(@Body Product product);
}
