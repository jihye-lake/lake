package java_0819;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends Frame implements ActionListener, Runnable {
	Button btn_exit; // 종료 버튼
	Button btn_send; // 문자열 전송
	Button btn_connect; // 서버 접속
	TextArea txt_list; // 접속 목록 출력
	TextField txt_server_ip; // 접속 서버 아이피
	TextField txt_name; // 접속 이름
	TextField txt_input; // 전송 메시지
	Socket client; // 접속 클라이언트 소켓
	BufferedReader br = null; // 읽어오기 스트림
	PrintWriter pw = null; // 보내기 스트림
	String server_ip = null; // 서버 아이피
	final int port = 2002; // 포트 번호와 ip address 필수

	CardLayout cl; // 카드 레이아웃 설정

	// 생성자
	public ChatClient(String title) {
		super(title); // 타이틀바 제목
		cl = new CardLayout(); // 카드 레이아웃 초기화
		setLayout(cl); // 레이아웃 변경
		Panel connect = new Panel(); // 접속 화면
		connect.setLayout(new BorderLayout());
		Panel chat = new Panel(); // 채팅 화면
		chat.setLayout(new BorderLayout());

		btn_connect = new Button("서버 접속"); // 버튼 생성
		btn_connect.addActionListener(this); // 이벤트 등록
		txt_server_ip = new TextField("192.168.0.92", 15); // 192.168.0.90 교수님 아이피로 해서 접속
		txt_name = new TextField("간zl휴먼JJang지혜", 15);
		Panel connect_sub = new Panel(); // 접속창 하위 패널
		connect_sub.add(new Label("서버 아이피 : "));
		connect_sub.add(txt_server_ip);
		connect_sub.add(new Label("대화명 : "));
		connect_sub.add(txt_name);
		connect.add("North", new Label("다중 채팅 접속 화면", Label.CENTER));
		connect.add("Center", connect_sub);
		connect.add("South", btn_connect);

		// 채팅창 화면 구성
		txt_list = new TextArea();
		txt_input = new TextField("", 25);
		btn_exit = new Button("종료");
		btn_send = new Button("전송");
		btn_exit.addActionListener(this); // 이벤트 등록  // 적는 곳, 전송, 종료 세 개의 이벤트
		btn_send.addActionListener(this); // 이벤트 등록
		txt_input.addActionListener(this); // 이벤트 등록
		Panel chat_sub = new Panel(); // 채팅창 하위 패널
		chat_sub.add(txt_input);
		chat_sub.add(btn_send);
		chat_sub.add(btn_exit);
		chat.add("North", new Label("채팅 프로그램 ver 1.0", Label.CENTER));
		chat.add("Center", txt_list);
		chat.add("South", chat_sub);

		add(connect, "접속창"); // 카드 레이아웃에 등록
		add(chat, "채팅창"); // 카드 레이아웃에 등록

		cl.show(this, "접속창"); // 초기화면은 접속창

		setSize(300, 300);
		setVisible(true);
	}
	// Runnable 인터페이스 run() 메소드 오버라이딩

	@Override
	public void run() {
		try {
			client = new Socket(server_ip, port); // 소켓 생성
			// 입출력 스트림 생성
			InputStream is = client.getInputStream();
			OutputStream os = client.getOutputStream();
			br = new BufferedReader(new InputStreamReader(is));  // 효율성있게 만들어줌
			pw = new PrintWriter(new OutputStreamWriter(os));  // 효율성있게 만들어줌
			String msg = txt_name.getText();  // 이름속에 들은 글자를 가져오겠다는 뜻
			pw.println(msg); // 대화명 전송
			pw.flush();
			txt_input.requestFocus(); // 채팅창 글 입력 상자에 포커스(커서를 그곳에 두겠다)
			while (true) {
				msg = br.readLine();		// 서버 데이터 수신
				txt_list.append(msg + "\n");
			}
		} catch (IOException e) { // 예외 처리
			System.out.print(e.getMessage());
		}

	}

	// ActionEvent 이벤트 처리
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Object obj = e.getSource();
			// 서버 접속 버튼이 눌렸을 때
			if (obj == btn_connect) {
				server_ip = txt_server_ip.getText();
				Thread th = new Thread(this);
				th.start(); // 스레드 시작
				cl.show(this, "채팅창"); // 채팅창으로 화면 전환
			}
			// 종료 버튼이 눌렸을 때
			else if (obj == btn_exit) {
				System.exit(0); // 프로그램 강제 종료
			}
			// 전송 버튼을 눌렀을 때
			// 글 입력 상자에서 엔터키를 쳤을 때
			else if (obj == btn_send || obj == txt_input) {  // 엔터키를 눌렀거나 전송을 누른다면 메시지가 나가게 됨
				String msg = txt_input.getText();
				pw.println(msg);
				pw.flush();
				txt_input.setText("");
				txt_input.requestFocus();

			}

		} catch (Exception ex) { // 예외처리
			txt_list.append(ex.getMessage() + "\n");
		}

	}

	// main()메소드
	public static void main(String[] args) {
		new ChatClient("채팅 클라이언트 프로그램");
	}
}
