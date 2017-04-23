package cool.auto.imageupload;

import java.awt.AWTException;

public class App3
{
	public static void main(String[] args) throws AWTException, InterruptedException
	{
		Keyboard keyboard = new Keyboard();
		Thread.sleep(2000);
		
		//Robot robot = new Robot();
		//robot.mouseMove(1064, 584);
		//robot.mousePress( InputEvent.BUTTON1_MASK );
	    //robot.mouseRelease( InputEvent.BUTTON1_MASK );

	    //Thread.sleep(2000);
	    keyboard.enter();
	    Thread.sleep(200);
	    keyboard.shift_tab();
	    Thread.sleep(200);
	    keyboard.ctrl_a();
	    Thread.sleep(200);
	    //keyboard.alt_o();
	    
	    //Thread.sleep(5000);
	    
		
		/*String str = "7.026106598553958";
		//System.out.println(Integer.parseInt(str));
		System.out.println(Float.parseFloat(str));
		System.out.println(Double.parseDouble(str));*/
	}
}
