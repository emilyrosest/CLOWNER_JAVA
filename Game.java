import java.util.ArrayList;
import java.util.List;
import java.awt.Font;

public class Game {

    //Public values that depend on the sprites
    static double floor1Height = 30; //heights of the lanes
    static double floor2Height = 131;
    static double floor3Height = 26;
    static double floor4Height = 131;
    static double floor5Height = 27;
    static double floor6Height = 175;
    static double floor7Height = 87;
    static double floor8Height = 44;
    static double margin = 8; //height of the margin between lanes
    static double goalHeight = 64; //size of the goal
    static double goalWidth = 220;
    
    //Private values of the Game class
    private List<Lane> lanes;
    private Frog frog;
    private int lives;
    private boolean lose; //lose and win are used to update the screen in the Frogger class
    private boolean win;
    private int width;
    private int height;

    //Game constructor
    public Game(int width, int height) {
        this.lanes = new ArrayList<>();
        this.frog = new Frog(width);
        this.lives = 3;
        this.lose = false;
        this.win = false;
        this.width = width;
        this.height = height;
        
        fillGame();
    }

    public void fillGame() {
        //We create a few cars, with the Car class
        Car car1 = new Car(30, 30, width/5, 59, "sprites/star.png");
        Car car2 = new Car(33, 46, width/5, 145, "sprites/human.png");
        Car car3 = new Car(60, 30, width/5, 226, "sprites/elephant.png");
        Car car4 = new Car(64, 28, width/5, 318, "sprites/car.png");
        Car car5 = new Car(234, 60, width/5, 610, "sprites/fire.png");
        Car car6 = new Car(40, 35, width/5, 430, "sprites/snail.png");
        Car car7 = new Car(40, 35, width/5, 472, "sprites/snail.png");
        Car car8 = new Car(40, 35, width/5, 515, "sprites/snail.png");
        
        //We fill the lanes list with a serie of lanes, using the Lane class
        //We use random speeds

        double speed1 = random(2, 4);
        double speed2 = random(3, 5);
        double speed3 = random(3, 6);
        double speed4 = random(4, 7);
        double speed5 = random(6, 7.5);
        double bigSpeed = random(25, 35);

        Lane lane1 = new Lane(width, speed1, 90, -1, car1);
        Lane lane2 = new Lane(width, speed2, 80, 1, car2);
        Lane lane3 = new Lane(width, speed3, 85, -1, car3);
        Lane lane4 = new Lane(width, speed4, 75, 1, car4);
        Lane lane5 = new Lane(width, bigSpeed, 500, 1, car5);
        Lane lane6 = new Lane(width, speed5, 160, -1, car6);
        Lane lane7 = new Lane(width, speed5, 160, -1, car7);
        Lane lane8 = new Lane(width, speed5, 160, -1, car8);
        lanes.add(lane1);
        lanes.add(lane2);
        lanes.add(lane3);
        lanes.add(lane4);
        lanes.add(lane5);
        lanes.add(lane6);
        lanes.add(lane7);
        lanes.add(lane8);
    }

    public void updateLives() {
        if (lives <= 0) {
            lose = true;
        }
        //For each lanes, we check if the frog is in collision with one of their cars
        //If yes, we play a loosing audio and decrement the number of lives
        for (int i = 0; i < lanes.size(); i++) {
            if (!lanes.get(i).handleCollision(frog)) {
                StdAudio.playInBackground("audio/loose.wav");
                --lives;
                frog = new Frog(width);
            }
        }
    }

    //Getters
    public boolean getLose() {
        return lose;
    }

    public boolean getWin() {
        return win;
    }

    public void displayLives() {
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        StdDraw.text(50, 650, Integer.toString(lives));
    }

    public void drawGame() {
        //Black background
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(width/2, height/2, width/2, height/2);

        //We draw each part of the game's floor and use a currentHeight variable to know at what height we are
        double currentHeight = 0;
        StdDraw.picture(width/2, floor1Height/2, "sprites/floor1.png", width, floor1Height);
        currentHeight += floor1Height + margin;
        StdDraw.picture(width/2, currentHeight + floor2Height/2, "sprites/floor2.png", width, floor2Height);
        currentHeight += floor2Height + margin;
        StdDraw.picture(width/2, currentHeight + floor3Height/2, "sprites/floor3.png", width, floor3Height);
        currentHeight += floor3Height + margin;
        StdDraw.picture(width/2, currentHeight + floor4Height/2, "sprites/floor4.png", width, floor4Height);
        currentHeight += floor4Height + margin;
        StdDraw.picture(width/2, currentHeight + floor5Height/2, "sprites/floor5.png", width, floor5Height);
        currentHeight += floor5Height + margin;
        StdDraw.picture(width/2, currentHeight + floor6Height/2, "sprites/floor6.png", width, floor6Height);
        currentHeight += floor6Height + margin;
        StdDraw.picture(width/2, currentHeight + floor7Height/2, "sprites/floor7.png", width, floor7Height);
        currentHeight += floor7Height + margin;
        StdDraw.picture(width/2, currentHeight + floor8Height/2, "sprites/floor8.png", width, floor8Height);
    }

    public void displayGoal() {     
        StdDraw.picture(width/2, height - goalHeight/2, "sprites/circus.png");
    }

    public void handleGoal() {
        double frogXpos = frog.getXpos();
        double frogYpos = frog.getYpos();
        double frogHalfWidth = frog.getWidth()/2;
        double frogHalfHeight = frog.getHeight()/2;
        
        //We check if the frog is inside of the goal area
        if (frogXpos + frogHalfWidth >= width/2 - goalWidth/2
         && frogXpos - frogHalfWidth <= width/2 + goalWidth/2
         && frogYpos + frogHalfHeight >= height - goalHeight
         && frogYpos - frogHalfHeight <= height + goalHeight) {
            StdAudio.playInBackground("audio/win.wav");
            win = true;
        }
    }

    //Update function called in the main loop
    public void update() {
        drawGame();
        for (int i = 0; i < lanes.size(); i++) {
            lanes.get(i).update(); //update the lanes and moving cars on them
        }
        frog.update();
        handleGoal();
        displayGoal();
        updateLives();
        displayLives();
    }

    //Random double function
    public static double random(double min, double max) {
        return (double) ((Math.random() * (max - min)) + min);
    }
}
