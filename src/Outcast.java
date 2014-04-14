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
        Integer[][] distances = new Integer[nouns.length][nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            int sumDistance = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i != j) {
                    sumDistance += getDistance(distances, nouns, i, j);
                }
            }
            if (sumDistance > maxDistance) {
                outcast = nouns[i];
                maxDistance = sumDistance;
            }
        }
        return outcast;
    }

    private int getDistance(Integer[][] distances, String[] nouns, int i, int j) {
        if (distances[i][j] != null) {
            return distances[i][j];
        } else {
            int distance = wordnet.distance(nouns[i], nouns[j]);
            distances[i][j] = distance;
            distances[j][i] = distance;
            return distance;
        }
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {

    }
}
