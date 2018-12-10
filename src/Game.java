import ui.Event;
import ui.SnakeUserInterface;
import ui.UIAuxiliaryMethods;
import ui.UserInterfaceFactory;

public class Game {

	SnakeUserInterface ui;

	static final int FIELD_WIDTH = 32, FIELD_HEIGHT = 24;
	static final double FramePerSecond = 6.5;

	char direction = 'R';

	int XApple;
	int YApple;

	boolean playSnake = true;

	CoordinateRow row = new CoordinateRow();
	Snake create = new Snake();

	Game() {

		ui = UserInterfaceFactory.getSnakeUI(FIELD_WIDTH, FIELD_HEIGHT);
		ui.setFramesPerSecond(FramePerSecond);

	}

	void startingPosition() {

		ui.place(row.snake[0].x, row.snake[0].y, SnakeUserInterface.SNAKE);
		ui.place(row.snake[1].x, row.snake[1].y, SnakeUserInterface.SNAKE);

		ui.showChanges();
	}
	
	void startSnake() {

		boolean checkSnake = false;

		int tailX = row.snake[row.snakeStartingLength - 1].x;
		int tailY = row.snake[row.snakeStartingLength - 1].y;

		if (!appleIsEaten()) {

			checkSnake = false;
			ui.place(tailX, tailY, SnakeUserInterface.EMPTY);

		} else {
			checkSnake = true;
			row.snake[row.snakeStartingLength] = new Coordinate();

			newApple();
		}

		if (direction == 'R' && row.snake[1].x != row.snake[0].x + 1) {

			for (int i = row.snakeStartingLength - 1; i > 0; i--) {

				row.snake[i].x = row.snake[i - 1].x;
				row.snake[i].y = row.snake[i - 1].y;
			}

			row.snake[0].x += 1;

			if (row.snake[0].x > 31) {
				row.snake[0].x = 0;
			}

			ui.place(row.snake[0].x, row.snake[0].y, SnakeUserInterface.SNAKE);

			ui.showChanges();

		} else if (direction == 'L' && row.snake[1].x != row.snake[0].x - 1) {

			for (int i = row.snakeStartingLength - 1; i > 0; i--) {

				row.snake[i].x = row.snake[i - 1].x;
				row.snake[i].y = row.snake[i - 1].y;
			}

			row.snake[0].x -= 1;

			if (row.snake[0].x < 0) {
				row.snake[0].x = 31;
			}

			ui.place(row.snake[0].x, row.snake[0].y, SnakeUserInterface.SNAKE);

			ui.showChanges();

		} else if (direction == 'U' && row.snake[1].y != row.snake[0].y - 1) {

			for (int i = row.snakeStartingLength - 1; i > 0; i--) {

				row.snake[i].x = row.snake[i - 1].x;
				row.snake[i].y = row.snake[i - 1].y;
			}

			row.snake[0].y -= 1;

			if (row.snake[0].y < 0) {
				row.snake[0].y = 23;
			}

			ui.place(row.snake[0].x, row.snake[0].y, SnakeUserInterface.SNAKE);

			ui.showChanges();

		} else if (direction == 'D' && row.snake[1].y != row.snake[0].y + 1) {

			for (int i = row.snakeStartingLength - 1; i > 0; i--) {

				row.snake[i].x = row.snake[i - 1].x;
				row.snake[i].y = row.snake[i - 1].y;
			}

			row.snake[0].y += 1;

			if (row.snake[0].y > 23) {
				row.snake[0].y = 0;
			}

			ui.place(row.snake[0].x, row.snake[0].y, SnakeUserInterface.SNAKE);

			ui.showChanges();

		}

		if (gameOver()) {
			playSnake = false;
		}

		if (checkSnake) {
			row.snake[row.snakeStartingLength].x = tailX;
			row.snake[row.snakeStartingLength].y = tailY;

			row.snakeStartingLength++;

		}
	}

	void speedUpLevel() {
		if (row.snakeStartingLength == 5) {
			ui.setFramesPerSecond(FramePerSecond + 10);

			ui.showChanges();
		}

		if (row.snakeStartingLength == 10) {
			ui.setFramesPerSecond(FramePerSecond + 20);

			ui.showChanges();
		}
	}

	void processChangeDirection(String newDirection, int directionChangeCoordinateX, int directionChangeCoordinateY) {
		direction = newDirection.charAt(0);
	}

	 boolean gameOver() {
		for (int i = 1; i < row.snakeStartingLength; i++) {
			if (row.snake[0].x == row.snake[i].x && row.snake[0].y == row.snake[i].y) {

				ui.printf("OUCH!\nGAME OVER!\nYou lose!\nYou scored %d points!", row.snakeStartingLength - 2);
				return true;
			}
		}
		return false;
	}

	void newApple() {

		boolean AppleInSnake = true;

		while (AppleInSnake) {
			AppleInSnake = false;

			for (int i = 0; i < row.snakeStartingLength; i++) {

				XApple = (int) (UIAuxiliaryMethods.getRandom(0, 32));
				YApple = (int) (UIAuxiliaryMethods.getRandom(0, 24));

				if (row.snake[i].x == XApple && row.snake[i].y == YApple) {
					AppleInSnake = false;
				}
			}
		}

		ui.place(XApple, YApple, SnakeUserInterface.FOOD);

	}

	boolean appleIsEaten() {

		if (row.snake[0].x == XApple && row.snake[0].y == YApple) {
			
			ui.printf("Yum! %d points!\n", row.snakeStartingLength - 1);
			return true;
		}
		return false;
	}

	void processEvent(Event event) {

		if (event.name.equals("alarm") && event.data.equals("refresh") && playSnake == true) {

			startSnake();
			speedUpLevel();

		} else if (event.name.equals("arrow")) {

			processChangeDirection(event.data, row.snake[0].x, row.snake[0].y);

		}
	}

	void start() {

		startingPosition();
		
		newApple();

		while (true) {
			Event event = ui.getEvent();
			processEvent(event);
		} 
	}

}
