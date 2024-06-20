package com.contacts.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Contact extends PanacheEntity {
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String emailAddress;
}
