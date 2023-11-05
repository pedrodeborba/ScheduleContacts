package com.example.schedulecontacts.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.example.schedulecontacts.db.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField ConfirmPasswordField;

    @FXML
    void register(ActionEvent event) {
        com.example.schedulecontacts.model.Register register = new com.example.schedulecontacts.model.Register();
        register.setEmail(emailField.getText());
        register.setPassword(passwordField.getText());
        register.setConfirmPassword(ConfirmPasswordField.getText());

        try (Connection conexao = Database.getConnection()) {
            String sql = "INSERT INTO usuario (email, senha) VALUES (?, ?)";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, register.getEmail());
                stmt.setString(2, register.getConfirmPassword());

                stmt.executeUpdate();

                System.out.println("Usuario cadastrado com sucesso!");

            }catch (SQLException e) {
                System.out.print("Erro ao registrar usuário.");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.print("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        try {
            // Carregando o arquivo FXML da tela de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/schedulecontacts/Login.fxml"));
            Parent root = loader.load();

            // Crinando a cena da tela de login
            Scene scene = new Scene(root);

            // Acessando o palco (Stage) atual da aplicação
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Definindo a cena da tela de login no palco
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}