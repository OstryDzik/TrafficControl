package Model;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.Random;


/**
 * Created by Filip on 2014-11-01.
 */
public class Car implements Serializable
{
    /** */
    private static final long serialVersionUID = -878205146976541058L;
    private int x;
    private int y;
    private final Color color;
    private Direction direction;
    private Direction newDirection;
    private boolean directionChosen;
    private int countTime;

    public Car(int x, int y) {
        Random rand = new Random();
        float r = rand.nextFloat() * 0.5f + 0.5f;
        float g = rand.nextFloat() * 0.5f + 0.5f;
        float b = rand.nextFloat() * 0.5f + 0.5f;
        this.color = new Color(r, g, b);
        this.x = x;
        this.y = y;
        this.direction = Direction.UP;
        this.directionChosen = false;
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
    
    public Direction getNewDirection()
    {
        return newDirection;
    }
    
    public void setNewDirection()
    {
        this.newDirection = Direction.UP;
        
        //skrety w prawo to juz na pierwszym wezle skrec
        if(direction == Direction.UP && newDirection == Direction.RIGHT)
        {
            countTime = 1;
        }
        else if(direction == Direction.DOWN && newDirection == Direction.LEFT)
        {
            countTime = 1;
        }
        else if(direction == Direction.LEFT && newDirection == Direction.UP)
        {
            countTime = 1;
        }
        else if(direction == Direction.RIGHT && newDirection == Direction.DOWN)
        {
            countTime = 1;
        }
        else 
        { //
            countTime = 2;
        }
    }
    
    public boolean isDirectionChosen()
    {
        return directionChosen;
    }
    
    public void setDirectionChosen(boolean directionChosen)
    {
        this.directionChosen = directionChosen;
    }
    
    public int getCountTime()
    {
        return countTime;
    }
    
    public void decrementCountTime()
    {
        countTime--;
    }
    
    public void setPoint(Point p)
    {
        this.x = p.x;
        this.y = p.y;
    }
    
    public void changeDirection()
    {
        direction = newDirection;
    }
}
