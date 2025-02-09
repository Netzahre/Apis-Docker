package org.example.clientegraficoapis.service;

import org.example.clientegraficoapis.model.Product;
import org.example.clientegraficoapis.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface ProductApiService {
    @GET("product")
    Call<List<Product>> getProducts();
}
