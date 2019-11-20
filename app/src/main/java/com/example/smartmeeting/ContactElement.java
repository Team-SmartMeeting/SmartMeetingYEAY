package com.example.smartmeeting;

public class ContactElement {

    private String name;
    private String email;
    private String nr;

    public ContactElement(String name, String email, String nr){
        this.name = name;
        this.email = email;
        this.nr = nr;
    }


    //Getters and Setters
    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
