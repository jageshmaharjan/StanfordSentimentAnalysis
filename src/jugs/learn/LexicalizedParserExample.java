package jugs.learn;

import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.sentiment.Evaluate.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static edu.stanford.nlp.ling.SentenceUtils.toWordList;

/**
 * Created by jugs on 1/5/17.
 */
public class LexicalizedParserExample
{
    public static void main(String[] args)
    {
        LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz",
                "-maxLength", "80", "-retainTmpSubcategories");

        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
        tlp.setGenerateOriginalDependencies(true);
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();

        String[] sent = {"This", "is", "an", "easy", "sentence", "." };
        Tree parse = lp.apply(SentenceUtils.toWordList(sent));
        GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
        Collection<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
        System.out.println(tdl);

    }

    public void LexParse()
    {
        LexicalizedParser lp = LexicalizedParser.loadModel();
        String[] sent3 = { "It", "can", "can", "it", "." };
        String[] tag3 = { "PRP", "MD", "VB", "PRP", "." };
        List sentence3 = new ArrayList();
        for (int i = 0; i < sent3.length; i++)
        {
            sentence3.add(new TaggedWord(sent3[i], tag3[i]));
        }
        Tree parse = lp.parse(sentence3);
        parse.pennPrint();
    }


}
