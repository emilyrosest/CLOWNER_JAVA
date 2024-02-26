public class Car {
    
    //Private values of the Car class
    private double width;
    private double height;
    private double Xpos;
    private double Ypos;
    private String picturePath;

    //Car constructor
    public Car(double width, double height, double Xpos, double Ypos, String picturePath) {
        this.width = width;
        this.height = height;
        this.Xpos = Xpos + width/2;
        this.Ypos = Ypos;
        this.picturePath = picturePath;
    }

    //Car copy constructor with changing X position
    public Car(Car car, double Xpos) {
        this.width = car.width;
        this.height = car.height;
        this.Xpos = Xpos + width/2;
        this.Ypos = car.Ypos;
        this.picturePath = car.picturePath;
    }

    public void display() {
        StdDraw.picture(Xpos, Ypos, picturePath);
    }

    //Getters and Setter
    public double getWidth() {
        return width;
    }

    public double getXpos() {
        return Xpos;
    }

    public void setXpos(double newXpos) {
        Xpos = newXpos;
    }

    public void moveCar(double speed, int direction) {
        display();
        Xpos -= speed * direction; //X position is incremented or decremented based on the direction of the lane
    }

    public boolean Collision(Frog frog) {
        double frogXpos = frog.getXpos();
        double frogYpos = frog.getYpos();
        double frogHalfWidth = frog.getWidth()/2;
        double frogHalfHeight = frog.getHeight()/2;
        
        //We check if the frog is inside of the border of the car
        if (frogXpos + frogHalfWidth >= Xpos - width/2
         && frogXpos - frogHalfWidth <= Xpos + width/2
         && frogYpos + frogHalfHeight >= Ypos - height/2
         && frogYpos - frogHalfHeight <= Ypos + height/2) {
            return true;
         }
        return false;
    }
}
