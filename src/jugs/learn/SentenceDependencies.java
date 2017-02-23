package jugs.learn;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Created by jugs on 2/20/17.
 */


//-------------NOT WORKING----------

public class SentenceDependencies
{
    private StanfordCoreNLP pipeline;
    public SentenceDependencies()
    {
        Properties prop = new Properties();
        prop.put("annotators","tokenize, ssplit, pos, lemma, ner,parse,dcoref");
        pipeline = new StanfordCoreNLP(prop);
    }

    public static void main(String[] args)
    {
        SentenceDependencies sd = new SentenceDependencies();
        sd.Run();
    }

    public void Run()
    {
        Annotation document = new Annotation("i like an icecream");
        pipeline.annotate(document);

//        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
//        for (CoreMap sentence : sentences)
//        {
            Tree tree = document.get(TreeCoreAnnotations.TreeAnnotation.class);
            SemanticGraph basic = document.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
            Collection<TypedDependency> deps = basic.typedDependencies();
            for (TypedDependency typedDeps : deps)
            {
                GrammaticalRelation reln = typedDeps.reln();
                String type = reln.toString();
                System.out.print(type);
            }
//            SemanticGraph colapsed = document.get(SemanticGraphCoreAnnotations.CollapsedDependenciesAnnotation.class);
//            Collection<TypedDependency> deps =

//        }
    }
}
