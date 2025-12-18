package Client.src;
/*
Adama Science and Technology University
School of Electrical Engineering And Computing
Department of computer science and engineering
Name: EYOB BAHIRU
ID : UGR/34360/16
Section: 03 Group: 06
*/
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ChatClientUI extends JFrame {

    private JTextArea chatArea;
    private JTextArea messageField;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;

    private final Color bgDark = new Color(30, 30, 30);
    private final Color bgLight = new Color(245, 245, 245);
    private final Color accentBlue = new Color(33, 150, 243);

    public ChatClientUI(String name) {

        setTitle("Chat Client window  " + name);
        setSize(700, 520); //
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // HEADER
        JLabel header = new JLabel(" Connected as " + name);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setBackground(bgDark);
        header.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // CHAT AREA
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        chatArea.setBackground(bgLight);
        chatArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane chatScroll = new JScrollPane(chatArea);

        //MESSAGE INPUT
        messageField = new JTextArea(3, 20);
        messageField.setLineWrap(true);
        messageField.setWrapStyleWord(true);
        messageField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane msgScroll = new JScrollPane(messageField);

        JButton sendButton = new JButton("Send");
        sendButton.setBackground(accentBlue);
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendButton.setFocusPainted(false);

        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        bottom.setBackground(bgDark);
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottom.add(msgScroll, BorderLayout.CENTER);
        bottom.add(sendButton, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(chatScroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());

        connectToServer(name);
    }

    private void connectToServer(String name) {
        try {
            socket = new Socket("127.0.0.1", 1234);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            in.readLine(); // server asks name
            out.println(name);

            chatArea.append("#  Connected to server\n\n");

            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        chatArea.append(msg + "\n");
                    }
                } catch (IOException e) {
                    chatArea.append("\n ?? Disconnected\n");
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Server not reachable",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void sendMessage() {
        String msg = messageField.getText().trim();
        if (!msg.isEmpty()) {
            out.println(msg);
            messageField.setText("");
        }
    }
}
