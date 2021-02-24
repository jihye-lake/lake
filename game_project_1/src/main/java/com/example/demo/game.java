// 캐릭터가 미사일 발사시 적 퇴치 구현 완료

package com.example.demo;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class game {

   public static void main(String[] args) {      // 메인 클래스 - 프레임 클래스는 아래에
      
      game_Frame fms = new game_Frame();
   }

}
                                                // 키보드 이벤트를 처리하기 위한 KeyListener 상속, 스레드를 돌리기 위한 Runnable 상속
class game_Frame extends JFrame implements KeyListener, Runnable{   // 프레임을 만들기 위한 클래스
                           // 프레임을 생성하기 위하여 JFrame 상속
   
   int f_width;      //프레임 가로값
   int f_height;   // 프레임 세로값
   
   int x, y;   // 캐릭터 좌표 변수
   
   boolean KeyUp = false;      // 키보드 입력 처리를 위한 변수
   boolean KeyDown = false;
   boolean KeyLeft = false;
   boolean KeyRight = false;
   boolean KeySpace = false;   // 미사일 발사를 위한 키
   
   
   int cnt;   // 각종 타이밍 조절을 위해 무한 루프를 카운터할 변수
   
   int e_w, e_h;      // 적 이미지의 크기값을 받을 변수
   int m_w, m_h;   // 미사일 이미지의 크기값을 받을 변수
   
   Thread th;   // 스레드 생성
   
   Toolkit tk = Toolkit.getDefaultToolkit();      // 윈도우의 툴킷을 사용하기 위한 추상 슈퍼 클래스
   Image me_img;         // 내 캐릭터 이미지를 받아들일 변수
   Image Missile_img;   // 미사일 이미지를 받아들일 이미지 변수
   Image Enemy_img;    // 적 이미지를 받아들일 이미지 변수
   
   ArrayList Missile_List = new ArrayList();   // 다수의 미사일을 등장 시켜야 하므로 배열을 사용
   ArrayList Enemy_List = new ArrayList();   // 다수의 적을 등장 시켜야 하므로 배열을 이용
   
   Image buffImage;      // 더블 버퍼링용
   Graphics buffg;      // 더블 버퍼링용
   
   Missile ms;   // 미사일 클래스 접근 키
   Enemy en;   // 적 클래스 접근 키
   
   game_Frame() {   // 프레임 생성
      init();      // 프레임에 들어갈 컴포넌트 세팅 메소드
      start();      // 기본적인 시작 명령 처리 부분
      
      setTitle("슈팅 게임 만들기");      // 프레임 이름 설정
      setSize(f_width, f_height);      // 프레임의 크기를 위의 크기 변수 값을 가져와서 설정해줌
      
      Dimension screen = tk.getScreenSize();      // 현재 모니터 해상도 값을 받아옴

      int f_xpos = (int)(screen.getWidth() / 2 - f_width /2);      // 프레임을 모니터 정중앙에 배치하기 위하여 좌표값을 계산(가로)
      int f_ypos = (int)(screen.getHeight() / 2 - f_height /2);   // 프레임을 모니터 정중앙에 배치하기 위하여 좌표값을 계산(세로)
      
      setLocation(f_xpos, f_ypos);      // 프레임을 화면에 배치
      setResizable(false);      // 프레임의 크기를 임의로 변경할 수 없도록 설정 - 검색하기
      setVisible(true);         // 프레임을 눈에 보이게 띄움 - 검색하기      
   }
   
   public void init() {
      
      x = 100;   // 캐릭터 최초 좌표
      y = 100;   // 캐릭터 최초 좌표
      
      f_width = 800;
      f_height = 600;
      
      me_img = tk.getImage("./src/main/img/char.png");
      Missile_img = tk.getImage("./src/main/img/Missile.png");
      Enemy_img = tk.getImage("./src/main/img/enemy.png");
      
      e_w = ImageWidthValue("./src/main/img/enemy.png");
      e_h = ImageHeightValue("./src/main/img/enemy.png");
      
      m_w = ImageWidthValue("./src/main/img/Missile.png");
      m_h = ImageHeightValue("./src/main/img/Missile.png");
   }
   
   public void start() {
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      // 프레임 오른쪽 상단의 X 버튼을 눌렀을 때, 프로그램이 정상적으로 종료됨
      
      addKeyListener(this);      // 키보드 이벤트 실행
      th = new Thread(this);   // 스레드 생성
      th.start();                  // 스레드 실행
      
   }
   
   public void run() {      // 스레드가 무한 루프될 부분
      try {      // 예외옵션 설정으로 에러 방지
         while(true) {   // while 문으로 무한 루프 시키기
            KeyProcess();   // 키보드 입력처리를 하여 x,y 갱신
            EnemyProcess();   // 적 움직임 처리 메소드 실행
            MissileProcess();   // 미사일 처리 메소드 실행
            repaint();   // 갱신된 x,y 값으로 이미지 새로 그리기
            Thread.sleep(20);   // 20 milli sec 로 스레드 돌리기
            
            cnt ++;   // 무한 루프 카운터
         }
      } catch(Exception e) {}      
   }
   
   
   public void MissileProcess() {   // 미사일 처리 메소드
      if(KeySpace) {   // 스페이스바 키 상태가 true 면
         ms = new Missile(x-100, y-20);      // 미사일 발사 위치를 제대로 하기 위한 좌표조정
         
//         ms = new Missile(x,y);      // 좌표 체크해서 넘기기
         
         Missile_List.add(ms);      // 해당 미사일 추가
      }
      
      for (int i = 0; i < Missile_List.size(); ++i) {
         ms = (Missile) Missile_List.get(i);
         ms.move();
         if (ms.x > f_width - 20) {
            Missile_List.remove(i);
         }   // 편의상 그림그리기 부분에 있던 미사일 이동과 미사일이 화면에서 벗어났을 시 명령처리를 이쪽으로 옮겼음
         
         for (int j = 0; j < Enemy_List.size(); ++j) {
            en = (Enemy) Enemy_List.get(j);
            if (Crash(ms.x, ms.y, en.x, en.y, m_w, m_h, e_w, e_h)) {      // 미사일과 적 객체를 하나하나 판별하여, 접촉했을 시 미사일과 적을 화면에서 지움
                                                                        // 판별에는 Crash 메소드에서 계산하는 방식을 씀
               Missile_List.remove(i);
               Enemy_List.remove(j);
               
            }
         }
      }
   }
   
   public void EnemyProcess() {   // 적 행동 처리 메소드
      
      for (int i = 0; i < Enemy_List.size(); ++i) {
         en = (Enemy)(Enemy_List.get(i));      // 배열에 적이 생성되어있을 때 해당되는 적을 판별
         en.move();   // 해당 적을 이동시킨다.
         if (en.x < -200) {   // 적의 좌표가 화면 밖으로 넘어가면
            Enemy_List.remove(i);   // 해당 적을 배열에서 삭제
            
         }
      }
      
      if (cnt % 300 == 0) {      // 루프 카운트 300회 마다
         en = new Enemy(f_width + 100, 100);
         Enemy_List.add(en);   // 각 좌표로 적을 생산한 후 배열에 추가한다
         en = new Enemy(f_width + 100, 200);
         Enemy_List.add(en);
         en = new Enemy(f_width + 100, 300);
         Enemy_List.add(en);
         en = new Enemy(f_width + 100, 400);
         Enemy_List.add(en);
         en = new Enemy(f_width + 100, 500);
         Enemy_List.add(en);
         
         
      }
   }
   public boolean Crash(int x1, int y1, int x2, int y2, int w1, int h1, int w2, int h2) {
      // 충돌 판정을 위한 새로운 Crash 메소드를 만듦, 판정을 위해 충돌할 두 사각 이미지의 좌표 및 넓이와 높이값을 받아들임
      // 여기서 이미지의 넓이, 높이값을 계산하기 위하여 밑에 보면 이미지 크기 계산용 메소드를 또 추가하였음
      
      boolean check = false;
      
      if (Math.abs((x1 + w1 / 2) - (x2 + w2 / 2)) < (w2 / 2 + w1 / 2) && Math.abs((y1 + h1 / 2) - (y2 + h2 / 2)) < (h2 / 2 + h1 / 2)) {
         // 충돌 계산식. 사각형 두개의 거리 및 겹치는 여부를 확인하는 방식. 더 간단한 법이 있으니 생각해보자!
         
         check = true;   // 위 값이  true 면  check 에  ture 를 전달함
      } else 
         check = false;
      
      return check;
   }
   
   public void paint(Graphics g) {
      
      buffImage = createImage(f_width, f_height);   // 더블 버퍼링 버퍼 크기를 화면 크기와 같게 설정
      
      buffg = buffImage.getGraphics();   // 버퍼의 그래픽 객체를 얻기
      
      update(g);
      
   }
   
   public void update(Graphics g) {
      
      Draw_Char();      // 실제로 그려진 그림을 가져온다
      Draw_Enemy();   // 그려진 적 이미지를 가져온다
      Draw_Missile();   // 그려진 미사일 가져와 실행
      
//      g.drawImage(me_img, x, y, this);   // 변경되는 좌표에 따라 이미지가 새로 그려지게 하기 -> 깜빡이는 게 보기 좋지 않음
      
      g.drawImage(buffImage, 0, 0, this);   // 화면에 버퍼에 그린 그림을 가져와 그리기
      
   }
  
   
   public void Draw_Missile() {   // 미사일 그리는 메소드
      for (int i = 0; i<Missile_List.size(); i++) {   // 미사일 존재 유무를 확인
         
         ms = (Missile) (Missile_List.get(i));   // 미사일 위치값을 확인
         
//         buffg.drawImage(Missile_img, ms.pos.x + 250, ms.pos.y + 55, this);
         buffg.drawImage(Missile_img, ms.x + 250, ms.y + 55, this);   // 변수명 변경으로 위에서 아래로 수정 됨
         // 현재 좌표에 미사일 그리기. 이미지 크기를 감안한 미사일 발사 좌표는 수정됨
 /*        
         ms.move();   // 그려진 미사일을 정해진 숫자만큼 이동시키기
         
         if(ms.pos.x > f_width) {   // 미사일이 화면 밖으로 나가면
            Missile_List.remove(i);   // 미사일 지우기
         }
*/         
      }
   }
   
   public void Draw_Char() {   // 실제로 캐릭터를 그릴 부분
      
      buffg.clearRect(0, 0, f_width, f_height);
      buffg.drawImage(me_img, x, y, this);
   }
   
   public void Draw_Enemy() {   // 적 이미지를 그리는 부분
      for (int i = 0; i < Enemy_List.size(); ++i) {     
         en = (Enemy)(Enemy_List.get(i));
         buffg.drawImage(Enemy_img, en.x, en.y, this);   // 배열에 생성된 각 적을 판별하여 이미지 그리기
      }
      
   }
   
   public void keyPressed(KeyEvent e) {   // 키보드 눌렸을 때 이벤트 처리하는 곳
      
      switch(e.getKeyCode()){
      case KeyEvent.VK_UP :
         KeyUp = true;
         break;
      case KeyEvent.VK_DOWN :
         KeyDown = true;
         break;
      case KeyEvent.VK_LEFT :
         KeyLeft = true;
         break;
      case KeyEvent.VK_RIGHT : 
         KeyRight = true;
         break;
      case KeyEvent.VK_SPACE :   // 스페이스바 클릭시 미사일 발사
         KeySpace = true;
         break;
      }
   }
   
   public void keyReleased(KeyEvent e) {      // 키보드가 눌렸다가 떼어졌을 때 이벤트 처리하는 곳
      
      switch(e.getKeyCode()){
         case KeyEvent.VK_UP :
         KeyUp = false;
         break;
      case KeyEvent.VK_DOWN :
         KeyDown = false;
         break;
      case KeyEvent.VK_LEFT :
         KeyLeft = false;
         break;
      case KeyEvent.VK_RIGHT :
         KeyRight = false;
         break;
      case KeyEvent.VK_SPACE :   // 스페이스바 클릭시 미사일 발사
         KeySpace = false;
         break;
         }
      }
      

   public void keyTyped(KeyEvent e) {}
   // 키보드가 타이핑 될 때 이벤트 처리하는 곳
   
   public void KeyProcess() {
      // 실제로 캐릭터 움직임 실현을 위해 위에서 받아들인 키값을 바탕으로 키 입력시마다 5만큼의 이동을 시킴
      
      if(KeyUp == true) y -= 5;
      if(KeyDown == true) y += 5;
      if(KeyLeft == true) x -= 5;
      if(KeyRight == true) x += 5;
   }
   
   public int ImageWidthValue(String file) {
      // 이미지 넓이 크기 및 계산용 메소드
      // 파일을 받아들여 그 파일 값을 계산하도록 하는 것
      
      int x = 0;
      try {
         File f = new File(file);   // 파일을 받음
         BufferedImage bi = ImageIO.read(f);   //받을 파일을 이미지로 읽어들임
         x = bi.getWidth();      // 이미지의 넓이 값을 받음
      } catch(Exception e) {}
         return x;    // 받을 넓이 값을 리턴 시킴
      }
      
      public int ImageHeightValue(String file) {   // 이미지 높이 크기 값 계산
         
         int y = 0;
         try {
            File f = new File(file);
            BufferedImage bi = ImageIO.read(f);
            y = bi.getHeight();
         } catch(Exception e) {}
         return y;
      }
   }

class Missile {      // 미사일 위치 파악 및 이동을 위한 클래스 추가
   
   int x;
   int y;   // 편의상 변수명 변경
   
   Missile(int x, int y) {
      this.x = x;
      this.y = y;   // 편의상 변수명을 pos.n 에서 x, y 로 변경
   }
   
   public void move() {
      x += 10;
   }
   
/*   
   Point pos;   // 미사일 좌표 변수
   
   Missile(int x, int y) {   // 미사일 좌표를 입력받는 메소드
      pos = new Point(x, y);   // 미사일 좌표를 체크
   }
   
   public void move() {   // 미사일 이동을 위한 메소드
      pos.x += 10;         // x 좌표에 10만큼 미사일 이동
   }
*/   
}

class Enemy {
   int x;
   int y;
   
   Enemy(int x, int y) {
      this.x = x;
      this.y = y;
   }
   
   public void move() {      // x좌표 -3 만큼 이동 시키는 명령 메소드
      x -= 3;
   }
}