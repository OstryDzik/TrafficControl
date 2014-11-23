package Model;

import java.awt.*;
import java.util.Random;

/**
 * Created by Filip on 2014-11-01.
 */
public class Car
{
    private int x;
    private int y;
    private final Color color;

    public Car(int x, int y) {
        Random rand = new Random();
        float r = rand.nextFloat() + 0.5f;
        float g = rand.nextFloat() + 0.5f;
        float b = rand.nextFloat() + 0.5f;
        this.color = new Color(r, g, b);
        this.x = x;
        this.y = y;
    }
    public Car() {
        this(0, 0);
    }

    public Color getColor()
    {
        return color;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }
}
