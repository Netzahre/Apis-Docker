package org.example.clientegraficoapis.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.clientegraficoapis.model.Product;
import org.example.clientegraficoapis.service.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.List;

public class storeController {

    @FXML
    private TableView<Product> tvProductos;
    @FXML
    private TableColumn<Product, Integer> idProducto;
    @FXML
    private TableColumn<Product, String> nombreProducto;
    @FXML
    private TableColumn<Product, String> descProducto;
    @FXML
    private TableColumn<Product, Double> precioProducto;

    private ProductApiService apiService;
    private FTPService ftpService = new FTPService();

    @FXML
    public void initialize() {
        inicializarTabla();
        apiService = RetrofitProduct.getClient().create(ProductApiService.class);
        tvProductos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        cargarProductos();
    }

    private void inicializarTabla() {
        idProducto.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreProducto.setCellValueFactory(new PropertyValueFactory<>("name"));
        descProducto.setCellValueFactory(new PropertyValueFactory<>("description"));
        precioProducto.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void cargarProductos() {
        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body();
                    tvProductos.getItems().clear();
                    tvProductos.getItems().addAll(products);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @FXML
    protected void comprar() {
        Product product = tvProductos.getSelectionModel().getSelectedItem();
        if (product != null) {
            System.out.println("Comprando: " + product.getName());
        }

    }

    //REVISAR, PEGADO DE GEPETO
    public void procesarCompra(int numProductos, double total) {
        boolean resultado = ftpService.savePurchase(numProductos, total);
        if (resultado) {
            System.out.println("Compra registrada en FTP correctamente.");
        } else {
            System.out.println("Error al registrar la compra en FTP.");
        }
    }
}
