package ui;

import service.IOService;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

/**
 * Created by sarleon on 16-5-7.
 */
public class FileNameFrame extends JFrame {
    private JTextField filenameField;
    private JLabel promptLabel;
    private JButton saveButton;
    public FileNameFrame(MainFrame mainFrame){
        filenameField=new JTextField();
        promptLabel=new JLabel("please enter filename" +"\n"+
                "(if the filename is existed,the file will be cover)");
        saveButton=new JButton("save");
        filenameField.setFont(new Font("Lato",1,40));
        promptLabel.setFont(new Font("Lato",1,20));
        saveButton.setFont(new Font("Lato",1,40));
        setVisible(true);
        setBounds(700,400,650,300);
        setLayout(new GridLayout(3,1));
        saveButton.addActionListener(e -> {
            mainFrame.remoteHelper.setCurrentFile(filenameField.getText());
            IOService ioService=mainFrame.remoteHelper.getIOService();
            try {
                ioService.writeFile(mainFrame.getCodeText(),mainFrame.remoteHelper.getUsername(),mainFrame.remoteHelper.getCurrentFile());

            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            dispose();
        });
        add(promptLabel);

        add(filenameField);
        add(saveButton);
    }
}
