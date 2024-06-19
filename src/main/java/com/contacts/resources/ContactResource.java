package com.contacts.resources;

import com.contacts.entities.Contact;
import com.contacts.repository.ContactRepository;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("USER")
public class ContactResource {

    @Inject
    ContactRepository repository;

    @GET
    public List<Contact> listAll(@QueryParam("filter") String filter) {
        if (filter == null || filter.isEmpty()) {
            return repository.listAll();
        } else {
            return repository.listAll().stream()
                    .filter(c -> c.firstName.contains(filter) || c.lastName.contains(filter))
                    .toList();
        }
    }

    @GET
    @Path("/{id}")
    public Contact getById(@PathParam("id") Long id) {
        return repository.findById(id);
    }

    @POST
    @Transactional
    public Response create(Contact contact) {
        repository.persist(contact);
        return Response.status(Response.Status.CREATED).entity(contact).build();
    }


    @PUT
    @Transactional
    @Path("/{id}")
    public Response updateContact(@PathParam("id") Long id, Contact contact) {
        Contact existing = repository.findById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        repository.persist(contact);
        return Response.ok(contact).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        repository.deleteById(id);
        return Response.noContent().build();
    }
}
