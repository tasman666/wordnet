import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class WordNetTest {

    private WordNet wordNet;
    @Before
    public void createWordNet() {
        wordNet = new WordNet("files/synsets.txt", "files/hypernyms.txt");
    }

    @Test
    public void check_if_is_noun() {
        assertThat(wordNet.isNoun("dupa")).isFalse();
        assertThat(wordNet.isNoun("word")).isTrue();
        assertThat(wordNet.isNoun("municipality")).isTrue();
        assertThat(wordNet.isNoun("Black_Plague")).isTrue();
    }

    @Test
    public void sap() {
        assertThat(wordNet.sap("individual","edible_fruit")).isEqualTo("physical_entity");
    }

    @Test(expected = IllegalArgumentException.class)
    public void sap_throw_exception_when_first_noun_not_exists_in_word_net() {
        wordNet.sap("asdsdfasf","edible_fruit");
    }

    @Test(expected = IllegalArgumentException.class)
    public void sap_throw_exception_when_second_noun_not_exists_in_word_net() {
        wordNet.sap("edible_fruit","asfasdfaff");
    }

    @Test
    public void distance() {
        assertThat(wordNet.distance("individual","edible_fruit")).isEqualTo(7);
        assertThat(wordNet.distance("Black_Plague","black_marlin")).isEqualTo(33);
        assertThat(wordNet.distance("American_water_spaniel","histology")).isEqualTo(27);
        assertThat(wordNet.distance("Brown_Swiss","barrel_roll")).isEqualTo(29);
    }

    @Test(expected = IllegalArgumentException.class)
    public void distance_throw_exception_when_first_noun_not_exists_in_word_net() {
        wordNet.distance("asdsdfasf","edible_fruit");
    }

    @Test(expected = IllegalArgumentException.class)
    public void distance_throw_exception_when_second_noun_not_exists_in_word_net() {
        wordNet.distance("edible_fruit","asfasdfaff");
    }
}
