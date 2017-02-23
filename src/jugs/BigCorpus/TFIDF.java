package jugs.BigCorpus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jugs on 12/15/16.
 */
//After running the "jugs.BigCorpus.Tokenize.java" file it generates idf and tf, this class will the tf-idf from the generated set of file
public class TFIDF
{
    public static void main(String[] args) throws Exception
    {
        TFIDF obj = new TFIDF();
        obj.readFile();
    }

    private void readFile() throws Exception
    {
        File file = new File("/home/jugs/IdeaProjects/MovieReviewProcessing/termfrequency.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        Map<String, Double> idf = getIDF();
        while ((line = br.readLine()) != null)
        {
            if (!line.equals("{}"))
            {
                Map<String, Double> tfidf = new HashMap<>();
                String[] str = line.substring(1, line.length() - 1).split(", ");
                for (int i = 0; i < str.length; i++)
                {
                    String[] kvPair = str[i].split("=");
                    Double score = Double.valueOf((kvPair[kvPair.length-1])) * getTFIDFScore(kvPair[0],idf);
                    tfidf.put(kvPair[0], score);
                }
                writeToFileTFIDF(tfidf);
            }
        }
    }

    private void writeToFileTFIDF(Map<String, Double> tfidf) throws Exception
    {
        FileWriter fileWriter = new FileWriter("tfidf.txt",true);
        fileWriter.write(tfidf.toString() + '\n');
        fileWriter.close();
    }

    private Double getTFIDFScore(String token, Map<String, Double> idf)
    {
        if (idf.get(token) != null)
            return idf.get(token);
        else
            return 0.0;
    }

    public Map<String,Double> getIDF() throws Exception
    {
        File file = new File("/home/jugs/IdeaProjects/MovieReviewProcessing/idf.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        Map<String, Double> idf = new HashMap<>();
        while ((line = br.readLine()) != null)
        {
            String[] str = line.substring(1,line.length()-1).split(", ");
            for (int i=0;i<str.length; i++)
            {
                String[] kvPair = str[i].split("=");
                if (!kvPair[0].equals(""))
                    idf.put(kvPair[0], Double.valueOf(kvPair[1]));
            }
        }
        return idf;
    }

}
