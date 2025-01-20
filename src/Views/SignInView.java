package Views;

import javax.swing.*;

import Controllers.AuthController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Services.AuthService;

public class SignInView extends JFrame {

    private JTextField usernameField;
    private JPasswordField pinField;
    private AuthController authController;

    public SignInView() {
    	authController = new AuthController();
        setTitle("Sign In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 160, 25);
        panel.add(usernameField);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(10, 50, 80, 25);
        panel.add(pinLabel);

        pinField = new JPasswordField();
        pinField.setBounds(100, 50, 160, 25);
        panel.add(pinField);

        JButton signInButton = new JButton("Sign In");
        signInButton.setBounds(100, 80, 160, 25);
        panel.add(signInButton);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String pin = new String(pinField.getPassword());
                if (authController.authenticate(username, pin)) {
                    JOptionPane.showMessageDialog(null, "Sign-in successful!");
                    dispose();
                    new DashboardView(username).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Sign-in failed!");
                }
            }
        });
    }
}
