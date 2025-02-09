package org.example.clientegraficoapis.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.clientegraficoapis.model.Product;
import org.example.clientegraficoapis.model.Purchases;
import org.example.clientegraficoapis.service.*;
import org.example.clientegraficoapis.session.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.List;

public class storeController {

    @FXML
    private TableView<Product> tvProducts;
    @FXML
    private TableColumn<Product, Integer> idProduct;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, String> productDescription;
    @FXML
    private TableColumn<Product, Double> productPrice;

    private ProductApiService apiService;
    private FTPService ftpService = new FTPService();

    @FXML
    public void initialize() {
        startTable();
        apiService = RetrofitProduct.getClient().create(ProductApiService.class);
        tvProducts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadProducts();
    }

    private void startTable() {
        idProduct.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadProducts() {
        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body();
                    tvProducts.getItems().clear();
                    tvProducts.getItems().addAll(products);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @FXML
    protected void buy() {
        List<Product> selectedProducts = tvProducts.getSelectionModel().getSelectedItems();

        if (selectedProducts.isEmpty()) {
            showError("No hay productos seleccionados.");
            System.out.println("No hay productos seleccionados.");
            return;
        }

        System.out.println("🛒 Productos comprados:");
        StringBuilder purchasedProducts = new StringBuilder();
        for (Product product : selectedProducts) {
            System.out.println("- " + product.getName() + " (Precio: " + product.getPrice() + ")");
            purchasedProducts.append(product.getName() +" (Precio: " + product.getPrice() + ")" + "\n");
        }

        //Añadido, comprobar.
        showError(purchasedProducts.toString());

        //Mandar la compra a la api, la compra tiene la lista de productos y el nombre del comprador, codigo debajo
        Purchases purchases = new Purchases();
        purchases.setProductList(selectedProducts);
        purchases.setPurchaser(Session.getLoggedUser().getUsername());
        //Hacer post a la api
        Call<Purchases> call = apiService.buy(purchases);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Purchases> call, Response<Purchases> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Purchases purchases = response.body();
                    System.out.println("Compra realizada correctamente.");

                    //Procesar la compra en el FTP
                    processPurchase(purchases.getProductList().size(), calculateTotalPrice(purchases.getProductList()));
                }
            }

            @Override
            public void onFailure(Call<Purchases> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    //REVISAR, PEGADO DE GEPETO
    public void processPurchase(int numProducts, double total) {
        boolean resultado = ftpService.savePurchase(numProducts, total);
        if (resultado) {
            System.out.println("Compra registrada en FTP correctamente.");
        } else {
            System.out.println("Error al registrar la compra en FTP.");
        }
    }

    private double calculateTotalPrice(List<Product> selectedProducts) {
        double total = 0;
        for (Product product : selectedProducts) {
            total += product.getPrice();
        }
        return total;
    }

    public static void showError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
