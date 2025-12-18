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

public class LoginUI extends JFrame {

    public LoginUI() {
        setTitle("Chat Login Page");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color bgDark = new Color(30, 30, 30);
        Color accentBlue = new Color(33, 150, 243);

        JPanel panel = new JPanel();
        panel.setBackground(bgDark);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("CHAT APPLICATION LOGIN PAGE  ", JLabel.CENTER);

        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        JLabel nameLabel = new JLabel("Enter Your Username :");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton loginButton = new JButton("Join Chat");
        loginButton.setBackground(accentBlue);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setFocusPainted(false);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridy++;
        panel.add(nameLabel, gbc);

        gbc.gridy++;
        panel.add(nameField, gbc);

        gbc.gridy++;
        panel.add(loginButton, gbc);

        add(panel);

        // Button action
        loginButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                dispose(); // close login window
                new ChatClientUI(name).setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new LoginUI().setVisible(true));
    }
}
