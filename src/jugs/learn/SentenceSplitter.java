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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by jugs on 1/2/17.
 */
public class SentenceSplitter
{
    List<String> NON_TOKENS = Arrays.asList("-LRB-", "," , ".");

    private StanfordCoreNLP pipeline;
    private Set<String> phrases = new HashSet<>();
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

        sentenceSplitter.writeToFile();
    }

    private void writeToFile()
    {


    }

    private void getReviews(String data) throws ParseException
    {
        //LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz",
        //        "-maxLength", "80", "-retainTmpSubcategories");

        //TreebankLanguagePack tlp = new PennTreebankLanguagePack();
        //tlp.setGenerateOriginalDependencies(true);
        //GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

        Annotation document = new Annotation(data);
        pipeline.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences)
        {
            List<String> tokenLst = new ArrayList<>();
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class))
            {
                String tok = processString(token.toString()); //token.toString().substring(0,token.toString().length()-2);
                if (!NON_TOKENS.stream().anyMatch(str -> str.equals(tok)))
                    tokenLst.add(tok);
            }

            Collection ngramsTokens = StringUtils.getNgrams(tokenLst, 1, tokenLst.size());
            phrases.addAll(ngramsTokens);
            System.out.println();
        }
    }

    private String processString(String str)
    {
        String reverseStr = new StringBuilder(str).reverse().toString();
        int index = reverseStr.indexOf("-");
        String trimString = reverseStr.substring(index+1,reverseStr.length());
        return new StringBuilder(trimString).reverse().toString();
    }

}
