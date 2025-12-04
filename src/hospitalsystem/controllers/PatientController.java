package hospitalsystem.controllers;

import common.dao.PatientDAO;
import common.database.Database;
import common.models.Patient;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;

@Path("/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientController {

    private PatientDAO dao;

    public PatientController() {
        Connection conn = Database.getConnection();
        dao = new PatientDAO(conn);
    }

    @POST
    public Response registerPatient(Patient patient) throws SQLException {
        boolean added = dao.insertPatient(patient);
        if (added) return Response.ok(patient).build();
        return Response.status(Response.Status.CONFLICT).entity("Patient ID already exists").build();
    }

    @GET
    @Path("/{id}")
    public Patient getPatient(@PathParam("id") String id) throws SQLException {
        return dao.getPatient(id);
    }
}
