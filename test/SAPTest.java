import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class SAPTest {

    @Test
    public void digraph_1() {
        SAP sap = createSAP("files/digraph1.txt");

        assertThat(sap.length(3, 11)).isEqualTo(4);
        assertThat(sap.ancestor(3, 11)).isEqualTo(1);

        assertThat(sap.length(9, 12)).isEqualTo(3);
        assertThat(sap.ancestor(9, 12)).isEqualTo(5);

        assertThat(sap.length(7, 2)).isEqualTo(4);
        assertThat(sap.ancestor(7, 2)).isEqualTo(0);

        assertThat(sap.length(1, 6)).isEqualTo(-1);
        assertThat(sap.ancestor(1, 6)).isEqualTo(-1);
    }

    @Test
    public void digraph_2() {
        SAP sap = createSAP("files/digraph2.txt");

        assertThat(sap.length(1, 5)).isEqualTo(2);
        assertThat(sap.ancestor(1, 5)).isEqualTo(0);

        assertThat(sap.length(1, 0)).isEqualTo(1);
        assertThat(sap.ancestor(1, 0)).isEqualTo(0);

        assertThat(sap.length(1, 2)).isEqualTo(1);
        assertThat(sap.ancestor(1, 2)).isEqualTo(2);

        assertThat(sap.length(4, 1)).isEqualTo(3);
        assertThat(sap.ancestor(4, 1)).isEqualTo(0);
    }

    @Test
    public void digraph_1_many_vertices() {
        SAP sap = createSAP("files/digraph1.txt");

        assertThat(sap.length(list(7,1), list(2))).isEqualTo(2);
        assertThat(sap.ancestor(list(7,1), list(2))).isEqualTo(0);

        assertThat(sap.length(list(11,12), list(7,8))).isEqualTo(5);
        assertThat(sap.ancestor(list(11,12), list(7,8))).isEqualTo(1);

        assertThat(sap.length(list(11,12), list(6))).isEqualTo(-1);
        assertThat(sap.ancestor(list(11,12), list(6))).isEqualTo(-1);
    }

    @Test
    public void sap_is_immutable() {
        In in = new In(new File("files/digraph1.txt"));
        Digraph digraph = new Digraph(in);

        SAP sap = new SAP(digraph);
        digraph.addEdge(2,6);

        assertThat(sap.length(2, 6)).isEqualTo(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throw_index_of_bound_exception_if_first_argument_is_below_zero() {
        SAP sap = createSAP("files/digraph1.txt");

        sap.length(-1, 5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throw_index_of_bound_exception_if_first_argument_is_more_than_number_of_vertices() {
        SAP sap = createSAP("files/digraph1.txt");

        sap.length(20, 5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throw_index_of_bound_exception_if_second_argument_is_below_zero() {
        SAP sap = createSAP("files/digraph1.txt");

        sap.length(1, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throw_index_of_bound_exception_if_second_argument_is_more_than_number_of_vertices() {
        SAP sap = createSAP("files/digraph1.txt");

        sap.length(5, 20);
    }

    private List<Integer> list(int... values) {
        List<Integer> list = new ArrayList<Integer>();
        for (Integer value : values) {
            list.add(value);
        }
        return list;
    }

    private SAP createSAP(String file) {
        In in = new In(new File(file));
        return new SAP(new Digraph(in));
    }
}
