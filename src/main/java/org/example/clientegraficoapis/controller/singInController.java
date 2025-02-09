package org.example.clientegraficoapis.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.clientegraficoapis.model.User;
import org.example.clientegraficoapis.service.UserApiService;
import org.example.clientegraficoapis.service.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class singInController {


    private UserApiService apiService;
    @FXML
    private TextField tfUser;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private CheckBox cbAdmin;

    @FXML
    public void initialize() {
        apiService = RetrofitClient.getClient().create(UserApiService.class);
    }

    @FXML
    public void signIn() throws NoSuchAlgorithmException {
        User user = new User();
        user.setUsername(tfUser.getText());
        user.setIsAdmin(cbAdmin.isSelected());
        String passSinCifrar = pfPassword.getText();
        MessageDigest cifrado = MessageDigest.getInstance("MD5");
        cifrado.reset();
        cifrado.update(passSinCifrar.getBytes());
        user.setPassword(cifrado.digest());
        Call<User> call = apiService.signIn(user);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Platform.runLater(() -> {
                        try {
                            Stage stage = new Stage();
                            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/org/example/clientegraficoapis/login.fxml"))));
                            stage.setTitle("Amazon't log-in");
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
    public void back() throws IOException {
        Stage actualScene = (Stage) cbAdmin.getScene().getWindow();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/org/example/clientegraficoapis/login.fxml"))));
        stage.setTitle("Amazon't log-in");
        actualScene.close();
        stage.show();
    }

}
