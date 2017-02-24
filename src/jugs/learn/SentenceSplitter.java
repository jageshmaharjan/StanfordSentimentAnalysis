package jugs.learn;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.StringUtils;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

/**
 * Created by jugs on 1/2/17.
 */
//-----------THIS CLASS READS THE DATAFILE AND GENERATES THE ”DATASENTENCES.TXT“ FILE WITH EACH SENTENCE EACH LINE ALONG WITH SENTENCE——INDEX----------

public class SentenceSplitter
{
    private StanfordCoreNLP pipeline;
    private int sentenceIndex = 1;

    public SentenceSplitter()
    {
        Properties prop = new Properties();
        prop.put("annotators","tokenize,ssplit");
        pipeline = new StanfordCoreNLP(prop);
    }

    public static void main(String[] args) throws Exception
    {
        File file = new File("/home/jugs/Desktop/Dataset/Reviewjson.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line ;

        SentenceSplitter sentenceSplitter = new SentenceSplitter();
        while ((line = br.readLine()) != null)
        {
            String review = line.split("\\: \"")[1].substring(0,line.split("\\: \"")[1].length()-10);
            sentenceSplitter.getReviews(review.replaceAll("\\\\r\\\\n"," ").replaceAll("\\.{2,}",". "));
        }

    }

    private void getReviews(String data) throws Exception
    {
        //LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz",
        //        "-maxLength", "80", "-retainTmpSubcategories");

        //TreebankLanguagePack tlp = new PennTreebankLanguagePack();
        //tlp.setGenerateOriginalDependencies(true);
        //GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

        Annotation document = new Annotation(data);
        pipeline.annotate(document);
        Set<String> phrases = new HashSet<>();
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences)
        {
            saveDataSentence(sentence);
        }
    }

    private void saveDataSentence(CoreMap sentence) throws IOException
    {
        FileWriter fw = new FileWriter("DataSentence.txt", true);
        fw.write(sentenceIndex + "\t" + sentence.toString());
        fw.write("\n");
        sentenceIndex++;
        fw.close();
    }

    private String processString(String str)
    {
        String reverseStr = new StringBuilder(str).reverse().toString();
        int index = reverseStr.indexOf("-");
        String trimString = reverseStr.substring(index+1,reverseStr.length());
        return new StringBuilder(trimString).reverse().toString();
    }

}
