package jugs.BigCorpus;

/**
 * Created by jugs on 12/15/16.
 */
public class Tools
{
    //For the rounding purpose
    public static double round(double value, int places)
    {
        if (places < 0)
            throw new IllegalArgumentException();
        long factor = (long) Math.pow(10,places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp/factor;
    }
}
