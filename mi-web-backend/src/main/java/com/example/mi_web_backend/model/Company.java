package com.example.mi_web_backend.model;


import jakarta.persistence.*;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CIF", length = 9, nullable = false, unique = true)
    private String cif;

    @Column(name = "name", length = 28, nullable = false)
    private String name;

    @Column(name = "telephone", length = 9)
    private String telephone;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "direction", length = 100)
    private String adress;

    @Column(name = "login", length = 20, nullable = false, unique = true)
    private String login;

    @Column(name = "password", length = 255, nullable = false)
    private String password;
    // --- Construntores ---

    public Company() {
    }

    public Company(Long id, String cif, String name, String telephone, String email, String direction, String login, String password) {
        this.id = id;
        this.cif = cif;
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.adress = direction;
        this.login = login;
        this.password = password;
    }


    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return adress;
    }

    public void setAddress(String address) {
        this.adress = adress;
    }
}