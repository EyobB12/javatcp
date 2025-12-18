import java.io.*;
import java.net.*;
/*
Adama Science and Technology University
School of Electrical Engineering And Computing
Department of computer science and engineering
Name: EYOB BAHIRU
ID : UGR/34360/16
Section: 03 Group: 06
*/
public class tempCodeRunnerFile {
    private static final String SERVER_IP = "127.0.0.1"; // localhost
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            // Thread to read messages from server
            Thread readThread = new Thread(() -> {
                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            readThread.start();

            // Send messages to server
            String userMsg;
            while ((userMsg = userInput.readLine()) != null) {
                out.println(userMsg);
            }

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

