import org.junit.Test;

public class SmallWordNetTest {

    @Test(expected=IllegalArgumentException.class)
    public void throw_exception_when_word_net_has_more_than_one_root() {
        new WordNet("files/synsets-small.txt", "files/hypernymsInvalidTwoRoots.txt");
    }
}
