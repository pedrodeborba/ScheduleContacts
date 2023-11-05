package com.example.schedulecontacts.controller;

import com.example.schedulecontacts.db.Database;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Contact {
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
        com.example.schedulecontacts.model.Contact contact = new com.example.schedulecontacts.model.Contact();
        contact.setNome(nameField.getText());
        contact.setTelefone(phoneField.getText());
        contact.setEmail(emailField.getText());
        contact.setCpf(cpfField.getText());
        contact.setRua(roadField.getText());
        contact.setNumero(numberField.getText());
        contact.setBairro(neighborhoodField.getText());
        contact.setCep(cepField.getText());

        try (Connection conexao = Database.getConnection()) {
            String sql = "INSERT INTO contato (nome, telefone, email, cpf, rua, numero, bairro, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
}
