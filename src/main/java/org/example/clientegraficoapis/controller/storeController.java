package org.example.clientegraficoapis.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import okhttp3.ResponseBody;
import org.example.clientegraficoapis.model.Product;
import org.example.clientegraficoapis.model.Purchases;
import org.example.clientegraficoapis.service.*;
import org.example.clientegraficoapis.session.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.io.IOException;
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
    @FXML
    private Button createButton;
    @FXML
    private Button modButton;
    @FXML
    private Button deleteButton;


    private ProductApiService apiService;
    private final FTPService ftpService = new FTPService();

    @FXML
    public void initialize() {
        startTable();
        apiService = RetrofitProduct.getClient().create(ProductApiService.class);
        tvProducts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadProducts();
        if (!Session.getLoggedUser().getIsAdmin()) {
            createButton.setVisible(false);
            modButton.setVisible(false);
            deleteButton.setVisible(false);
        }
    }

    private void startTable() {
        idProduct.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
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
                System.out.println("Ha ocurrido un error: " + t.getMessage());
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
        purchasedProducts.append("Ha comprado: " + "\n");
        for (Product product : selectedProducts) {
            System.out.println("- " + product.getName() + " (Precio: " + product.getPrice() + ")");
            purchasedProducts.append(product.getName()).append(" (Precio: ").append(product.getPrice()).append(")").append("\n");
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
                System.out.println("Ha ocurrido un error: " + t.getMessage());
            }
        });


    }

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

    @FXML
    public void openModifyProduct() throws IOException {
        Stage stage = (Stage) tvProducts.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/clientegraficoapis/productForm.fxml"));

        if (tvProducts.getSelectionModel().getSelectedItems().size() == 1) {
            Scene scene = new Scene(loader.load());
            productFormController controller = loader.getController();
            controller.setProduct(tvProducts.getSelectionModel().getSelectedItem());
            stage.setScene(scene);
            stage.setTitle("Modificar producto de Amazon't");
        } else {
            showError("Seleccione un único producto.");
            System.out.println("Seleccione un único producto.");
        }
    }

    @FXML
    public void openCreateProduct() throws IOException {
        Stage stage = (Stage) tvProducts.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/clientegraficoapis/productForm.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("crear producto de Amazon't");
    }

    @FXML
    public void deleteProduct() {
        List<Product> selectedProducts = tvProducts.getSelectionModel().getSelectedItems();
        if (selectedProducts.isEmpty()) {
            showError("No hay productos seleccionados.");
            System.out.println("No hay productos seleccionados.");
            return;
        }
        for (Product product : selectedProducts) {
            Call<ResponseBody> call = apiService.deleteProduct(product.getIdProduct());
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Producto eliminado correctamente.");
                        loadProducts();
                    } else {
                        showError("Error al eliminar el producto.");
                        System.out.println("Error al eliminar el producto.");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    showError("Error al eliminar el producto.");
                    System.out.println("Error al eliminar el producto.");
                }
            });
        }
    }
}
