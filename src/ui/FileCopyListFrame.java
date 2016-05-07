package ui;

import service.IOService;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

/**
 * Created by sarleon on 16-5-7.
 */
public class FileCopyListFrame extends JFrame {
    private JComboBox<String> fileComboBox;
    private JButton confirmButton;
    private JButton cancelButton;

    public FileCopyListFrame(MainFrame mainFrame) {
        IOService ioService = mainFrame.remoteHelper.getIOService();

        try {
            String[] fileList = ioService.readFileCopyList(mainFrame.remoteHelper.getUsername(),mainFrame.remoteHelper.getCurrentFile());
            fileComboBox = new JComboBox<String>();
            for (int i = 0; i < fileList.length; i++) {
                fileComboBox.addItem(fileList[i]);
            }


        } catch (RemoteException e) {
            e.printStackTrace();
        }

        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            try {
                String filename = (String) fileComboBox.getSelectedItem();
                mainFrame.remoteHelper.setCurrentFile(filename);
                String codeText = ioService.readFileCopy(mainFrame.remoteHelper.getUsername(), filename);
                mainFrame.setCodeText(codeText);
                mainFrame.remoteHelper.setCurrentFile(filename.split("_")[1]);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            dispose();

        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            dispose();
        });
        add(confirmButton, BorderLayout.LINE_START);
        add(cancelButton, BorderLayout.LINE_END);
        add(fileComboBox, BorderLayout.NORTH);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(800, 400, 400, 300);


    }
}
