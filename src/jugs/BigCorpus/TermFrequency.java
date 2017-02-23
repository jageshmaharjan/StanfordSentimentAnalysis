package jugs.BigCorpus;

/**
 * Created by jugs on 12/13/16.
 */
public class TermFrequency
{
    public String term;
    public Double frequency;
    public Double tf;

    public TermFrequency(String term, Double frequency, Double tf)
    {
        this.term = term;
        this.frequency = frequency;
        this.tf = tf;
    }

    public String getTerm()
    {
        return term;
    }

    public void setTerm(String term)
    {
        this.term = term;
    }

    public Double getFrequency()
    {
        return frequency;
    }

    public void setFrequency(Double frequency)
    {
        this.frequency = frequency;
    }

    public Double getTf()
    {
        return tf;
    }

    public void setTf(Double tf)
    {
        this.tf = tf;
    }
}
