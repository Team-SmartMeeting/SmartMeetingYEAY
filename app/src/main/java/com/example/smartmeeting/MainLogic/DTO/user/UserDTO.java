package com.example.smartmeeting.MainLogic.DTO.user;

import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;

import java.util.ArrayList;

/**
 * @author Søren Aarup Poulsen
 */

public class UserDTO implements IUserDTO {

    private String name;
    private String email;
    private String phoneNumber;
    private String Company;
    private String Address;
    private int ZipCode;
    private String Country;
    private ArrayList<String> meetingsList;

    public UserDTO() {
    }


    public UserDTO(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        meetingsList = new ArrayList<>();
    }

    public UserDTO(String name, String email, String phoneNumber,String Company,String Address, int ZipCode, String Country) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.Company = Company;
        this.Address = Address;
        this.ZipCode = ZipCode;
        this.Country = Country;
        meetingsList = new ArrayList<>();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        this.Company = company;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public int getZipCode() {
        return ZipCode;
    }

    public void setZipCode(int zipcode) {
        this.ZipCode = zipcode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        this.Country = country;
    }

    public ArrayList<String> getMeetingsList() {
        return meetingsList;
    }

    public void setMeetingsList(ArrayList<String> meetingsList) {
        this.meetingsList = meetingsList;
    }
}
