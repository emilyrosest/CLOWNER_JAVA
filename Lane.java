import java.util.ArrayList;
import java.util.List;

public class Lane {
    
    //Private values of the Lane class
    private double width;
    private double speed;
    private double spacing;
    private int direction;
    private Car firstCar; //this value is the template of the car

    private List<Car> cars;

    //Lane constructor
    public Lane(double screenWidth, double speed, double spacing, int direction, Car firstCar) {
        this.width = screenWidth * 2;
        this.speed = speed;
        this.spacing = spacing;
        this.direction = direction;
        this.firstCar = firstCar;
        this.cars = new ArrayList<>();

        fillLane();
    }

    //Getter for the Xpos
    public double getXpos() {
        return width/4;
    }

    public void fillLane() {
        //We create a few cars depending on the position of the first car and their width
        double carPos = firstCar.getXpos();
        
        //We add cars until a certain limit
        while(carPos <= width/2 + width) {
            Car car = new Car(firstCar, carPos); //We create a new car based on firstCar but with another X position
            cars.add(car);
            carPos += firstCar.getWidth() + spacing;
        }
    }

    public void update() {
        for (int i = 0; i < cars.size(); i++) {
            if (direction == 1) {
                //If a car cross the left limit, it gets translated to the right of the last car of the road
                if (i == 0 && cars.get(i).getXpos() <= getXpos() - width/2) {
                    //We separate the case of the first car because its previous car is the last one
                    cars.get(i).setXpos(cars.get(cars.size()-1).getXpos() + cars.get(i).getWidth() + spacing);
                }
                if (i != 0 && cars.get(i).getXpos() <= getXpos() - width/2) {
                    cars.get(i).setXpos(cars.get(i - 1).getXpos() + cars.get(i).getWidth() + spacing);
                }
            }

            else {
                //Same thing but for the right direction
                if (i == cars.size()-1 && cars.get(i).getXpos() >= getXpos() + width/2) {
                    cars.get(i).setXpos(cars.get(0).getXpos() - cars.get(i).getWidth() - spacing);
                }
                if (i != cars.size()-1 && cars.get(i).getXpos() >= getXpos() + width/2) {
                    cars.get(i).setXpos(cars.get(i + 1).getXpos() - cars.get(i).getWidth() - spacing);
                }
            }
            
            cars.get(i).moveCar(speed, direction);
        }
    }

    public boolean handleCollision(Frog frog) {
        //We check each car of the lane to see if its in collision with the frog
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).Collision(frog)) {
                return false;
            }            
        }
        return true;
    }
}
