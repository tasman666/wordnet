import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class OutcastTest {

    private Outcast outcast;
    @Before
    public void createOutcast() {
        outcast = new Outcast(new WordNet("files/synsets.txt", "files/hypernyms.txt"));
    }

    @Test
    public void outcast() {
        assertThat(outcast.outcast($("horse","zebra","cat","bear","table"))).isEqualTo("table");
        assertThat(outcast.outcast($("water","soda","bed","orange_juice","milk","apple_juice","tea","coffee"))).isEqualTo("bed");
        assertThat(outcast.outcast($("apple","pear","peach","banana","lime","lemon","blueberry","strawberry","mango","watermelon","potato"))).isEqualTo("potato");
    }

    private String[] $(String... values) {
        return values;
    }
}
