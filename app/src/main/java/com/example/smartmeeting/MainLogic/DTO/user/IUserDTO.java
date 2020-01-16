package com.example.smartmeeting.MainLogic.DTO.user;


/**
 * @author Søren Aarup Poulsen
 */

public interface IUserDTO {

    String getName();

    void setName(String name);

    String getEmail();

    void setEmail(String email);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    String getCompany();

    void setCompany(String company);

    String getAddress();

    void setAddress(String address);

    String getCity();

    void setCity(String City);

    int getZipCode();

    void setZipCode(int zipcode);

    String getCountry();

    void setCountry(String country);
}
