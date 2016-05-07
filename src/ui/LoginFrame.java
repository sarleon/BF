package ui;

import rmi.RemoteHelper;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;


public class LoginFrame extends JFrame {
    private RemoteHelper remoteHelper=RemoteHelper.getInstance();
    private JButton loginButton;
    private JButton cancelButton;
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private MainFrame mainFrame;
    public LoginFrame(MainFrame mainFrame){
        this.mainFrame=mainFrame;
        loginButton=new JButton("Submit");
        cancelButton=new JButton("Cancel");
        usernameInput=new JTextField();
        passwordInput=new JPasswordField();

        usernameLabel=new JLabel("username");
        passwordLabel=new JLabel("password");

        loginButton.setSize(100,50);
        cancelButton.setSize(100,50);

        cancelButton.addActionListener(e -> {
            dispose();
        });

        loginButton.addActionListener(e -> {
            UserService userService=remoteHelper.getUserService();
            String username=usernameInput.getText().trim();
            String password=new String(passwordInput.getPassword()).trim();

            try {
                if(userService.login(username,password)==true){
                    remoteHelper.setUsername(username);
                    remoteHelper.setLogin(true);
                    mainFrame.accoutLogin(username);


                }
             dispose();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }

        });

        cancelButton.setFont(new Font("Dialog",1,20));
        loginButton.setFont(new Font("Dialog",1,20));
        usernameLabel.setFont(new Font("Dialog",1,20));
        passwordLabel.setFont(new Font("Dialog",1,20));
        usernameInput.setFont(new Font("Dialog",1,20));
        passwordInput.setFont(new Font("Dialog",1,20));

        add(usernameLabel);
        add(usernameInput);
        add(passwordLabel);
        add(passwordInput);
        add(loginButton);
        add(cancelButton);
        setLocation(800,400);
        setSize(400,300);
        setTitle("login");
        setFont(new Font("Dialog",Font.BOLD,30));
        setVisible(true);
        setLayout(new GridLayout(3,2));
    }
}
