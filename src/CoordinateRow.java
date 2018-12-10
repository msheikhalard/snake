public class CoordinateRow {
	 
    Coordinate[] snake = new Coordinate[768];
    int snakeStartingLength = 2;
   
        CoordinateRow(){
           
            snake[0] = new Coordinate();
            snake[1] = new Coordinate();
           
            snake[0].x = 1;
            snake[0].y = 0;
           
            snake[1].x = 0;
            snake[1].y = 0;
        }
}