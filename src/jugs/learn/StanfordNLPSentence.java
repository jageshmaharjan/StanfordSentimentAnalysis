package jugs.learn;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;

import java.io.FileReader;
import java.util.List;

/**
 * Created by jugs on 1/2/17.
 */
public class StanfordNLPSentence
{
    public static void main(String[] args) throws Exception
    {
        for (String arg : args)
        {
            System.out.println(arg);
            DocumentPreprocessor dp = new DocumentPreprocessor(arg);
            for (List<HasWord> sentence : dp)
            {
                System.out.println(sentence);
            }

            PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<>(new FileReader(arg), new CoreLabelTokenFactory(),"");
            while (ptbt.hasNext())
            {
                CoreLabel label = ptbt.next();
                System.out.println(label);
            }
        }

    }
}
