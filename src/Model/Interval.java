package Model;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class Interval {

    public Interval(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public Interval()
    {
        this.min = 10;
        this.max = 20;
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
