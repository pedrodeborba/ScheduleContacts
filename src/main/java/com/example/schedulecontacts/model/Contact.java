package com.example.schedulecontacts.model;

public class Contact {
    private String nome;
    private String telefone;
    private String email;
    private String cpf;
    private String rua;
    private String numero;
    private String bairro;
    private String cep;

    public Contact(String nome, String telefone, String email, String cpf, String rua, String numero, String bairro, String cep) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
    }

    public Contact() {
        this.nome = "";
        this.telefone = "";
        this.email = "";
        this.cpf = "";
        this.rua = "";
        this.numero = "";
        this.bairro = "";
        this.cep = "";
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() { return email; }

    public String getCpf() { return cpf; }

    public String getRua() { return rua; }

    public String getNumero() { return numero; }

    public String getBairro() { return bairro; }

    public String getCep() { return cep; }

    public void setNome(String nome) { this.nome = nome; }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    public void setEmail(String email) { this.email = email; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public void setRua(String rua) { this.rua = rua; }

    public void setNumero(String numero) { this.numero = numero; }

    public void setBairro(String bairro) { this.bairro = bairro; }

    public void setCep(String cep) { this.cep = cep; }
}
