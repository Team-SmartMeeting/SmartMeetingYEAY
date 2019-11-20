package com.example.smartmeeting;

public class ContactElement {

    private String name;
    private String email;
    private String nr;
    private String id;

    public ContactElement(String name, String email, String nr, String id){
        this.name = name;
        this.email = email;
        this.nr = nr;
        this.id = id;
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
