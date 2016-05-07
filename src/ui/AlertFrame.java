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
        alertLabel.setFont(new Font("Monospace",Font.BOLD,40));
        cancelButton=new JButton("back");
        cancelButton.addActionListener(e -> {
            dispose();
        });
        add(alertLabel,BorderLayout.CENTER);
        add(cancelButton,BorderLayout.SOUTH);
        setBounds(800,400,400,300);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


    }
}
