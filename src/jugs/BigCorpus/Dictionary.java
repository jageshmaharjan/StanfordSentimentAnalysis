package jugs.BigCorpus;

import java.util.List;

/**
 * Created by jugs on 12/13/16.
 */
public class Dictionary
{
    public String documentname;
    List<TermFrequency> termFrequencyList;

    public Dictionary(String documentname, List<TermFrequency> termFrequencyList)
    {
        this.documentname = documentname;
        this.termFrequencyList = termFrequencyList;
    }

    public String getDocumentname()
    {
        return documentname;
    }

    public void setDocumentname(String documentname)
    {
        this.documentname = documentname;
    }

    public List<TermFrequency> getTermFrequencyList()
    {
        return termFrequencyList;
    }

    public void setTermFrequencyList(List<TermFrequency> termFrequencyList)
    {
        this.termFrequencyList = termFrequencyList;
    }
}
