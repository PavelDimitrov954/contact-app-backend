package com.contacts.service;

import com.contacts.entities.Contact;
import com.contacts.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for ContactService.
 */
@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository repository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ContactService contactService;

    private Contact contact1;
    private Contact contact2;

    @BeforeEach
    void setUp() {
        contact1 = new Contact();
        contact1.id = 1L;
        contact1.firstName = "John";
        contact1.lastName = "Doe";

        contact2 = new Contact();
        contact2.id = 2L;
        contact2.firstName = "Jane";
        contact2.lastName = "Doe";
    }

    @Test
    public void testListAllContactsNoFilter() {
        when(repository.listAll()).thenReturn(Arrays.asList(contact1, contact2));

        List<Contact> result = contactService.listAllContacts(null);

        assertEquals(2, result.size());
        verify(repository, times(1)).listAll();
    }

    @Test
    public void testListAllContactsWithFilter() {
        when(repository.listAll()).thenReturn(Arrays.asList(contact1, contact2));

        List<Contact> result = contactService.listAllContacts("Jane");

        assertEquals(1, result.size());
        assertEquals("Jane", result.get(0).firstName);
    }

    @Test
    public void testGetContactByIdFound() {
        when(repository.findById(1L)).thenReturn(contact1);

        Optional<Contact> result = contactService.getContactById(1L);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().firstName);
    }

    @Test
    public void testGetContactByIdNotFound() {
        when(repository.findById(1L)).thenReturn(null);

        Optional<Contact> result = contactService.getContactById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    public void testCreateContact() {
        doNothing().when(repository).persist(contact1);

        Contact result = contactService.createContact(contact1);

        assertNotNull(result);
        verify(repository, times(1)).persist(contact1);
    }

    @Test
    public void testUpdateContactFound() {
        when(repository.findById(1L)).thenReturn(contact1);
        when(repository.getEntityManager()).thenReturn(entityManager);
        when(entityManager.merge(contact1)).thenReturn(contact1);

        Optional<Contact> result = contactService.updateContact(1L, contact1);

        assertTrue(result.isPresent());
        verify(entityManager, times(1)).merge(contact1);
    }

    @Test
    public void testUpdateContactNotFound() {
        when(repository.findById(1L)).thenReturn(null);

        Optional<Contact> result = contactService.updateContact(1L, contact1);

        assertFalse(result.isPresent());
        verify(repository, never()).getEntityManager();
    }

    @Test
    public void testDeleteContactFound() {
        when(repository.findById(1L)).thenReturn(contact1);

        boolean result = contactService.deleteContact(1L);

        assertTrue(result);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteContactNotFound() {
        when(repository.findById(1L)).thenReturn(null);

        boolean result = contactService.deleteContact(1L);

        assertFalse(result);
        verify(repository, never()).deleteById(1L);
    }
}
