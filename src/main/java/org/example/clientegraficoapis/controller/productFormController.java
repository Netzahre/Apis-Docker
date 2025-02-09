package org.example.clientegraficoapis.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import org.example.clientegraficoapis.model.Product;
import org.example.clientegraficoapis.service.ProductApiService;
import org.example.clientegraficoapis.service.RetrofitProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class productFormController {
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDesc;
    @FXML
    private TextField tfPrecio;

    private ProductApiService apiService;
    private Product product;

    public void initialize() {
        tfNombre.setText("");
        tfDesc.setText("");
        tfPrecio.setText("");
        tfPrecio.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty() || newText.matches("[0-9]\\.?[0-9]")) {
                int dotCount = newText.length() - newText.replace(".", "").length();
                if (dotCount <= 1) {
                    return change;
                }
            }
            return null;
        }));
        apiService = RetrofitProduct.getClient().create(ProductApiService.class);
    }

    public void setProduct(Product product) {
        this.product = product;
        tfNombre.setText(product.getName());
        tfDesc.setText(product.getDescription());
        tfPrecio.setText(String.valueOf(product.getPrice()));
    }

    @FXML
    public void productManager() throws IOException {
        if (product == null) {
            if (createProduct()) {
                Call<Product> call = apiService.createProduct(product);
                call.enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if (response.isSuccessful()) {
                            showError("Producto creado");
                            System.out.println("Producto creado");
                        } else {
                            showError("Error al crear el producto");
                            System.out.println("Error al crear producto");
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable throwable) {
                        showError("Error al crear el producto");
                        System.out.println("Error al crear producto");
                    }
                });
                closeWindow();
            }
        } else {
            updateProduct();
            Call<Product> call = apiService.updateProduct(product);
            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if (response.isSuccessful()) {
                        showError("Producto actualizado");
                        System.out.println("Producto actualizado");
                    } else {
                        showError("Error al actualizar el producto");
                        System.out.println("Error al actualizar producto");
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable throwable) {
                    showError("Error al actualizar el producto");
                    System.out.println("Error al actualizar producto");
                }
            });
            closeWindow();
        }
    }

    private boolean createProduct() {
        if (tfNombre.getText().isEmpty() || tfDesc.getText().isEmpty() || tfPrecio.getText().isEmpty()) {
            showError("No pueden existir campos vacios");
            return false;
        }
        product = new Product();
        product.setName(tfNombre.getText());
        product.setDescription(tfDesc.getText());
        product.setPrice(Double.parseDouble(tfPrecio.getText()));
        return true;
    }

    private void updateProduct() {
        product.setName(tfNombre.getText());
        product.setDescription(tfDesc.getText());
        product.setPrice(Double.parseDouble(tfPrecio.getText()));
    }


    public void showError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void closeWindow() throws IOException {
        Stage actualScene = (Stage) tfNombre.getScene().getWindow();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/org/example/clientegraficoapis/store.fxml"))));
        stage.setTitle("Tienda Amazon't");
        actualScene.close();
        stage.show();
    }
}
