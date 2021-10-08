package com.swissre;

import java.time.LocalDate;
import java.util.Objects;

public class Citizen {

    private String lastName;
    private String firstName;
    private LocalDate birthday;
    private String email;

    public Citizen() {
    }

    public Citizen(String lastName, String firstName, LocalDate birthday, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                '}';
    }
}
