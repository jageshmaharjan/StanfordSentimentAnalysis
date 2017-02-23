package jugs.learn;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.util.CoreMap;

/**
 * Created by jugs on 1/5/17.
 */

//--------THIS CLASS TOKENIZE THE SENTENCES FROM THE INPUT DOCUMENT(DATASET), AND OUTPUTS THE STEEM TOKEN , ITS POS AND ENTITY TYPE
// And saves it to the file
//Example::
// The	the	DT	O
// formula	formula	NN	O
// is	be	VBZ	O
// simple	simple	JJ	O
// .	.	.	O
// Trap	trap	VB	O
// a	a	DT	O

public class TokenizePOSNERTask
{
    private StanfordCoreNLP pipeline;
    public TokenizePOSNERTask()
    {
        Properties prop = new Properties();
        prop.put("annotators","tokenize, ssplit, pos, lemma, ner,parse,dcoref");
        pipeline = new StanfordCoreNLP(prop);

    }

    public static void main(String[] args) throws Exception
    {
        File inputFile = new File("/home/jugs/Desktop/Dataset/ReviewJSON.txt");
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String line ;
        String text = "";
        TokenizePOSNERTask simpleExample = new TokenizePOSNERTask();
        while ((line = br.readLine()) != null)
        {
            String review = line.split("\\: \"")[1].substring(0,line.split("\\: \"")[1].length()-10);
            simpleExample.parsing(review.replaceAll("\\\\r\\\\n"," ").replaceAll("\\.{2,}",". "));
            System.out.println();
        }
    }

    private void parsing(String text) throws Exception
    {
        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        //--- Fragments each document into sentences
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences)
        {
            //--- Fragments each sentence into tokens
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class))
            {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                System.out.println("word: "+word + ", lemma: " + lemma +  ", pos: " + pos + ", ne: " + ne);
                writeToFile(word,lemma,pos,ne);
            }
//            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
//            System.out.println("parse tree: \n" + tree);
//
//            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
//            System.out.println("dependencies graph: :\n" + dependencies);
//
//            Map<Integer,CorefChain> graph = document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
//            System.out.println(graph);
        }
    }

    private void writeToFile(String word, String lemma, String pos, String ne) throws Exception
    {
        FileWriter fileWriter = new FileWriter("ReviewLabelling_2.txt",true);
        fileWriter.write(word+'\t'+lemma+'\t'+pos+'\t'+ne+'\n');
        fileWriter.close();
    }
}