import ui.SnakeUserInterface;
import ui.UserInterfaceFactory;
public class Snake {
	
	// Name : Malek Sheikh Alrd
	// Assignment : Snake
	// Date : 26/11/2017

	public SnakeUserInterface ui;

	static final int FIELD_WIDTH = 32, FIELD_HEIGHT = 24;
	final static int WIDTH = 10,
            			HEIGHT = 10;
	static final double FramePerSecond = 8.5;


	CoordinateRow row = new CoordinateRow();


	Snake() {

		ui = UserInterfaceFactory.getSnakeUI(FIELD_WIDTH, FIELD_HEIGHT);
		ui.setFramesPerSecond(FramePerSecond);

	}
	

	public static void main(String[] args) {

		new Game().start();
		

	}

}