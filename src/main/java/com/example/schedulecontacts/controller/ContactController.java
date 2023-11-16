package com.example.schedulecontacts.controller;

import com.example.schedulecontacts.db.Database;
import com.example.schedulecontacts.model.ContactModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class ContactController {
    @FXML
    private TableView<ContactModel> table;
    @FXML
    private TableColumn<ContactModel, String> nome, telefone, email, cpf, rua, bairro, cep;
    @FXML
    private TableColumn<ContactModel, Integer> numero;


    ObservableList<ContactModel> contacts = FXCollections.observableArrayList();

    public void initialize() {
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        rua.setCellValueFactory(new PropertyValueFactory<>("rua"));
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        bairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        cep.setCellValueFactory(new PropertyValueFactory<>("cep"));

        //Adicionando um estilo na tabela
        Label labelNome = new Label("NOME");
        Label labelTelefone = new Label("TELEFONE");
        Label labelEmail = new Label("EMAIL");
        Label labelCpf = new Label("CPF");
        Label labelRua = new Label("RUA");
        Label labelNumero = new Label("NÚMERO");
        Label labelBairro = new Label("BAIRRO");
        Label labelCep = new Label("CEP");

        labelNome.setStyle("-fx-text-fill: #6495ED;");
        labelTelefone.setStyle("-fx-text-fill: #6495ED;");
        labelEmail.setStyle("-fx-text-fill: #6495ED;");
        labelCpf.setStyle("-fx-text-fill: #6495ED;");
        labelRua.setStyle("-fx-text-fill: #6495ED;");
        labelNumero.setStyle("-fx-text-fill: #6495ED;");
        labelBairro.setStyle("-fx-text-fill: #6495ED;");
        labelCep.setStyle("-fx-text-fill: #6495ED;");

        nome.setGraphic(labelNome);
        telefone.setGraphic(labelTelefone);
        email.setGraphic(labelEmail);
        cpf.setGraphic(labelCpf);
        rua.setGraphic(labelRua);
        numero.setGraphic(labelNumero);
        bairro.setGraphic(labelBairro);
        cep.setGraphic(labelCep);

        // Exibindo os contatos do banco de dados
        listContact();
    }

    public void listContact() {
        String sql = "SELECT * FROM contatos";

        try (Connection conexao = Database.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Limpando a lista de contatos
            contacts.clear();

            // Pegando os contatos do banco de dados
            while (rs.next()) {
                ContactModel contact = new ContactModel();
                contact.setNome(rs.getString("nome"));
                contact.setTelefone(rs.getString("telefone"));
                contact.setEmail(rs.getString("email"));
                contact.setCpf(rs.getString("cpf"));
                contact.setRua(rs.getString("rua"));
                contact.setNumero(rs.getString("numero"));
                contact.setBairro(rs.getString("bairro"));
                contact.setCep(rs.getString("cep"));

                contacts.add(contact);
            }

            // Definindo a lista de contatos na tabela
            table.setItems(contacts);

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
    }

    @FXML
    private void goToAddContact(ActionEvent event) {
        try {
            // Carregando o arquivo FXML da tela
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/schedulecontacts/AddContact.fxml"));
            Parent root = loader.load();

            // Criando a cena da tela
            Scene scene = new Scene(root);

            // Acessando o palco (Stage) atual da aplicação
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Definindo a cena da tela no palco
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------- Editar e apagar contato ----------
    @FXML
    private TextField nameField, phoneField, emailField, cpfField, roadField, numberField, neighborhoodField, cepField;

    @FXML
    protected void lineTable(MouseEvent event) {
        int show = table.getSelectionModel().getSelectedIndex();
        ContactModel contact = table.getSelectionModel().getSelectedItem();

        nameField.setText(contact.getNome());
        phoneField.setText(contact.getTelefone());
        emailField.setText(contact.getEmail());
        cpfField.setText(contact.getCpf());
        roadField.setText(contact.getRua());
        numberField.setText(String.valueOf(contact.getNumero()));
        neighborhoodField.setText(contact.getBairro());
        cepField.setText(contact.getCep());
    }

    @FXML
    void deleteAction(ActionEvent event) {
        ContactModel selectedContact = table.getSelectionModel().getSelectedItem();

        if (selectedContact != null) {
            String sql = "DELETE FROM contatos WHERE nome = ?";

            try (Connection conexao = Database.getConnection();
                 PreparedStatement stmt = conexao.prepareStatement(sql)) {

                stmt.setString(1, selectedContact.getNome());
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Contato excluído com sucesso.");
                    listContact();  // Atualiza a tabela após exclusão
                    nameField.setText("");
                    phoneField.setText("");
                    emailField.setText("");
                    cpfField.setText("");
                    roadField.setText("");
                    numberField.setText("");
                    neighborhoodField.setText("");
                    cepField.setText("");
                } else {
                    System.out.println("Erro ao excluir o contato.");
                }

            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Nenhum contato selecionado para exclusão.");
        }
    }

    @FXML
    void editAction(ActionEvent event) {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String cpf = cpfField.getText();
        String road = roadField.getText();
        String number = String.valueOf(parseInt(numberField.getText()));
        String neighborhood = neighborhoodField.getText();
        String cep = cepField.getText();

        ContactModel selectedContact = table.getSelectionModel().getSelectedItem();

        if (selectedContact != null) {
            String sql = "UPDATE contatos SET nome = ?, telefone = ?, email = ?, cpf = ?, rua = ?, numero = ?, bairro = ?, cep = ? WHERE nome = ?";
            try (Connection conexao = Database.getConnection();
                 PreparedStatement stmt = conexao.prepareStatement(sql)) {

                stmt.setString(1, name);
                stmt.setString(2, phone);
                stmt.setString(3, email);
                stmt.setString(4, cpf);
                stmt.setString(5, road);
                stmt.setString(6, number);
                stmt.setString(7, neighborhood);
                stmt.setString(8, cep);
                stmt.setString(9, selectedContact.getNome());

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Contato atualizado com sucesso.");
                    listContact();  // Atualiza a tabela após atualização
                } else {
                    System.out.println("Erro ao atualizar o contato.");
                }

            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Nenhum contato selecionado para edição.");
        }
    }



}





