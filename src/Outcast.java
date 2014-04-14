public class Outcast {
    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String outcast = null;
        int maxDistance = 0;
        for (int i = 0; i < nouns.length; i++) {
            int sumDistance = 0;
            for (int j = 0; j < nouns.length; j++) {
                sumDistance += wordnet.distance(nouns[i], nouns[j]);
            }
            if (sumDistance > maxDistance) {
                outcast = nouns[i];
                maxDistance = sumDistance;
            }
        }
        return outcast;
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {

    }
}
