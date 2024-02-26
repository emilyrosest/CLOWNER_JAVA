public class Frog {
    
    //Private values of the Frog class
    private double width;
    private double height;
    private double Xpos;
    private double Ypos;
    private int moveNumber;

    //Frog constructor
    public Frog(double screenWidth) {
        this.width = 23;
        this.height = 18;
        this.Xpos = screenWidth/2;
        this.Ypos = 9;
        moveNumber = 10;
    }

    public void update() {
        handleMove();
        display();
    }

    //Getters
    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getXpos() {
        return Xpos;
    }

    public double getYpos() {
        return Ypos;
    }

    public void display() {
        StdDraw.picture(Xpos, Ypos, "sprites/clown.png");
    }

    public void handleMove() {
        double mouseX = StdDraw.mouseX(); //we get the X position of the mouse
        double mouseY = StdDraw.mouseY(); //we get the Y position of the mouse

        //We move the X and Y position of the frog if the gamer press the buttons
        if (StdDraw.isKeyPressed('Z') || StdDraw.isKeyPressed('W')) {
            Ypos += moveNumber;
        }
        if (StdDraw.isKeyPressed('Q') || StdDraw.isKeyPressed('A')) {
            Xpos -= moveNumber;
        }
        if (StdDraw.isKeyPressed('S')) {
            Ypos -= moveNumber;
        }
        if (StdDraw.isKeyPressed('D')) {
            Xpos += moveNumber;
        }
        //We move the X and Y position of the frog if the gamer press the mouse
        if (StdDraw.isMousePressed()) {
            //We check the area where the gamer clicked to see if it's more to the left, right, top, or bottom
            if (mouseY > Ypos && Math.abs(mouseX - Xpos) <= Math.abs(mouseY - Ypos)) { 
                Ypos += moveNumber;
                return;
            }
            if (mouseX < Xpos && Math.abs(mouseY - Ypos) <= Math.abs(mouseX - Xpos)) {
                Xpos -= moveNumber;
                return;
            }
            if (mouseY < Ypos && Math.abs(mouseX - Xpos) <= Math.abs(mouseY - Ypos)) {
                Ypos -= moveNumber;
                return;
            }
            if (mouseX > Xpos && Math.abs(mouseY - Ypos) <= Math.abs(mouseX - Xpos)) {
                Xpos += moveNumber;
                return;
            }
        }
    }
}
