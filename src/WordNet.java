import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordNet {

    private Map<String, Set<Integer>> synsetsMap = new HashMap<String, Set<Integer>>();
    private Map<Integer, String> synsetsIdMap = new HashMap<Integer, String>();
    private Set<String> nouns = new HashSet<String>();
    private Digraph digraph;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        loadSynsets(synsets);
        createDigraph(hypernyms);
    }

    private void createDigraph(String hypernyms) {
        In inHypernyms = new In(new File(hypernyms));
        digraph = new Digraph(synsetsIdMap.size());
        while (inHypernyms.hasNextLine()) {
            String[] lineValues = inHypernyms.readLine().split(",");
            for (int i = 1; i < lineValues.length; i++) {
                digraph.addEdge(Integer.valueOf(lineValues[0]), Integer.valueOf(lineValues[i]));
            }
        }
        sap = new SAP(digraph);
    }

    private void loadSynsets(String synsets) {
        In inSynsets = new In(new File(synsets));
        while (inSynsets.hasNextLine()) {
            String[] lineValues = inSynsets.readLine().split(",");
            String[] nounsInLine = lineValues[1].split(" ");
            for (int i = 0; i < nounsInLine.length; i++) {
                nouns.add(nounsInLine[i]);
                initializeSynsetsMap(lineValues[0], nounsInLine[i]);
            }
            synsetsIdMap.put(Integer.valueOf(lineValues[0]), lineValues[1]);
        }
    }

    private void initializeSynsetsMap(String lineValue, String key) {
        if (synsetsMap.containsKey(key)) {
            synsetsMap.get(key).add(Integer.valueOf(lineValue));
        } else {
            Set set = new HashSet<Integer>();
            set.add(Integer.valueOf(lineValue));
            synsetsMap.put(key, set);
        }
    }

    // the set of nouns (no duplicates), returned as an Iterable
    public Iterable<String> nouns() {
        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (isNoun(nounA) == false || isNoun(nounB) == false) {
            throw new IllegalArgumentException("not proper noun");
        }
        return sap.length(synsetsMap.get(nounA),synsetsMap.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (isNoun(nounA) == false || isNoun(nounB) == false) {
            throw new IllegalArgumentException("not proper noun");
        }
        int ancestor = sap.ancestor(synsetsMap.get(nounA),synsetsMap.get(nounB));
        return synsetsIdMap.get(ancestor);
    }

    // for unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("files/synsets.txt", "files/hypernyms.txt");
        System.out.println(wordNet.synsetsIdMap.size());//82,192
    }
}
