
public class loading implements Runnable{

	@Override
	public void run() {
//		AlertBox.display("Check", "Wow");
		for (int i = 0; i < 50; i++) {
			System.out.println("Count is : " + i);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
