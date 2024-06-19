package com.contacts.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.Entity;
@Data
@Entity
public class Contact extends PanacheEntity {
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String emailAddress;
}
