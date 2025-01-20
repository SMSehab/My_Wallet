package Views;

import javax.swing.*;

import Controllers.AuthController;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthView extends JFrame {

    private JTextField usernameField;
    private JPasswordField pinField;
    private AuthController authController;

    public AuthView() {
    	authController = new AuthController();
        setTitle("Register");
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

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(100, 80, 160, 25);
        panel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String pin = new String(pinField.getPassword());
                try {
					if (authController.register(username, pin)) {
					    JOptionPane.showMessageDialog(null, "Registration successful!");
					    dispose(); 
					    new SignInView().setVisible(true);
					} else {
					    JOptionPane.showMessageDialog(null, "Registration failed!");
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        });
    }
}
