package ServerClient;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;

import UI.gameUI;

import java.io.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//2271103 박성민
public class Client {
	private JFrame frame;
	private JTextField t_input;
	private JTextArea t_display;
	private JButton b_connect;
	private JButton b_disconnect;
	private JButton b_send;
	private JButton b_exit;
	private String serverAddress;
	private int serverPort;
	private Socket socket;
	private ObjectOutputStream out;
	private Writer out1;
	private DataInputStream in;
	private Thread receiveThread;
	private JTextField t_userID;
	private JTextField t_address;
	private JTextField t_port;
	private JTextField t_id, t_pt, t_ad;
	private gameUI ui;
	
	
	public Client(String serverAddress, int serverPort) {
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		
		frame = new JFrame("기본 화면");
		buildGUI();
		frame.setSize(700,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}
	private void buildGUI() {
		frame.add(creatDisplayPanel(),BorderLayout.CENTER);
		JPanel P_input = new JPanel(new BorderLayout());
		P_input.add(creatInputPanel(),BorderLayout.NORTH);
		P_input.add(creatInfoPanel(),BorderLayout.CENTER);
		P_input.add(creatControlPanel(),BorderLayout.SOUTH);
		frame.add(P_input,BorderLayout.SOUTH);
	}
	private JPanel creatDisplayPanel() {
		t_display = new JTextArea();
		t_display.setEditable(false);
		JPanel P_Display = new JPanel(new BorderLayout());
		P_Display.add(t_display);
		return P_Display;
	}
	private JPanel creatInputPanel() {
		t_input = new JTextField("");
		b_send = new JButton("보내기");
		b_send.setEnabled(false);
		String text = t_input.getText();
		if (text == null) {
			b_send.setEnabled(false);
		}
		else {
			b_send.setEnabled(true);
		}
		b_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent es) {
				//sendMessage();
				//receiveMessage();
			}
		});
		t_input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent es) {
				//sendMessage();
				//receiveMessage();
			}
		});
		JPanel P_InputPanel = new JPanel(new BorderLayout());
		P_InputPanel.add(t_input,BorderLayout.CENTER);
		P_InputPanel.add(b_send,BorderLayout.EAST);
		return P_InputPanel;
	}
	//private JTextField t_address;
	//private JTextField t_port;
	//private JTextField t_text;
	private JPanel creatInfoPanel() {
		InetAddress local;
		t_userID = new JTextField("");
		/*try {
			local = InetAddress.getLocalHost();
			String addr = local.getHostAddress();
			String[] part = addr.split("\\.");
			t_userID.setText("guest" + part[3]);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String addr = serverAddress;
		String[] part = addr.split("\\.");
		t_userID.setText("guest" + part[3]);
		t_id = new JTextField("닉네임:");
		t_id.setEditable(false);
		t_ad = new JTextField("서버주소:");
		t_ad.setEditable(false);
		t_pt = new JTextField("포트번호:");
		t_pt.setEditable(false);
		t_address = new JTextField(serverAddress);
		t_port = new JTextField(serverPort + "");
		JPanel InfoPanel = new JPanel(new GridLayout(1,0));
		InfoPanel.add(t_id);
		InfoPanel.add(t_userID);
		InfoPanel.add(t_ad);
		InfoPanel.add(t_address);
		InfoPanel.add(t_pt);
		InfoPanel.add(t_port);
		//serverAddress = t_address.getText();
		//serverPort = Integer.parseInt(t_port.getText());
		return InfoPanel;
	}
	private JPanel creatControlPanel() {
		b_connect = new JButton("접속하기");
		b_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b_connect.setEnabled(false);
				b_disconnect.setEnabled(true);
				b_exit.setEnabled(false);
				b_send.setEnabled(true);
				connectToServer();
				ui = new gameUI();
				frame.getContentPane().removeAll();
				frame.add(ui);
				frame.revalidate();
				frame.repaint();
			}
		});
		b_disconnect = new JButton("접속끊기");
		b_disconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b_disconnect.setEnabled(false);
				b_connect.setEnabled(true);
				b_exit.setEnabled(true);
				b_send.setEnabled(false);
				disconnect();
			}
		});
		b_exit = new JButton("종료하기");
		b_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		b_disconnect.setEnabled(false);
		JPanel ControlPanel = new JPanel(new GridLayout(0,3));
		ControlPanel.add(b_connect);
		ControlPanel.add(b_disconnect);
		ControlPanel.add(b_exit);
		
		return ControlPanel;
	}
	private void connectToServer() {
		try {
			socket = new Socket(serverAddress, serverPort);
			out = new ObjectOutputStream(new DataOutputStream(socket.getOutputStream()));
			in = new DataInputStream(socket.getInputStream());
			sendUserID();
			
			
			receiveThread = new Thread(new Runnable() {

				@Override
				public void run() {
					while(receiveThread == Thread.currentThread()) {
						//receiveMessage();
					}
					
				}
				
			});
			receiveThread.start();
		} catch (IOException e){
			System.err.println("클라이언트 접속 오류> " + e.getMessage());
			System.exit(0);
		}
	}
	private void sendUserID() {
		String id = t_userID.getText();
		
		try {
			out1 = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
			out1.write("/uid:" + id + "\n");
			out1.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void disconnect() {
		try {
			receiveThread = null;
			socket.close();
		} catch (IOException e){
			System.err.println("클라이언트 닫기 오류> " + e.getMessage());
			System.exit(-1);
		}
	}
	/*private void sendMessage() {
		try {
			//String msg = t_cal.getText();
			double operand1,operand2;
			char operator;
			//operand1 = Double.parseDouble(t_num1.getText());//스트링문자를 double형으로 변환
			//operand2 = Double.parseDouble(t_num2.getText());
			//operator = msg.charAt(0);//스트링문자를 char형으로 변환
			//ChessInf calcExpr = new ChessInf(operand1,operator,operand2);
			//t_display.append("나: " + msg + "\n");
			//t_display.setCaretPosition(t_display.getDocument().getLength());
			//out.writeObject(calcExpr);
			out.flush();
		} catch (IOException e){
			System.err.println("클라이언트 쓰기 오류> " + e.getMessage());
			return;
		} catch (NumberFormatException e){
			System.err.println("클라이언트 정수 오류> " + e.getMessage());
			return;
		}
	}
	
	private void receiveMessage() {
		try {
			String inMsg = ((BufferedReader)in).readLine();
			t_display.append(inMsg + "\n");
		}catch(IOException e){
			System.out.println("클라이언트 일반 수신 오류> " + e.getMessage());
		}
	}*/

	public static void main(String[] args) {
		String serverAddress = "127.0.0.2";
		int serverPort = 54321;
		Client a = new Client(serverAddress, serverPort);
	}
}