package com.demo.cryptographyChallenge.entities;


import jakarta.persistence.*;






public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String userDocument;
    private String creditCardToken;
    private Integer value;

    public Client(Long id, String userDocument, String creditCardToken, Integer value) {
        this.id = id;
        this.userDocument = userDocument;
        this.creditCardToken = creditCardToken;
        this.value = value;
    }

    public Client(){

    }

    public Long getId() {
        return id;
    }

    public String getUserDocument() {
        return userDocument;
    }

    public Integer getValue() {
        return value;
    }

    public String getCreditCardToken() {
        return creditCardToken;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserDocument(String userDocument) {
        this.userDocument = userDocument;
    }

    public void setCreditCardToken(String creditCardToken) {
        this.creditCardToken = creditCardToken;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
