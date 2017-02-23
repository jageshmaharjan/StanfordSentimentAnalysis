package jugs.learn;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.Tree;

import java.io.StringReader;
import java.util.List;

/**
 * Created by jugs on 2/23/17.
 */
//-------NOT REQUIRED---------------------------
public class ParserWithtokenAndPOS
{
    private final static String PCG_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";

    private final TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "invertible=true");

    private final LexicalizedParser parser = LexicalizedParser.loadModel(PCG_MODEL);

    public Tree parse(String str)
    {
        Tokenizer<CoreLabel> tokenizer = tokenizerFactory.getTokenizer(new StringReader(str));
        List<CoreLabel> tokens = tokenizer.tokenize();
        Tree tree = parser.apply(tokens);
        return tree;
    }

    public static void main(String[] args)
    {
        String str = "My dog also likes to eat sausage";
        ParserWithtokenAndPOS parser = new ParserWithtokenAndPOS();
        Tree tree = parser.parse(str);

        List<Tree> leaves = tree.getLeaves();
        for (Tree leaf : leaves)
        {
            Tree parent = leaf.parent(tree);
            System.out.println(leaf.label().value() + "_" + parent.label().value() + " ");
        }
        System.out.println();
    }
}
