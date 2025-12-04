package hospitalsystem.controllers;

import common.dao.DoctorDAO;
import common.database.Database;
import common.models.Doctor;
import hospitalsystem.controllers.dto.DoctorRequest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/doctors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorController {

    private final DoctorDAO dao;

    public DoctorController() {
        Connection conn = Database.getConnection();
        dao = new DoctorDAO(conn);
    }

    @GET
    public List<Doctor> getAllDoctors() throws SQLException {
        return dao.getAllDoctors();
    }

    @POST
    public Response registerDoctor(DoctorRequest dto) throws SQLException {
        // Convert DTO to model
        Doctor doctor = new Doctor(dto.id, dto.name, dto.specialization, dto.passwordHash);
        System.out.println(doctor);

        boolean added = dao.insertDoctor(doctor);

        if (added) return Response.ok(doctor).build();

        return Response.status(Response.Status.CONFLICT)
                .entity("Doctor ID already exists")
                .build();
    }

    @GET
    @Path("/{id}")
    public Doctor getDoctor(@PathParam("id") String id) throws SQLException {
        return dao.getDoctor(id);
    }
}
