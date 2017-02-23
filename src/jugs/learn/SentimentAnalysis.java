package jugs.learn;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;

/**
 * Created by jugs on 2/22/17.
 */
public class SentimentAnalysis
{
    private StanfordCoreNLP pipeline;

    public SentimentAnalysis()
    {
        Properties prop = new Properties();
        prop.put("annotators","tokenize, ssplit, pos, lemma, parse, sentiment");
        pipeline = new StanfordCoreNLP(prop);
    }

    public void run(String text)
    {


        Annotation annotation = pipeline.process(text);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences)
        {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            System.out.print(sentiment + "\t" + sentence);
        }
    }

    public static void main(String[] args)
    {
        String analyseText = "this movie advertisement seems boaring but its rocking";

        SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
        sentimentAnalysis.run(analyseText);
    }
}
