package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Filip on 2014-11-01.
 */
public class CarsInfo implements Serializable
{
    /** */
    private static final long serialVersionUID = -4234002272974425338L;

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public CarsInfo(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public void addCar(Car car) {
        this.cars.add(car);
    }

    private ArrayList<Car> cars = null;
}
