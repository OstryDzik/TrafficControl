package Model;

import java.io.Serializable;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class Interval implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2954104352966374374L;

	public Interval(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public Interval()
    {
        this.min = 1;
        this.max = 1;
    }

    private int min;
    private int max;

    public int getMax()
    {
        return max;
    }

    public int getMin()
    {

        return min;
    }
}
