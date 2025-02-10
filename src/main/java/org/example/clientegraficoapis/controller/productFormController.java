package org.example.clientegraficoapis.controller;


import javafx.application.Platform;
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

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        tfNombre.setText("");
        tfDesc.setText("");
        tfPrecio.setText("");
        tfPrecio.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isContentChange()) {
                String text = change.getControlNewText();
                if (text.matches("([1-9][0-9]*)?([.][0-9]*)?")) {
                    return change;
                }
                return null;
            }
            return change;
        }));
        apiService = RetrofitProduct.getClient().create(ProductApiService.class);
    }

    /**
     * Sets the product to be edited in the form
     *
     * @param product the product to be edited
     */
    public void setProduct(Product product) {
        this.product = product;
        tfNombre.setText(product.getName());
        tfDesc.setText(product.getDescription());
        tfPrecio.setText(String.valueOf(product.getPrice()));
    }

    /**
     * Handles the action of the user clicking the "Guardar" button
     * If the product is null, it creates a new product
     * If the product is not null, it updates the product
     */
    @FXML
    public void productManager() throws IOException {
        if (product == null) {
            if (createProduct()) {
                Call<Product> call = apiService.createProduct(product);
                call.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        Platform.runLater(() -> {
                            if (response.isSuccessful()) {
                                Product createdProduct = response.body();
                                System.out.println("Producto creado con id: " + createdProduct.getId());

                                product = createdProduct;
                                showMessage("Producto creado");
                            } else {
                                showMessage("Error al crear el producto");
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable throwable) {
                        showMessage("Error al crear el producto");
                    }
                });
                closeWindow();
            }
        } else {
            updateProduct();
            Call<Product> call = apiService.updateProduct(product.getId(), product);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    Platform.runLater(() -> {
                        if (response.isSuccessful()) {
                            showMessage("Producto actualizado");
                            System.out.println("Producto actualizado");

                        } else {
                            showMessage("Error al actualizar el producto");
                            System.out.println("Error al actualizar producto");
                        }
                    });
                }

                @Override
                public void onFailure(Call<Product> call, Throwable throwable) {
                    showMessage("Error al actualizar el producto");
                    System.out.println("Error al actualizar producto");
                }
            });
            closeWindow();
        }
    }

    /**
     * Creates a new product with the data entered by the user
     *
     * @return true if the product was created, false if there are empty fields
     */
    private boolean createProduct() {
        if (tfNombre.getText().isEmpty() || tfDesc.getText().isEmpty() || tfPrecio.getText().isEmpty()) {
            showMessage("No pueden existir campos vacios");
            return false;
        }
        product = new Product();
        product.setName(tfNombre.getText());
        product.setDescription(tfDesc.getText());
        product.setPrice(Double.parseDouble(tfPrecio.getText()));
        return true;
    }

    /**
     * Updates the product with the data entered by the user
     */
    private void updateProduct() {
        product.setName(tfNombre.getText());
        product.setDescription(tfDesc.getText());
        product.setPrice(Double.parseDouble(tfPrecio.getText()));
    }


    /**
     * Shows a message to the user
     *
     * @param text the message to be shown
     */
    public void showMessage(String text) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText(text);
            alert.showAndWait();
        });
    }


    /**
     * Closes the current window and opens the store window
     */
    public void closeWindow() throws IOException {
        Stage actualScene = (Stage) tfNombre.getScene().getWindow();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/org/example/clientegraficoapis/store.fxml"))));
        stage.setTitle("Tienda Amazon't");
        actualScene.close();
        stage.show();
    }
}