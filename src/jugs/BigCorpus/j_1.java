package jugs.BigCorpus;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by jugs on 12/15/16.
 */
public class j_1
{
    Lemmatizer lemmatizer = new Lemmatizer();
    Tokenize tn = new Tokenize();

    public static void main(String[] args) throws Exception
    {
        j_1 j1 = new j_1();
//        j1.run();
//        j1.read();
        j1.cleanDocument();
    }

    private void cleanDocument() throws  Exception
    {
        File file = new File("/home/jugs/Desktop/Dataset/ReviewJSON.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line ;
        while ((line = br.readLine()) != null)
        {
            System.out.println(line);
//            String data = line.replaceAll("(\\\\r\\\\n)|\\*\\W{3}|\\\\", " ");
//            writeToFile(data);
        }

    }

    private void writeToFile(String data) throws Exception
    {
        FileWriter fw = new FileWriter("/home/jugs/Desktop/Dataset/Reviewjson.txt", true);
        fw.write(data + '\n');
        fw.close();
    }

    public void read() throws Exception
    {
        File file = new File("/home/jugs/IdeaProjects/StanfordNLP_POS/tfidf.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int i=0;
        while ((line = br.readLine()) != null)
        {
            i++;
//            if (line.equals("{}"))
            System.out.println(line);
        }
        System.out.println(i);
    }


    public void run() throws Exception
    {
        File file = new File("/home/jugs/PycharmProjects/scrapeHTML/scraper/reviewDump.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null)
        {
            JSONParser parser = new JSONParser();
            Object object = parser.parse(line);
            JSONObject jsonObject = (JSONObject) object;
            String title = (String) jsonObject.get("title");
            String noisyReview = (String) jsonObject.get("review");
            String fname = (String) jsonObject.get("fname");
//            if (title.equals("L.A. Confidential (1997)"))
//            {
                List<String> alltokenList = lemmatizer.lemmas(noisyReview);
                Map<String,Double> termCount= tn.getTermCount(alltokenList);
                Map<String,Double> termFrequency = tn.getTermFrequency(termCount, Double.valueOf(alltokenList.size()));
                if (termFrequency.toString().equals("{}"))
                    System.out.println(title+", "+ fname);
//            }
        }
    }


}
