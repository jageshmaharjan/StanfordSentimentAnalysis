package jugs.BigCorpus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by jugs on 12/15/16.
 */
public class CosSimillarity
{
    public static void main(String[] args) throws Exception
    {
        CosSimillarity cs = new CosSimillarity();
        cs.readFile();
    }

    private void readFile() throws Exception
    {
        File file = new File("/home/jugs/IdeaProjects/MovieReviewProcessing/tfidf.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null)
        {
            System.out.println(line);
        }
    }
}
