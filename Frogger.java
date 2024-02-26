public class Frogger {

    //Public values that depend on the sprites and audio file
    static long musicLength = 53000; //music length is in milliseconds
    static double buttonWidth = 199; //start button size
    static double buttonHeight = 97;

    //Private values of the Frogger class
    //Screen values
    private int WIDTH;
    private int HEIGHT;
    private double buttonXpos;
    private double buttonYpos;
    //Game values
    private boolean running; //this boolean is used for the game loop
    private String state; //used to know which screen we display
    private Game game; //A Game object
    private long time; //time is used to know when we have to play the music again


    //Frogger constructor
    public Frogger() {
        this.WIDTH = 525;
        this.HEIGHT = 700;
        this.buttonXpos = WIDTH/2;
        this.buttonYpos = 200;
        this.running = true;
        this.state = "START";
        this.time = System.currentTimeMillis();
    }

    public void start() {
        //We set the screen
        StdDraw.setCanvasSize(WIDTH, HEIGHT);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.enableDoubleBuffering();

        //We play the music
        StdAudio.playInBackground("audio/music.wav");

        loop();
    }

    //Main loop of the game
    //Handles the game graphic and music
    public void loop() {
        while (running) {
            StdDraw.clear();

            displayFrogger();
            handleMusic();

            StdDraw.show(); //make the drawn elements visible
            StdDraw.pause(20); //pause for 20ms before rendering next image
        }
    }

    public void displayFrogger() {
        switch (state) {
            //For each state, we display a screen and a button
            //For the game state, we handle the progress and update the game object
            case "START":
                StdDraw.picture(WIDTH/2, HEIGHT/2, "sprites/startscreen.png");
                StdDraw.picture(buttonXpos, buttonYpos, "sprites/startbutton.png");
                handleMouse();
                break;
            case "GAME":
                if (game.getLose()) {
                    state = "FAIL";
                }
                if (game.getWin()) {
                    state = "WIN";
                }
                game.update();
                break;
            case "WIN":
                StdDraw.picture(WIDTH/2, HEIGHT/2, "sprites/bravoscreen.png");
                StdDraw.picture(buttonXpos, buttonYpos, "sprites/startbutton.png");
                handleMouse();
                break;
            case "FAIL":
                StdDraw.picture(WIDTH/2, HEIGHT/2, "sprites/gameoverscreen.png");
                StdDraw.picture(buttonXpos, buttonYpos, "sprites/startbutton.png");
                handleMouse();
                break;
            default:
                break;
        }
    }

    public void handleMouse() {
        double mouseX = StdDraw.mouseX(); //we get the X position of the mouse
        double mouseY = StdDraw.mouseY(); //we get the Y position of the mouse

        double borderLeft = buttonXpos - buttonWidth;
        double borderRight = buttonXpos + buttonWidth;
        double borderTop = buttonYpos + buttonHeight;
        double borderBottom = buttonYpos - buttonHeight;

        //We check if the mouse is inside of the button
        if (mouseX > borderLeft && mouseX < borderRight && mouseY > borderBottom && mouseY < borderTop) {
            StdDraw.picture(buttonXpos, buttonYpos, "sprites/startbutton2.png");

            //We check if the mouse is pressed
            if (StdDraw.isMousePressed()) {
                StdDraw.picture(buttonXpos, buttonYpos, "sprites/startbutton3.png");

                //We create a new game object and start entering the game
                game = new Game(WIDTH, HEIGHT);
                state = "GAME";
                StdDraw.pause(200); //We pause so the frog doesn't move because of the already pressed mouse
            }
        }        
    }

    public void handleMusic() {
        //Check the difference between the current time and the instanciated time
        //If bigger than the length of the music, the music is finished and we have to play it again
        if (System.currentTimeMillis() - time >= musicLength) {
            StdAudio.playInBackground("audio/music.wav");
            time = System.currentTimeMillis();
        }
    }
}
