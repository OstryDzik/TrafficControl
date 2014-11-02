package Common;

import java.util.ArrayList;

/**
 * Created by Filip on 2014-11-01.
 */
public class CarsMockup
{
    ArrayList<Car> cars = null;

    public ArrayList<Car> getCars()
    {
        return cars;
    }

    public void setCars(ArrayList<Car> cars)
    {
        this.cars = cars;
    }

    public CarsMockup(ArrayList<Car> cars)
    {
        this.cars = cars;
    }

}
