package jugs.BigCorpus;

/**
 * Created by jugs on 12/14/16.
 */
//DataStructure that holds the number of Document that contains the specific word
public class PostingTable
{
    public String docName;
    public String term;
    public int noOfDocuments;


    public PostingTable(String term, int noOfDocuments)
    {
        this.term = term;
        this.noOfDocuments = noOfDocuments;
    }

    public boolean isPresent(String term)
    {
        if (this.term.equals(term))
            return true;
        else
            return false;
    }
}
