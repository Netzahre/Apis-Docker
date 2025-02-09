package org.example.clientegraficoapis.service;

import okhttp3.ResponseBody;
import org.example.clientegraficoapis.model.Product;
import org.example.clientegraficoapis.model.Purchases;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ProductApiService {
    @GET("product")
    Call<List<Product>> getProductsFilter(
            @Query("name") String name,
            @Query("minPrice") Double minPrice,
            @Query("maxPrice") Double maxPrice
    );

    @GET("product")
    Call<List<Product>> getProducts();

    @POST("purchase")
    Call<Purchases> buy(@Body Purchases purchases);

    @POST("product")
    Call<Product> createProduct(@Body Product product);

    @DELETE("product/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") Integer id);

    @PUT("product/{id}")
    Call<Product> updateProduct(@Path("id") Integer id, @Body Product product);



}
