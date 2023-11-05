package com.example.schedulecontacts.model;

public class Register {
    private String email;
    private String password;
    private String confirmPassword;

    public Register() {
    }

    public Register(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("A senha deve ter no mínimo 8 caracteres.");
        }
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        if (!confirmPassword.equals(this.password)) {
            throw new IllegalArgumentException("As senhas não coincidem.");
        }
        this.confirmPassword = confirmPassword;
    }
}
