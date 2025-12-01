package chat_server;

import java.util.HashMap;
import java.util.Map;

public class ChatRouter {

    // Maps patientId -> doctorId
    private static Map<String, String> routing = new HashMap<>();

    // Assign doctor to patient
    public static void assign(String patientId, String doctorId) {
        routing.put(patientId, doctorId);
    }

    // Check if two users should talk together
    public static boolean canCommunicate(String sender, String receiver) {
        return (routing.containsKey(sender) && routing.get(sender).equals(receiver)) ||
                (routing.containsKey(receiver) && routing.get(receiver).equals(sender));
    }
}
