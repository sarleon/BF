package ui;

import rmi.RemoteHelper;
import service.ExecuteService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class MainFrame extends JFrame {
	public RemoteHelper remoteHelper=RemoteHelper.getInstance();
	private JTextArea textArea;
	private JTextArea resultTextArea;
	private JTextArea inputTextArea;
	private JLabel accountLabel;
	private JLabel textAreaLabel;


	private int undoPointer=0;
	private ArrayList<String> pastCodeArrayList=new ArrayList<String >();

	public MainFrame() {
		UIManager.put("Menu.font",new Font("Lato",Font.PLAIN,25));
		UIManager.put("MenuItem.font",new Font("Lato",Font.PLAIN,25));
		UIManager.put("JLabel.font",new Font("Lato",Font.PLAIN,35));
		UIManager.put("Border.font",new Font("Lato",Font.PLAIN,35));





		/*Border*/
		TitledBorder textAreaBorder,inputBorder,outputBorder;

		textAreaBorder=BorderFactory.createTitledBorder(null,"Source Code Area",2,0,new Font("VAG Rounded",Font.PLAIN,35),Color.RED);
		inputBorder=BorderFactory.createTitledBorder(null,"input params Area",2,0,new Font("VAG Rounded",Font.PLAIN,35),Color.RED);
		outputBorder=BorderFactory.createTitledBorder(null,"output Area",2,0,new Font("VAG Rounded",Font.PLAIN,35),Color.RED);

		textAreaBorder.setBorder(BorderFactory.createLineBorder(Color.blue,5));
		inputBorder.setBorder(BorderFactory.createLineBorder(Color.YELLOW,5));
		outputBorder.setBorder(BorderFactory.createLineBorder(Color.YELLOW,5));

		// 创建窗体
		JFrame frame = new JFrame("BF Client");
		frame.setLayout(new BorderLayout());


		/*menu part*/
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Lato",1,20));
		accountLabel=new JLabel("                                                                                                                                                                                                                                    "+"unlogin");
		accountLabel.setFont(new Font("Lato",Font.BOLD,25));
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

		/*第3栏*/
		JMenu editMenu=new JMenu("Edit");
		menuBar.add(editMenu);
		JMenuItem undoMenuItem=new JMenuItem("Undo");
		editMenu.add(undoMenuItem);
		JMenuItem redoMenuItem=new JMenuItem("Redo");
		editMenu.add(redoMenuItem);

		/*第4栏*/
		JMenu historyMenu=new JMenu("Version");
		menuBar.add(historyMenu);
		JMenuItem checkCodeMenuItem=new JMenuItem("Check history code");
		historyMenu.add(checkCodeMenuItem);


		/*第5栏*/
		JMenu accountMenu=new JMenu("Login");
		menuBar.add(accountMenu);
		JMenuItem loginMenuItem=new JMenuItem("Login");
		accountMenu.add(loginMenuItem);
		JMenuItem logoutMenuItem=new JMenuItem("Logout");
		accountMenu.add(logoutMenuItem);
		JMenuItem registerMenuItem=new JMenuItem("Register");
		accountMenu.add(registerMenuItem);

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
		undoMenuItem.addActionListener(new UndoMenuItemActionListener());
		redoMenuItem.addActionListener(new RedoMenuItemActionListener());

		menuBar.add(accountLabel);


		textArea = new JTextArea("",20,10);
		textArea.setFont(new Font("Monaco",1,25));
		textArea.setBorder(textAreaBorder);
		textArea.setBackground(Color.WHITE);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);


		frame.add(textArea, BorderLayout.NORTH);



		//输入
		inputTextArea=new JTextArea("input",10,45);
		inputTextArea.setFont(new Font("Lato",1,25));
		inputTextArea.setBorder(inputBorder);
		inputTextArea.setLineWrap(true);
		inputTextArea.setWrapStyleWord(true);
		frame.add(inputTextArea,BorderLayout.LINE_START);


		// 显示结果
		resultTextArea=new JTextArea("",10,45);
		resultTextArea.setFont(new Font("Lato",1,25));
		resultTextArea.setEditable(false);
		resultTextArea.setBorder(outputBorder);
		resultTextArea.setLineWrap(true);
		resultTextArea.setWrapStyleWord(true);
		frame.add(resultTextArea,BorderLayout.LINE_END);

		pastCodeArrayList.add("");
		Thread thread=new Thread(new saveBackupsThread());
		thread.start();

		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
				newFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);


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


	class UndoMenuItemActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			undoCode();
		}
	}


	class RedoMenuItemActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			redoCode();
		}
	}

	public void undoCode(){
		System.out.println(undoPointer);
		if(undoPointer>0) {
			undoPointer--;
			setCodeText(pastCodeArrayList.get(undoPointer));
		}
	}

	public void redoCode(){
		System.out.println(undoPointer);
		if(undoPointer<(pastCodeArrayList.size()-1)) {
			undoPointer++;
			setCodeText(pastCodeArrayList.get(undoPointer));
		}
	}

	public void saveBackups(){

	}
	class saveBackupsThread implements Runnable{

		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.MILLISECONDS.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				boolean flag = true;
				for (int i = 0; i < pastCodeArrayList.size(); i++) {
					if (getCodeText().equals(pastCodeArrayList.get(i))) {
						flag = false;
					}
				}

				if (flag) {
					System.out.println("save a copy");
					pastCodeArrayList.add(getCodeText());
					undoPointer = pastCodeArrayList.size() - 1;
				}
			}
		}
	}



	public void accoutLogin(String username){
		accountLabel.setText("                                                                                                                                                                                                                                    User:"+ username);

	}
	public void accoutUnLogin(){
		remoteHelper.setUsername("");
		remoteHelper.setLogin(false);
		accountLabel.setText("                                                                                                                                                                                                                                    unlogin");
	}
	public String  getCodeText(){
		return  textArea.getText();
	}
	public void setCodeText(String text){
		textArea.setText(text);
	}
}
