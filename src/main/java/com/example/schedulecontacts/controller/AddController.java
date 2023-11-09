package com.example.schedulecontacts.controller;

import com.example.schedulecontacts.model.ContactModel;
import com.example.schedulecontacts.db.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField cpfField;
    @FXML
    private TextField roadField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField neighborhoodField;
    @FXML
    private TextField cepField;

    @FXML
    private void sendContact() {
        ContactModel contact = new ContactModel();
        contact.setNome(nameField.getText());
        contact.setTelefone(phoneField.getText());
        contact.setEmail(emailField.getText());
        contact.setCpf(cpfField.getText());
        contact.setRua(roadField.getText());
        contact.setNumero(numberField.getText());
        contact.setBairro(neighborhoodField.getText());
        contact.setCep(cepField.getText());

        try (Connection conexao = Database.getConnection()) {
            String sql = "INSERT INTO contatos (nome, telefone, email, cpf, rua, numero, bairro, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, contact.getNome());
                stmt.setString(2, contact.getTelefone());
                stmt.setString(3, contact.getEmail());
                stmt.setString(4, contact.getCpf());
                stmt.setString(5, contact.getRua());
                stmt.setString(6, contact.getNumero());
                stmt.setString(7, contact.getBairro());
                stmt.setString(8, contact.getCep());
                stmt.executeUpdate();

                System.out.println("Contato inserido com sucesso!");
            }
        } catch (SQLException e) {
            System.out.print("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
    }

    @FXML
    void goToContact(ActionEvent event) {
        try {
            // Carregando o arquivo FXML para adicionar contatos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/schedulecontacts/Contact.fxml"));
            Parent root = loader.load();

            // Criando a cena da tela addContact
            Scene scene = new Scene(root);

            // Acessando o palco (Stage) atual da aplicação
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Definindo a cena da tela no palco
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}