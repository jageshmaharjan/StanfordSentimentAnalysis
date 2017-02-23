package jugs.BigCorpus;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by jugs on 12/13/16.
 */
public class Lemmatizer implements CONSTANTS
{
    private StanfordCoreNLP pipeline;

    public Lemmatizer()
    {
        Properties properties = new Properties();
        properties.put("annotators", "tokenize, ssplit, pos, lemma");
        this.pipeline = new StanfordCoreNLP(properties);
    }

    public List<String> lemmas(String text)
    {
        List<String> lemmasList = new LinkedList<>();
        Annotation document = new Annotation(text);

        this.pipeline.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences)
        {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class))
            {
                lemmasList.add(token.getString(CoreAnnotations.PartOfSpeechAnnotation.class));
            }
        }
        return stopwordremoveAndLemma(lemmasList);
    }

    //Removes all the stop words from the given list of tokens
    public List<String> stopwordremoveAndLemma(List<String> lemmasList)
    {
        List<String> finalLemma = new ArrayList<>();
        for (int i = 0; i< lemmasList.size(); i++)
        {
            if (!isStopWord(lemmasList.get(i)))
                finalLemma.add(lemmasList.get(i));
        }
        return finalLemma;
    }

    //checks if the token(term) is a stop word or not from the listed array of stopwords in CONSTANT
    private boolean isStopWord(String tokken)
    {
        boolean isStop = false;
        for (String sword : stopWordsofwordnet)
        {
            if (sword.contains(tokken))
                isStop =  true;
        }
        return isStop;
    }
}
