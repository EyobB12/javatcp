package Client.src;
/*
Adama Science and Technology University
School of Electrical Engineering And Computing
Department of computer science and engineering
Name: EYOB BAHIRU
ID : UGR/34360/16
Section: 03 Group: 06
*/
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 1234);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            String msg;
            while ((msg = user.readLine()) != null) {
                out.println(msg);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
