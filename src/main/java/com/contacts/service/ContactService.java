package com.contacts.service;

import com.contacts.entities.Contact;
import com.contacts.repository.ContactRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for handling business logic related to contacts.
 */
@ApplicationScoped
public class ContactService {

    @Inject
    ContactRepository repository;

    /**
     * List all contacts, optionally filtered by a search term.
     * @param filter Optional filter for full-text search.
     * @return List of contacts.
     */
    public List<Contact> listAllContacts(String filter) {
        if (filter == null || filter.isEmpty()) {
            return repository.listAll();
        } else {
            return repository.listAll().stream()
                    .filter(c -> c.firstName.contains(filter) || c.lastName.contains(filter))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Get a contact by ID.
     * @param id The ID of the contact.
     * @return An optional containing the contact if found.
     */
    public Optional<Contact> getContactById(Long id) {
        return Optional.ofNullable(repository.findById(id));
    }

    /**
     * Create a new contact.
     * @param contact The contact to create.
     * @return The created contact.
     */
    @Transactional
    public Contact createContact(Contact contact) {
        if (contact.id != null && contact.id != 0) {
            // Avoid persisting an entity with an existing ID
            throw new IllegalArgumentException("Cannot create contact with an existing ID");
        }
        repository.persist(contact);
        return contact;
    }

    /**
     * Update an existing contact.
     * @param id The ID of the contact to update.
     * @param contact The updated contact data.
     * @return An optional containing the updated contact if found.
     */
    @Transactional
    public Optional<Contact> updateContact(Long id, Contact contact) {
        Contact existing = repository.findById(id);
        if (existing == null) {
            return Optional.empty();
        }
        existing.firstName = contact.firstName;
        existing.lastName = contact.lastName;
        existing.phoneNumber = contact.phoneNumber;
        existing.emailAddress = contact.emailAddress;
        repository.persist(existing); // Use persist here to update the existing contact
        return Optional.of(existing);
    }

    /**
     * Delete a contact by ID.
     * @param id The ID of the contact to delete.
     * @return True if the contact was deleted, false if not found.
     */
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
