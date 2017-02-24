package jugs.learn;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by jugs on 2/24/17.
 */
//--------THIS CLASS PARSE THE SENTENCE GENERATED BY THE SentenceSplitter.java Class, GENERATES THE TOKENS OF THE SENTENCE AND GENERATES THE PHRSESE USING " STANFORD N-GRAM" CLASS.
// -------THE OUTPUT GENERATED BY THIS CLASS IS "dictionary.txt", AND "SOStr.txt".

public class PhraseDictionary
{
    List<String> NON_TOKENS = Arrays.asList("-LRB-", "-RRB-", "," , ".","\"", "``");

    private StanfordCoreNLP pipeline;
    private int phraseID = 0;
    public static void main(String[] args) throws Exception
    {
        PhraseDictionary dictionary = new PhraseDictionary();
        dictionary.readData();
    }

    public PhraseDictionary()
    {
        Properties prop = new Properties();
        prop.put("annotators", "tokenize,ssplit");
        pipeline = new StanfordCoreNLP(prop);
    }

    private void readData() throws Exception
    {
        Set<String> phraseSet = new HashSet<>();
        String path = "DataSentence.txt";
        File file = new File(path);
        String line;
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((line = br.readLine()) != null)
        {
            int indentSeperator = line.indexOf("\t");
            String sentence = line.substring(indentSeperator+1,line.length());
            Set<String> phrasesLst = new HashSet<>(generatePhrase(sentence));

            for (String phrase : phrasesLst)
            {
                phraseSet.add(phrase);
            }
        }
        writeToDictionary(phraseSet);
    }

    private void writeToDictionary(Set<String> phrases) throws Exception
    {
        FileWriter fw = new FileWriter("dictionary.txt",true);
        for (String phrase : phrases)
        {
            fw.write(phrase + "|" + phraseID );
            fw.write("\n");
            phraseID++;
        }
        fw.close();
    }

    private List generatePhrase(String sentence) throws Exception
    {
        Annotation document = new Annotation(sentence);
        pipeline.annotate(document);

        List<String> tokenLst = new ArrayList<>();
        for (CoreLabel token : document.get(CoreAnnotations.TokensAnnotation.class))
        {
            String tok = processString(token.toString()); //token.toString().substring(0,token.toString().length()-2);
            if (!NON_TOKENS.stream().anyMatch(str -> str.equals(tok)))
                tokenLst.add(tok);
        }
        saveToSOStr(tokenLst);
        Collection ngramTokens = StringUtils.getNgrams(tokenLst,1,tokenLst.size());
        return (List) ngramTokens;
    }

    private void saveToSOStr(List<String> tokenLst)throws Exception
    {
        FileWriter fw = new FileWriter("SOStr.txt",true);
        for (String token : tokenLst)
        {
            fw.write(token + "|");
        }
        fw.write("." + "\n");
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