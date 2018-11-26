import java.sql.Connection;

public class ClientMain {
    public static void main(String[] args) {
        ClientInterface ci = new ClientInterface();
        try {
            ci.startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
