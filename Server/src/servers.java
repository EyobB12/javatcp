package Server.src;
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
import java.util.*;

public class servers {
    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(1234);
            System.out.println("Server started...");

            while (true) {
                Socket socket = server.accept();
                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public static class ClientHandler implements Runnable {
        Socket socket;
        BufferedReader in;
        PrintWriter out;
        String name;

        public ClientHandler(Socket socket) {
            try {
                this.socket = socket;
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("Enter your name:");
                name = in.readLine();
                broadcast(name + " joined the chat", this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println(name + ": " + msg);
                    broadcast(name + ": " + msg, this);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    broadcast(name + " left the chat", this);
                    clients.remove(this);
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String msg) {
            out.println(msg);
        }
    }
}
