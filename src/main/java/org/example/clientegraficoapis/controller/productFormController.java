package org.example.clientegraficoapis.controller;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.example.clientegraficoapis.model.Product;
import org.example.clientegraficoapis.service.ProductApiService;
import org.example.clientegraficoapis.service.RetrofitProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void productManager() {
        if (product == null) {
            createProduct();
            Call<Product> call = apiService.createProduct(product);
            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Producto creado");
                    } else {
                        System.out.println("Error al crear producto");
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable throwable) {
                    System.out.println("Error al crear producto");
                }
            });
        } else {
            updateProduct();
            Call<Product> call = apiService.updateProduct(product);
            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Producto actualizado");
                    } else {
                        System.out.println("Error al actualizar producto");
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable throwable) {
                    System.out.println("Error al actualizar producto");
                }
            });
        }


    }

    private void createProduct() {
        product = new Product();
        product.setName(tfNombre.getText());
        product.setDescription(tfDesc.getText());
        product.setPrice(Double.parseDouble(tfPrecio.getText()));
    }

    private void updateProduct() {
        product.setName(tfNombre.getText());
        product.setDescription(tfDesc.getText());
        product.setPrice(Double.parseDouble(tfPrecio.getText()));
    }

}
