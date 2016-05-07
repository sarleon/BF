package ui;

import rmi.RemoteHelper;
import service.IOService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by sarleon on 16-5-6.
 */
public class ExitFrame extends JFrame {
    private JButton exit;
    private JButton cancel;
    private JButton saveAndExit;
    private JLabel label;
    public ExitFrame(){
        exit=new JButton("退出");
        cancel=new JButton("取消");
        saveAndExit=new JButton("保存并退出");
        label=new JLabel();
        exit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        cancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        saveAndExit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IOService ioService= RemoteHelper.getInstance().getIOService();
//                ioService.writeFile();
                System.exit(0);
            }
        });
        label.setText("确定要退出吗,是否保存?");
        add(label);
        add(exit);
        add(cancel);
        add(saveAndExit);
        this.setLayout(new FlowLayout());
        this.setTitle("退出");
        this.setVisible(true);
        this.setBounds(800,600,400,300);

    }
}
