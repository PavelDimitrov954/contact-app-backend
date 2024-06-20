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

@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("USER")
public class ContactResource {

    @Inject
    ContactService contactService;

    @GET
    public List<Contact> listAll(@QueryParam("filter") String filter) {
        return contactService.listAllContacts(filter);
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Optional<Contact> contact = contactService.getContactById(id);
        return contact
                .map(c -> Response.ok(c).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response create(Contact contact) {
        Contact createdContact = contactService.createContact(contact);
        return Response.status(Response.Status.CREATED).entity(createdContact).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id") Long id, Contact contact) {
        Optional<Contact> updatedContact = contactService.updateContact(id, contact);
        return updatedContact
                .map(c -> Response.ok(c).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

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
