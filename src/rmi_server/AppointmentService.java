
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AppointmentService extends Remote {

    String bookAppointment(String patientId, String doctorId, String date)
            throws RemoteException;

    boolean cancelAppointment(String appointmentId)
            throws RemoteException;

    List<String> getPatientAppointments(String patientId)
            throws RemoteException;
}
