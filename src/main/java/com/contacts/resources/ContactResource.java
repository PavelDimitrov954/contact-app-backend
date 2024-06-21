package com.contacts.resources;

import com.contacts.entities.Contact;
import com.contacts.service.ContactService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

/**
 * Resource class to handle contact-related endpoints.
 */
@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("USER")
public class ContactResource {

    @Inject
    ContactService contactService;

    /**
     * Endpoint to list all contacts with optional filtering.
     * @param filter Optional filter for full-text search.
     * @return List of contacts.
     */
    @GET
    public List<Contact> listAll(@QueryParam("filter") String filter) {
        return contactService.listAllContacts(filter);
    }

    /**
     * Endpoint to get a contact by ID.
     * @param id The ID of the contact.
     * @return The contact if found, otherwise a 404 response.
     */
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Optional<Contact> contact = contactService.getContactById(id);
        return contact
                .map(c -> Response.ok(c).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Endpoint to create a new contact.
     * @param contact The contact to create.
     * @return The created contact with a 201 response.
     */
    @POST
    public Response create(Contact contact) {
        Contact createdContact = contactService.createContact(contact);
        return Response.status(Response.Status.CREATED).entity(createdContact).build();
    }

    /**
     * Endpoint to update an existing contact.
     * @param id The ID of the contact to update.
     * @param contact The updated contact data.
     * @return The updated contact if found, otherwise a 404 response.
     */
    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id") Long id, Contact contact) {
        Optional<Contact> updatedContact = contactService.updateContact(id, contact);
        return updatedContact
                .map(c -> Response.ok(c).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Endpoint to delete a contact by ID.
     * @param id The ID of the contact to delete.
     * @return A 204 response if the contact was deleted, otherwise a 404 response.
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = contactService.deleteContact(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
