package jugs.learn;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.trees.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jugs on 2/22/17.
 */
public class RunStanfordParser
{
    public static void main(String[] args) throws Exception
    {
        //String parserFileUrl = args[3];
        String fileToParse = args[1];

        //LexicalizedParser lp = new LexicalizedParser("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
        LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz",
                "-maxLength", "80", "-retainTmpSubcategories");


        FileInputStream fstream = new FileInputStream(fileToParse);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringReader sr;
        PTBTokenizer tkzr;
        WordStemmer ls = new WordStemmer();

        String strLine;
        while ((strLine = br.readLine()) != null)
        {
            System.out.println("Token and pars - ing: " + strLine);

            sr = new StringReader(strLine);
            tkzr = PTBTokenizer.newPTBTokenizer(sr);
            List toks = tkzr.tokenize();
            System.out.println("tokens: " + toks);

            Tree parse = lp.apply(toks);

            ArrayList<String> words = new ArrayList<>();
            ArrayList<String> stems = new ArrayList<>();
            ArrayList<String> tags = new ArrayList<>();

            for (TaggedWord tw : parse.taggedYield())
            {
                words.add(tw.word());
                tags.add(tw.tag());
            }

            ls.visitTree(parse);
            for (TaggedWord tw : parse.taggedYield())
            {
                stems.add(tw.word());
            }

            TreebankLanguagePack tlp = new PennTreebankLanguagePack();
            GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
            GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
            Collection td1 = gs.typedDependenciesCollapsed();

            System.out.println("words: "+ words);
            System.out.println("POSTags: "+ tags);
            System.out.println("steemmedwordAndtags: " + stems );
            System.out.println("typeDependencies: "+ td1);
        }
    }
}
