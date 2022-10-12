package com.example.phonebook;

public class phoneBookData {
    String firstName;
    String lastName;
    String phoneNumber;
    String address;
    String city;
    String state;
    String zipCode;

    public phoneBookData(String firstNames, String lastNames, String phoneNumbers, String addresses, String cities,
                     String states, String zipCodes) {
        this.firstName = firstNames;
        this.lastName = lastNames;
        this.phoneNumber = phoneNumbers;
        this.address = addresses;
        this.city = cities;
        this.state = states;
        this.zipCode = zipCodes;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
