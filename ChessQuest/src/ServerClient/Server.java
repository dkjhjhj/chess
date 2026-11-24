package ServerClient;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Thread;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

//2271103 박성민
public class Server {
	private JFrame frame;
	private JTextArea t_display;
	private JTextField t_input;
	private JButton b_send, b_connect, b_disconnect;
	private JButton b_exit;
	private int port;
	private ServerSocket serverSocket;
	private Thread acceptThread = null;
	private Vector<ClientHandler> users = new Vector<>();
	
	public Server(int port) {
		this.port = port;
		frame = new JFrame("Multi Chat Server");
		buildGUI();
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	private void buildGUI() {
		frame.add(creatDisplayPanel(),BorderLayout.CENTER);
		JPanel P_input = new JPanel(new BorderLayout());
		//P_input.add(creatInputPanel(),BorderLayout.NORTH);
		P_input.add(creatControlPanel(),BorderLayout.SOUTH);
		frame.add(P_input,BorderLayout.SOUTH);
	}
	private JPanel creatDisplayPanel() {
		t_display = new JTextArea("");
		t_display.setEditable(false);
		JPanel P_Display = new JPanel(new BorderLayout());
		P_Display.add(t_display);
		return P_Display;
	}
	/*private JPanel creatInputPanel() {
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
				sendMessage();
				//receiveMessage();
			}
		});
		t_input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent es) {
				sendMessage();
				//receiveMessage();
			}
		});
		JPanel P_InputPanel = new JPanel(new BorderLayout());
		P_InputPanel.add(t_input,BorderLayout.CENTER);
		P_InputPanel.add(b_send,BorderLayout.EAST);
		return P_InputPanel;
	}*/
	private JPanel creatControlPanel() {
		b_connect = new JButton("서버 시작");
		b_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//startServer();
				//t_input.setEnabled(true);
				//b_send.setEnabled(true);
				b_disconnect.setEnabled(true);
				b_connect.setEnabled(false);
				b_exit.setEnabled(false);
				//connectToServer();
				acceptThread = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						startServer();
					}
					
				});
				acceptThread.start();
			}
		});
		b_disconnect = new JButton("서버 종료");
		b_disconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b_disconnect.setEnabled(false);
				b_connect.setEnabled(true);
				b_exit.setEnabled(true);
				//b_send.setEnabled(false);
				disconnect();
			}
		});
		b_exit = new JButton("종료");
		b_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		JPanel ControlPanel = new JPanel(new GridLayout(1,0));
		ControlPanel.add(b_connect);
		ControlPanel.add(b_disconnect);
		ControlPanel.add(b_exit);
		b_disconnect.setEnabled(false);
		return ControlPanel;
	}
	private void startServer() {
		Socket clientSocket = null;
		
		try {
			serverSocket = new ServerSocket(port);
			printDisplay("서버가 시작되었습니다.");

			while (acceptThread == Thread.currentThread()) {
				clientSocket = serverSocket.accept();
				printDisplay("클라이언트가 연결되었습니다.");
				ClientHandler cHandler = new ClientHandler(clientSocket);
				users.add(cHandler);
				cHandler.start();
			}
		}catch (SocketException e){
			printDisplay("서버 소켓 종료");
		}
		catch (IOException e){
			printDisplay("서버 오류> " + e.getMessage());
			System.err.println("서버 오류> " + e.getMessage());
			System.exit(-1);
		}
		finally {
			try {
				if(clientSocket != null) clientSocket.close();
			} catch (IOException e){
				printDisplay("서버 닫기 오류> " + e.getMessage());
				System.err.println("서버 닫기 오류> " + e.getMessage());
				System.exit(-1);
			}
		}
	}
	private void disconnect() {
		try {
			acceptThread = null;
			serverSocket.close();
		} catch (IOException e){
			System.err.println("클라이언트 닫기 오류> " + e.getMessage());
			System.exit(-1);
		}
	}
	private void printDisplay(String msg) {
		t_display.append(msg + "\n");
		t_display.setCaretPosition(t_display.getDocument().getLength());
	}
	
	
	public class ClientHandler extends Thread{
		private Socket clientSocket;
		private String uid;
		private BufferedWriter out;
		
		public ClientHandler(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		/*private void receiveMessages(Socket cs) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream(),"UTF-8"));
				out = new BufferedWriter(new OutputStreamWriter(cs.getOutputStream(),"UTF-8"));
				String msg;
				while ((msg = in.readLine())!= null) {
					if(msg.startsWith("/uid:")) {//msg의 첫시작문자가 /uid: 맞으면
						uid = msg.substring(5);//msg의 5번인덱스부터 끝까지
						printDisplay("새 참가자: " +uid);
						printDisplay("현재 참가자 수:"+users.size());
					}
					else {
						msg=uid + ":" + msg;
						printDisplay(msg);
						//out.write("'" + msg + "' ... echo\n");
						//out.flush();
						broadcasting(msg);
					}
				}
				users.remove(this);
				printDisplay("클라이언트가 연결을 종료했습니다.");
				printDisplay("현재 참가자 수:"+users.size());//벡터의 개수
			} catch (IOException e){
				printDisplay("서버 읽기 오류> " + e.getMessage());
				printDisplay("클라이언트가 연결을 종료했습니다.");
			}
			finally {
				try {
					cs.close();
				} catch (IOException e){
					printDisplay("서버 닫기 오류> " + e.getMessage());
					System.err.println("서버 닫기 오류> " + e.getMessage());
					System.exit(-1);
				}
			}
		}
		private void sendMessage(String msg) {
			try {
				out.write(msg + "\n");
				out.flush();
			} catch (IOException e){
				System.err.println("클라이언트 쓰기 오류> " + e.getMessage());
				return;
			} 
		}
		private void broadcasting(String msg) {
			for(int i=0;i<users.size();i++) {
				users.get(i).sendMessage(msg);
			}
		}*/
		
		@Override
		public void run() {
			//receiveMessages(clientSocket);
		}
	}
	
	public static void main(String[] args) {
		int serverPort = 54321;
		Server a = new Server(serverPort);
	}
}