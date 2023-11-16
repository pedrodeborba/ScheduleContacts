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
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String cpf = cpfField.getText();
        String road = roadField.getText();
        String number = numberField.getText();
        String neighborhood = neighborhoodField.getText();
        String cep = cepField.getText();
        
        ContactModel contact = new ContactModel();
        contact.setNome(name);
        contact.setTelefone(phone);
        contact.setEmail(email);
        contact.setCpf(cpf);
        contact.setRua(road);
        contact.setNumero(number);
        contact.setBairro(neighborhood);
        contact.setCep(cep);

        // Validando se todos os campos estão preenchidos
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || cpf.isEmpty() ||
                road.isEmpty() || number.isEmpty() || neighborhood.isEmpty() || cep.isEmpty()) {
            System.out.println("Todos os campos devem ser preenchidos.");
            return;
        }

        // Validando o formato do email
        if (!isValidEmail(email)) {
            System.out.println("Formato de email inválido.");
            return;
        }

        // Validando o formato do telefone
        if (!isValidPhoneNumber(phone)) {
            System.out.println("Formato de telefone inválido.");
            return;
        }

        // Validando o formato do CPF
        if (!isValidCPF(cpf)) {
            System.out.println("Formato de CPF inválido.");
            return;
        }

        // Validando o formato do CEP
        if (!isValidCEP(cep)) {
            System.out.println("Formato de CEP inválido.");
            return;
        }

        // Validando se o número é um inteiro
        if (!isValidInteger(number)) {
            System.out.println("O número deve ser um valor inteiro válido.");
            return;
        }

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

                nameField.setText("");
                phoneField.setText("");
                emailField.setText("");
                cpfField.setText("");
                roadField.setText("");
                numberField.setText("");
                neighborhoodField.setText("");
                cepField.setText("");

            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados.");
                e.printStackTrace();
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

    // ---------- Validações ----------

    //Função para validar se o email tem @
    private boolean isValidEmail(String email) {
        return email.contains("@");
    }

    // Função para validar o formato do telefone (min 9, max 14) | (900000000), (00 90000-0000).
    private boolean isValidPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("\\s", "");
        return phoneNumber.length() >= 9 && phoneNumber.length() <= 14 && phoneNumber.matches("[0-9().\\-]+");
    }


    // Função para validar o formato do CPF (min 11, max 14) | (00000000000), (000.000.000-00).
    private boolean isValidCPF(String cpf) {
        return cpf.length() >= 11 && cpf.length() <= 14 && cpf.matches("[0-9().\\-]+");
    }



    // Função para validar o formato do CEP (min 8, max 9) | (00000000), (00000-000).
    private boolean isValidCEP(String cep) {
        return cep.matches("[0-9\\-]+") && (cep.length() == 8 || cep.length() == 9);
    }

    // Função para validar se a string representa um número inteiro
    private boolean isValidInteger(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}