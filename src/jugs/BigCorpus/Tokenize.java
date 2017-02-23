package jugs.BigCorpus;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jugs on 12/13/16.
 */
public class Tokenize implements CONSTANTS
{
    public static Pattern pattern = Pattern.compile("(<.*?>)|\\(\\*?\\)|[^A-Za-z0-9_ \\.,\"-]|-{2,}|\\.{2,}");
    Lemmatizer lemmatizer = new Lemmatizer();
    List<Dictionary> dictionaryList = new ArrayList<>();
    int N =0;
    Map<String, Double> idf = new HashMap<>();

    public static void main(String[] args) throws Exception
    {
        Tokenize tz = new Tokenize();
        tz.readFile();
    }


    public void readFile() throws Exception
    {
        File file = new File(FILE);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null)
        {
            getTokens(line);
            N++;
        }
        finalizeIDF(idf);
        saveToFileIDF();
        System.out.println();
    }

    private void saveToFileIDF()
    {
        try
        {
            FileWriter fileWriter = new FileWriter("idf.txt",true);
            fileWriter.write(idf.toString());
            fileWriter.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void finalizeIDF(Map<String, Double> idf)
    {
        for (Map.Entry entry : idf.entrySet())
        {
            Double entryValue = (Double) entry.getValue();
            Double logvalue = Math.log10(N/entryValue);
            idf.put((String) entry.getKey(), Tools.round(logvalue,8));
        }
    }

    private void getTokens(String line) throws ParseException
    {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(line);
        JSONObject jsonObject = (JSONObject) object;
        String fname = (String) jsonObject.get("fname");
        String title = (String) jsonObject.get("title");
        String noisyReview = (String) jsonObject.get("review");
        String cleanReview = getCleanReview(noisyReview);             //This is to clean the given string using the regular expression
        String review_POS= getPOSTagging(noisyReview);                  //This get's the part of Speech (POS) of the given sentence using StanfordCoreNLP tool
        List<String> alltokenList = lemmatizer.lemmas(cleanReview);      //Outputs the token from the given set of string using StanfordCoreNLP tool
//        List<String> tokens = getNounsOnly(alltokenList);               //Output the tokens only with the POS having POS as Noun form


        Map<String,Double> termCount= getTermCount(alltokenList);
        Map<String,Double> termFrequency = getTermFrequency(termCount, Double.valueOf(alltokenList.size()));

        writeToFile(termFrequency);
        createIDF(termCount);


//        Double size = Double.valueOf(termCount.size());
//        List<jugs.BigCorpus.TermFrequency> termFrequencyList = new ArrayList<>();
//        for (Map.Entry entry : termCount.entrySet())
//        {
//
//            Double value = (Double) entry.getValue();
//            Double tf = value/ size;
//            termFrequencyList.add(new jugs.BigCorpus.TermFrequency((String) entry.getKey(),(Double) entry.getValue(), tf));
//        }
//
//        dictionaryList.add(new jugs.BigCorpus.Dictionary(title, termFrequencyList));
//        System.out.println(termCount.toString());

    }

    private void createIDF(Map<String, Double> termCount)
    {
        for (Map.Entry entry : termCount.entrySet())
        {
            Double count = idf.get(entry.getKey());
            if (count == null)
                idf.put((String) entry.getKey(), 1.0);
            else
                idf.put((String) entry.getKey(), (count+1));

        }
    }

    private void writeToFile(Map<String, Double> termFrequency)
    {
        try
        {
            if (termFrequency.size() >1)
            {
                FileWriter fileWriter = new FileWriter("termfrequency.txt",true);
                fileWriter.write(termFrequency.toString() + '\n');
                fileWriter.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    //private
    public Map<String, Double> getTermFrequency(Map<String, Double> termCount, Double size)
    {
        Map<String,Double> tf = new HashMap<>();
        for (Map.Entry entry : termCount.entrySet())
        {
            Double calc = (Double) entry.getValue();
            tf.put((String) entry.getKey(), Tools.round(calc / size ,8));
        }
        return tf;
    }

    //private
    public Map<String, Double> getTermCount(List<String> alltokenList)
    {
        Map<String, Double> termfrequency = new HashMap<>();
//        List<String> reviewTokens = getNounsOnly(alltokenList);
        for (String term : alltokenList)
        {
            Double count = termfrequency.get(term);
            if (count == null)
                termfrequency.put(term,1.0);
            else
                termfrequency.put(term,count +1.0);
        }
        return termfrequency;
    }

    private List<String> getNounsOnly(List<String> alltokens)
    {
        List<String> tokenList = new ArrayList<>();
        for (String token : alltokens)
        {
            //System.out.println(token);
            String[] noun = token.split("_");
            if ((noun.length >=2)) // && (noun[1].equals("nn") | noun[1].equals("nns") | noun[1].equals("nnp") | noun[1].equals("nnps")))
                tokenList.add(noun[0]);
        }
        return tokenList;
    }


    private String getCleanReview(String noisyReview)
    {
        String cleanReview;
        Matcher matcher = pattern.matcher(noisyReview );
        if (matcher.find())
        {
            cleanReview = matcher.replaceAll(" ").replace(".",". ");
        }
        else
            cleanReview = noisyReview;

        return cleanReview;
    }

    private static String getPOSTagging(String cleanReview)
    {
        String tagged = tagger.tagString(cleanReview);
        return tagged;
    }

}
