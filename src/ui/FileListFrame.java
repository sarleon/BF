package ui;

import service.IOService;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

/**
 * Created by sarleon on 16-5-7.
 */
public class FileListFrame extends JFrame{
    private JComboBox<String> fileComboBox;
    private JButton confirmButton;
    private JButton cancelButton;
    public FileListFrame(MainFrame mainFrame){
        IOService ioService=mainFrame.remoteHelper.getIOService();

        try {
            String[] fileList=ioService.readFileList(mainFrame.remoteHelper.getUsername());
            fileComboBox=new JComboBox<String >();
            for (int i = 0; i < fileList.length; i++) {
                fileComboBox.addItem(fileList[i]);
            }


        } catch (RemoteException e) {
            e.printStackTrace();
        }

        confirmButton=new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            try {
                String filename=(String)fileComboBox.getSelectedItem();
                mainFrame.remoteHelper.setCurrentFile(filename);
                String codeText=ioService.readFile(mainFrame.remoteHelper.getUsername(),filename);
                mainFrame.setCodeText(codeText);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            dispose();

        });

        cancelButton=new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            dispose();
        });

        setLayout(null);
        fileComboBox.setFont(new Font("Lato",1,25));
        confirmButton.setFont(new Font("Lato",1,20));
        cancelButton.setFont(new Font("Lato",1,20));
        fileComboBox.setBounds(40,30,300,40);
        confirmButton.setBounds(30,100,120,30);
        cancelButton.setBounds(210,100,120,30);
        add(confirmButton);
        add(cancelButton);
        add(fileComboBox);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(800,400,400,240);


    }
}
