package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sarleon on 16-5-7.
 */
public class AlertFrame extends JFrame{
    private JLabel alertLabel;
    private JButton cancelButton;
    public AlertFrame(){
        alertLabel=new JLabel("please login first");
        alertLabel.setFont(new Font("Dialog",Font.BOLD,40));
        cancelButton=new JButton("back");
        cancelButton.setFont(new Font("Dialog",Font.BOLD,30));
        cancelButton.addActionListener(e -> {
            dispose();
        });
        setLayout(null);
        alertLabel.setBounds(25,0,420,150);
        cancelButton.setBounds(150,150,150,40);
        add(alertLabel);
        add(cancelButton);
        setBounds(700,400,450,300);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());


    }
}
