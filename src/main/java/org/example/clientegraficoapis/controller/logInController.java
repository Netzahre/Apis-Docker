package org.example.clientegraficoapis.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.clientegraficoapis.model.User;
import org.example.clientegraficoapis.service.UserApiService;
import org.example.clientegraficoapis.service.RetrofitClient;
import org.example.clientegraficoapis.session.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class logInController {


    private UserApiService apiService;
    @FXML private TextField tfUsuario;
    @FXML private PasswordField pfPassword;

    @FXML
    public void initialize() {
        apiService = RetrofitClient.getClient().create(UserApiService.class);
    }

    @FXML
    public void login() {
        String user = tfUsuario.getText();
        String password = pfPassword.getText();

        Call<User> call = apiService.logIn(user, password);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    Session.setLoggedUser(user);
                    Platform.runLater(() -> {
                        Stage currentStage = (Stage) tfUsuario.getScene().getWindow();
                        try {
                            Stage stage = new Stage();
                            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/org/example/clientegraficoapis/store.fxml"))));
                            stage.setTitle("Tienda Amazon't");
                            currentStage.close();
                            stage.show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    @FXML
    public void signIn() {
        try {
            Stage stage = (Stage) tfUsuario.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/clientegraficoapis/signin.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Registro Amazon't");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void salir() {
        System.exit(0);
    }


}
