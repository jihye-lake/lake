package game_03;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class game_04 {

   public static void main(String[] args) {      // 메인 클래스 - 프레임 클래스는 아래에
      
      myGame_Frame mgf = new myGame_Frame();
   }	
}

	class myGame_Frame extends JFrame implements Runnable {
		
		int frame_w;
		int frame_h;
		
		Thread th1;
		
		int char_x, char_y;
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		myGame_Frame() {
			
			init();      // 프레임에 들어갈 컴포넌트 세팅 메소드
		    start();
			
			setTitle("1234");
			setSize(frame_w, frame_h);
			
			Dimension screen = tk.getScreenSize();
			
			int frame_xpos = (int)(screen.getWidth() / 2 - frame_w / 2);
			int frame_ypos = (int)(screen.getWidth() / 2 - frame_h / 2);
			
			this.setBackground(new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)));
			setLocation(frame_xpos, frame_ypos);
			setResizable(false);
			setVisible(true);
		}
		
		   public void init() {		      
		      
		      frame_w = 800;
		      frame_h = 600;
		   }
		     
		   public void start() {
		      
		      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      // 프레임 오른쪽 상단의 X 버튼을 눌렀을 때, 프로그램이 정상적으로 종료됨
		      		      
		      th1 = new Thread(this);   // 스레드 생성
		      th1.start();                  // 스레드 실행
//			      th2 = new Thread(this);   // 스레드 생성
//			      th2.start();                  // 스레드 실행
		      
		   }
		   
		   public void run() {      // 스레드가 무한 루프될 부분
		      try {      // 예외옵션 설정으로 에러 방지
		         while(true) {
		            repaint();   // 갱신된 x,y 값으로 이미지 새로 그리기
		            Thread.sleep(20);   // 20 milli sec 로 스레드 돌리기
		            
		         }
		      } catch(Exception e) {}      
		   }
	}
