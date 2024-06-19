package com.contacts.repository;

import com.contacts.entities.Contact;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContactRepository implements PanacheRepository<Contact> {
    // Custom methods can be added here if needed
}
