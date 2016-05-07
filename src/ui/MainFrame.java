package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.*;

import org.omg.PortableInterceptor.ACTIVE;
import rmi.RemoteHelper;
import service.ExecuteService;


public class MainFrame extends JFrame {
	public RemoteHelper remoteHelper=RemoteHelper.getInstance();
	private JTextArea textArea;
	private JTextArea resultTextArea;
	private JTextArea inputTextArea;
	private JLabel accountLabel;

	public MainFrame() {
		UIManager.put("Menu.font",new Font("Lato",Font.PLAIN,25));
		UIManager.put("MenuItem.font",new Font("Lato",Font.PLAIN,25));
		UIManager.put("JLabel.font",new Font("Lato",Font.PLAIN,35));


		// 创建窗体
		JFrame frame = new JFrame("BF Client");
		frame.setLayout(new BorderLayout());


		/*menu part*/
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Lato",1,20));
		accountLabel=new JLabel("                                                                            "+"unlogin");
		accountLabel.setFont(new Font("Monospace",Font.BOLD,25));
		accountLabel.setBackground(Color.red);


		/*第一栏*/
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		JMenuItem saveMenuItem = new JMenuItem("Save as");
		fileMenu.add(saveMenuItem);
		JMenuItem saveNewMenuItem = new JMenuItem("Save");
		fileMenu.add(saveNewMenuItem);
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);




		/*第二栏*/
		JMenu runMenu =new JMenu("Run");
		menuBar.add(runMenu);
		JMenuItem runMenuItem = new JMenuItem("Run");
		runMenu.add(runMenuItem);

		/*第三栏*/
		JMenu historyMenu=new JMenu("History");
		menuBar.add(historyMenu);
		JMenuItem checkCodeMenuItem=new JMenuItem("Check history code");
		historyMenu.add(checkCodeMenuItem);


		/*第四栏*/
		JMenu accountMenu=new JMenu("account");
		menuBar.add(accountMenu);
		JMenuItem loginMenuItem=new JMenuItem("Login");
		accountMenu.add(loginMenuItem);
		JMenuItem logoutMenuItem=new JMenuItem("Logout");
		accountMenu.add(logoutMenuItem);
		frame.setJMenuBar(menuBar);




		saveNewMenuItem.addActionListener(new SaveNewMenuItemActionListener());
		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenuItem.addActionListener(new OpenActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		exitMenuItem.addActionListener(new MenuItemActionListener());

		loginMenuItem.addActionListener(new AccoutMenuItemActionListener());
		logoutMenuItem.addActionListener(new AccoutMenuItemActionListener());
		runMenuItem.addActionListener(new RunMenuItemActionListener());
		checkCodeMenuItem.addActionListener(new HistoryMenuItemActionListen());
		menuBar.add(accountLabel);
		textArea = new JTextArea("",20,10);
		textArea.setFont(new Font("Lato",1,25));
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setBackground(Color.LIGHT_GRAY);


		frame.add(textArea, BorderLayout.NORTH);



		//输入
		inputTextArea=new JTextArea("input",10,45);
		inputTextArea.setFont(new Font("Lato",1,25));
		frame.add(inputTextArea,BorderLayout.LINE_START);


		// 显示结果
		resultTextArea=new JTextArea("output",10,45);
		frame.add(resultTextArea,BorderLayout.LINE_END);
		resultTextArea.setFont(new Font("Lato",1,25));


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1920, 1080);
		frame.setLocation(0, 0);
		frame.setVisible(true);
	}

	class SaveNewMenuItemActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!remoteHelper.isLogin()){
				System.out.println(remoteHelper.isLogin());
				new AlertFrame();
//				new JOptionPane().createDialog("Please login first");
			} else {



				String code = textArea.getText();
				try {

					RemoteHelper.getInstance().getIOService().writeFile(code,remoteHelper.getUsername(), remoteHelper.getCurrentFile());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

	}
	class HistoryMenuItemActionListen implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!remoteHelper.isLogin()){
				System.out.println(remoteHelper.isLogin());
				new AlertFrame();
//				new JOptionPane().createDialog("Please login first");
			} else {
				new FileCopyListFrame(MainFrame.this);
			}
		}
	}
	class RunMenuItemActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			ExecuteService executeService=remoteHelper.getExecuteService();
			String code=textArea.getText();
			String param=inputTextArea.getText();
			try {
				String result=executeService.execute(code,param);
				System.out.println(result);
				resultTextArea.setText(result);
				resultTextArea.repaint();

					} catch (RemoteException e1) {
				e1.printStackTrace();
			}

		}
	}
	class AccoutMenuItemActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd=e.getActionCommand();
			switch (cmd){
				case "Login":new LoginFrame(MainFrame.this);break;
				case "Logout":
					try {
						remoteHelper.getUserService().logout(remoteHelper.getUsername());
						remoteHelper.setLogin(false);
						MainFrame.this.accoutUnLogin();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					break;
			}
		}
	}


	class MenuItemActionListener implements ActionListener {
		/**
		 * 子菜单响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Open")) {
			} else if (cmd.equals("Save")) {
			} else if (cmd.equals("New")) {

				MainFrame newFrame=new MainFrame();
				newFrame.accoutLogin(remoteHelper.getUsername());
				newFrame.remoteHelper.setLogin(true);
				newFrame.remoteHelper.setUsername(remoteHelper.getUsername());
				newFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


			} else if(cmd.equals("Exit")){
				new ExitFrame();
			}
		}
	}
	class OpenActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!remoteHelper.isLogin()){
				System.out.println(remoteHelper.isLogin());
				new AlertFrame();
//				new JOptionPane().createDialog("Please login first");
			} else {
				new FileListFrame(MainFrame.this);
			}

		}
	}
	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!remoteHelper.isLogin()){
				System.out.println(remoteHelper.isLogin());
				new AlertFrame();
//				new JOptionPane().createDialog("Please login first");
			} else {



				String code = textArea.getText();
				try {
					new FileNameFrame(MainFrame.this);
				//	RemoteHelper.getInstance().getIOService().writeFile(code,remoteHelper.getUsername(), remoteHelper.getCurrentFile());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	public void accoutLogin(String username){
		accountLabel.setText("                                                                            User:"+ username);

	}
	public void accoutUnLogin(){
		remoteHelper.setUsername("");
		remoteHelper.setLogin(false);
		accountLabel.setText("                                                                            unlogin");
	}
	public String  getCodeText(){
		return  textArea.getText();
	}
	public void setCodeText(String text){
		textArea.setText(text);
	}
}
