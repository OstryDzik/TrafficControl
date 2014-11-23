package Common;

import java.util.ArrayList;

/**
 * Created by Filip on 2014-11-01.
 */
public class CarsInfo
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

    public CarsInfo(ArrayList<Car> cars)
    {
        this.cars = cars;
    }

}
