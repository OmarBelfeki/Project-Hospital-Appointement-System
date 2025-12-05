package hospitalsystem.controllers;

import common.dao.AppointmentDAO;
import common.database.Database;
import common.models.Appointment;
import hospitalsystem.controllers.dto.AppointmentRequest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/appointments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AppointmentController {

    private AppointmentDAO dao;

    public AppointmentController() {
        Connection conn = Database.getConnection();
        dao = new AppointmentDAO(conn);
    }

    @POST
    public Response bookAppointment(AppointmentRequest dto) throws SQLException {

        Appointment apt = new Appointment(
                dto.id,
                dto.patientId,
                dto.doctorId,
                dto.date
        );

        boolean added = dao.insertAppointment(
                apt.getId(),
                apt.getPatientId(),
                apt.getDoctorId(),
                apt.getDate()
        );

        if (added) return Response.ok(apt).build();

        return Response.status(Response.Status.CONFLICT)
                .entity("Appointment could not be created")
                .build();
    }

    @GET
    @Path("/patient/{patientId}")
    public List<String> getPatientAppointments(@PathParam("patientId") String patientId) throws SQLException {
        return dao.getAppointmentsForPatient(patientId);
    }

    @GET
    @Path("/doctor/{doctorId}")
    public List<String> getDoctorAppointments(@PathParam("doctorId") String doctorId) throws SQLException {
        return dao.getAppointmentsForDoctor(doctorId);
    }

    @DELETE
    @Path("/{id}")
    public Response cancelAppointment(@PathParam("id") String id) throws SQLException {
        boolean deleted = dao.deleteAppointment(id);
        if (deleted) return Response.ok().build();

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Appointment not found")
                .build();
    }
}
