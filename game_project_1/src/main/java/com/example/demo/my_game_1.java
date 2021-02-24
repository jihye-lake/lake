package com.example.demo;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class my_game_1 {
	public static void main(String[] arge) {
		
		my_game_1_sub frm = new my_game_1_sub();
	}
}

	class my_game_1_sub extends JFrame {
		
		int frm_width = 800;
		int frm_height = 600;
		
		my_game_1_sub() {
			init();		// 프레임에 들어갈 컴포넌트 세팅 메소드
			start();	// 기본적인 시작 명령 처리 부분
			
			setTitle("비행기 게임");
			setSize(frm_width, frm_height);	// 프레임 크기 가져오기
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			// 프레임이 윈도우에 표시될 수 있도록 위치를 잡아주기 위해서 현재 모니터의 해상도 값을 불러옴
			
			int frm_xpos = (int)(screen.getWidth() / 2 - frm_width / 2);
			int frm_ypos = (int)(screen.getHeight() / 2 - frm_height / 2);
			
			setLocation(frm_xpos, frm_ypos);
			setResizable(false);
			setVisible(true);
		}
		
		public void init() {
			// TODO Auto-generated method stub
			
		}

		public void start() {
			// TODO Auto-generated method stub
			
		}
	}

