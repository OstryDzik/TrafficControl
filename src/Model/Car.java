package Model;

import java.awt.Color;
import java.util.Random;

/**
 * Created by Filip on 2014-11-01.
 */
public class Car
{
    private int x;
    private int y;
    private final Color color;
    private Direction direction;

    public Car(int x, int y) {
        Random rand = new Random();
        float r = rand.nextFloat() * 0.5f + 0.5f;
        float g = rand.nextFloat() * 0.5f + 0.5f;
        float b = rand.nextFloat() * 0.5f + 0.5f;
        this.color = new Color(r, g, b);
        this.x = x;
        this.y = y;
        this.direction = Direction.UP;
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
    
    public Direction getDirection()
    {
        return direction;
    }
    
    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }
}
