package com.contacts.service;

import com.contacts.entities.Contact;
import com.contacts.repository.ContactRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ContactService {

    @Inject
    ContactRepository repository;

    public List<Contact> listAllContacts(String filter) {
        if (filter == null || filter.isEmpty()) {
            return repository.listAll();
        } else {
            return repository.listAll().stream()
                    .filter(c -> c.firstName.contains(filter) || c.lastName.contains(filter))
                    .collect(Collectors.toList());
        }
    }

    public Optional<Contact> getContactById(Long id) {
        return Optional.ofNullable(repository.findById(id));
    }

    @Transactional
    public Contact createContact(Contact contact) {
        repository.persist(contact);
        return contact;
    }

    @Transactional
    public Optional<Contact> updateContact(Long id, Contact contact) {
        Contact existing = repository.findById(id);
        if (existing == null) {
            return Optional.empty();
        }
        contact.id = id;  // Ensure the ID remains the same
        Contact updated = repository.getEntityManager().merge(contact);
        return Optional.of(updated);
    }

    @Transactional
    public boolean deleteContact(Long id) {
        Contact existing = repository.findById(id);
        if (existing == null) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
