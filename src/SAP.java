import java.util.ArrayList;
import java.util.List;

// Shortest ancestral path
public class SAP {
    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph digraph) {
        this.digraph = new Digraph(digraph);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return findSAP(list(v), list(w))[1];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return findSAP(list(v), list(w))[0];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return findSAP(v, w)[1];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return findSAP(v, w)[0];
    }

    private int[] findSAP(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths breadthFirstDirectedPathsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths breadthFirstDirectedPathsW = new BreadthFirstDirectedPaths(digraph, w);
        int length = -1;
        int ancestor = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (breadthFirstDirectedPathsV.hasPathTo(i) && breadthFirstDirectedPathsW.hasPathTo(i)) {
                int newLength = breadthFirstDirectedPathsV.distTo(i) + breadthFirstDirectedPathsW.distTo(i);
                if (length == -1 || newLength < length) {
                    length = newLength;
                    ancestor = i;
                }
            }
        }
        return new int[]{ancestor, length};
    }

    private List<Integer> list(int... values) {
        List<Integer> list = new ArrayList<Integer>();
        for (int value : values) {
            list.add(value);
        }
        return list;
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
