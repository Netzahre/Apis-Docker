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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

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

        MessageDigest cifrado = MessageDigest.getInstance("SHA-256");

        byte[] passwordBytes = cifrado.digest(passSinCifrar.getBytes(StandardCharsets.UTF_8));
        String passwordEncrypted = Base64.getEncoder().encodeToString(passwordBytes);

        user.setPassword(passwordEncrypted);

        Call<User> call = apiService.signIn(user);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Platform.runLater(() -> {
                        try {
                            Stage stage = (Stage) tfUser.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/clientegraficoapis/login.fxml"));
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
