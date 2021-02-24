package java_0819;

class MyThread_3 extends Thread
{
	
	public MyThread_3(String name) {
		
		super(name);
	}
	
	@Override
	public void run() {
		try {
			for(int i = 0; i <5; i++) {
				Thread.sleep(1000);  //반드시 예외처리 시켜주기
				System.out.println(" " + i + "번 " +getName()+ " : 스레드");
																 //getName 은 스레드에 있는 method 이다.
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class ThreadTest_3 {
	public static void main(String[] args) {
		
		MyThread_3 obj_1 = new MyThread_3("첫 번째		");
		MyThread_3 obj_2 = new MyThread_3("두 두 번째		");
		MyThread_3 obj_3 = new MyThread_3("세 세 세 번째	");
		MyThread_3 obj_4 = new MyThread_3("네 네 네 네 번째	");
		
		obj_1.start();
		obj_2.start();
		obj_3.start();
		obj_4.start();
		
	}

}
