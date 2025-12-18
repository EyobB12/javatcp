import java.io.*;
import java.net.*;
import java.util.*;

public class servers {

    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(1234);
            System.out.println("Server started...");

            // START: Server input thread
            Thread serverInput = new Thread(() -> {
                try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
                    String msg;
                    while ((msg = console.readLine()) != null) {
                        broadcast("SERVER: " + msg, null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            serverInput.start();
            // END

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

    static class ClientHandler implements Runnable {
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
                // Client disconnected
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
