package com.example.schedulecontacts.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.example.schedulecontacts.db.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void login(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (authenticateUser(email, password)) {
            // Autenticação bem-sucedida, navegue para a próxima tela
            navigateNextScreen(event);
        } else {
            Alert errorAuth = new Alert(Alert.AlertType.ERROR);
            errorAuth.setTitle("Erro de autenticação");
            errorAuth.setHeaderText("Email ou senha incorretos");
            errorAuth.setContentText("Por favor, tente novamente");
            errorAuth.showAndWait();
        }
    }

    @FXML
    private void goToRegister(ActionEvent event) {
        try {
            // Carregando o arquivo FXML da tela de register
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/schedulecontacts/Register.fxml"));
            Parent root = loader.load();

            // Criando a cena da tela de login
            Scene scene = new Scene(root);

            // Acessando o palco (Stage) atual da aplicação
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Definindo a cena da tela de register no palco
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean authenticateUser(String email, String password) {
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT * FROM usuarios WHERE email = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String storedPassword = rs.getString("senha");

                    if (password.equals(storedPassword)) {
                        return true; // Autenticação bem-sucedida
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Autenticação falhou
    }

    private void navigateNextScreen(ActionEvent event) {
        try {
            // Carregando o arquivo FXML da tela de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/schedulecontacts/Contact.fxml"));
            Parent root = loader.load();

            // Crinando a cena da tela de Contatos
            Scene scene = new Scene(root);

            // Acessando o palco (Stage) atual da aplicação
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Definindo a cena da tela de contatos no palco
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}