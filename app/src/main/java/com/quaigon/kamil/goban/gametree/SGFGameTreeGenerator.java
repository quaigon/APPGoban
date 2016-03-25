package com.quaigon.kamil.goban.gametree;

import com.quaigon.kamil.sgfparser.SGFProvider;
import com.quaigon.kamil.sgfparser.SGFnode;
import com.quaigon.kamil.sgfparser.StringSGFProvider;
import com.quaigon.kamil.sgfparser.Treewalker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SGFGameTreeGenerator {
    private String sgf;
    private Treewalker walker;

    public SGFGameTreeGenerator(SGFProvider sgfProvider) {
        this.sgf = sgfProvider.getLines();
        SGFnode sgFnode = new SGFnode();
        sgFnode.fromString(sgf);
        this.walker = new Treewalker(sgFnode);
    }

    public Node getTree() {
        Node prev = null;
        Node root = null;
        while (walker.getNextMove() != null) {
            Node node;
            String cords = getCords(walker.printCurrent());
            String comment = getComment(walker.printCurrent());

            Move move = sgfCordsToMove(cords);
            if (null != prev) {
                node = new Node(prev, move);
                node.addComent(comment);
                prev.addChild(node);
                prev = node;
            } else {
                node = new Node(null, move);
                node.addComent(comment);
                root = node;
                prev = node;
            }
        }
        return root;
    }

    private Move sgfCordsToMove(String cords) {
        char color = cords.charAt(0);
        char x = cords.charAt(2);
        char y = cords.charAt(3);

        int intcolor = 0;
        if ('B' == color) intcolor = 1;

        int intx = Character.getNumericValue(x) - Character.getNumericValue('a') + 1;
        int inty = Character.getNumericValue(y) - Character.getNumericValue('a') + 1;

        return new Move(intx, inty, intcolor);
    }

    private String getCords(String current) {
        String cords = null;
        Pattern cordsPattern = Pattern.compile("[WB]\\[\\w\\w\\]");
        Matcher matcher = cordsPattern.matcher(current);
        if (matcher.find()) cords = matcher.group();

        return cords;
    }

    private String getComment(String current) {
        String comment = null;
        Pattern commentPattern = Pattern.compile("C\\[([\\u0000-\\u00FF]+)\\]");
        Matcher matcher = commentPattern.matcher(current);
        if (matcher.find()) comment = matcher.group();

        return comment;
    }

    public static void main(String[] args) {
        String gameSgf = "(;DT[2010-10-13]EV[15th Samsung Cup]\n" +
                "PB[Choi Cheolhan]BR[9p]PW[Park Jungwhan]WR[8p]\n" +
                "KM[6.5]RE[W+R]SO[Go4Go.net]\n" +
                ";B[pd];W[dd];B[qp];W[dp];B[fq];W[cn];B[nq];W[qj];B[fc];W[qf];B[qh];W[of];B[mc];W[rd];B[qc];W[pi];B[cf];W[fd];B[gd];W[fe];B[ge];W[ec];B[gc];W[ff];B[cc];W[cd];B[bd];W[fb];B[bb];W[gb];B[ic];W[gf];B[ie];W[eq];B[fp];W[ip];B[en];W[er];B[kq];W[qm];B[db];W[eb];B[dl];W[jo];B[pn];W[pm];B[om];W[lp];B[pk];W[qk];B[kp];W[ko];B[lo];W[on];B[ln];W[nn];B[po];W[be];B[ce];W[ad];B[bc];W[cg];B[dg];W[df];B[bg];W[ch];B[bf];W[mp];B[np];W[mo];B[mn];W[lm];B[mm];W[nm];B[ml];W[no];B[mr];W[jm];B[jk];W[nk];B[mk];W[hl];B[co];W[do];B[dn];W[cm];B[bo];W[cp];B[cl];W[bn];B[dm];W[bl];B[an];W[bm];B[bk];W[bp];B[dh];W[ij];B[nl];W[ol];B[nj];W[ki];B[ok];W[mi];B[ni];W[ph];B[mh];W[fi];B[ei];W[jd];B[id];W[lh];B[mg];W[kf];B[ig];W[md];B[le];W[lf];B[mf];W[ld];B[ke];W[me];B[je];W[nh];B[oh];W[ng];B[nf];W[og];B[ne];W[hi];B[jh];W[jj];B[qe];W[pe];B[pl];W[om];B[qg];W[oe];B[nd];W[oi];B[kk];W[km];B[rf];W[od];B[oc];W[qd];B[pf];W[pc];B[ql];W[rl];B[rk];W[rm];B[rj];W[pj];B[qi];W[sk];B[ri];W[pd];B[lk];W[oj];B[gj];W[gi];B[ik];W[hk];B[fh];W[gh])\n";

        SGFGameTreeGenerator SGFGameTreeGenerator = new SGFGameTreeGenerator(new StringSGFProvider(gameSgf));
        System.out.println("lol");
        Node node = SGFGameTreeGenerator.getTree();
        while (node.hasChild()) {
            System.out.println(node.getMove().getX() + " " + node.getMove().getY() +" " + node.getMove().getColor());
            node = node.getNode(0);
        }

        while (!node.isRoot()){
            System.out.println(node.getMove().getX() + " " + node.getMove().getY() +" " + node.getMove().getColor());
            node = node.getParent();
        }
    }

}
