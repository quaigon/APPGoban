package com.quaigon.kamil.sgfparser;
/**
 * A tree of SGFnodes store the record of the game, and can
 * import/export SGF format.
 */

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class SGFnode implements Serializable {
    public SGFnode parent;
    public Vector children;
    public Hashtable properties;

    public boolean head_p = false;

    // Like GoBoard, we need to keep track of the *bottom* of our tree
    private SGFnode tempnode;

    // Needed for parser, so we *don't* build a child node on the very
    // first ";" encountered in the SGF stream.
    private boolean firsttime = true;

    /** Basic constructor. */
    public SGFnode() {
        initialize();
    }

    /** Constructor, given the parent node. */
    public SGFnode(SGFnode p) {
        initialize(p);
    }

    // Helpers for constructors, since they apparently can't call each
    // other.  Bummer.
    private void initialize() {
        properties = new Hashtable();
        parent = null;
        children = new Vector();
    }

    private void initialize(SGFnode p) {
        initialize();
        this.set_parent(p);   // give this new node a pointer to its parent
        p.add_child(this);    // and add this child to the parent's list.
    }


    // Convert a GameTree into an SGF string -- the crux of SGF output!
    // This is the entry point.

    public String toString() {
        StringBuffer sbuf = new StringBuffer(80);
        dump_properties(sbuf);
        // print_tree(sbuf, this);
        return sbuf.toString();
    }

    // Obviously, this is a recursive depth-first search;
    // it prints every property of every node, *including* all branches.

    private void print_tree(StringBuffer sbuf, SGFnode currentnode) {
        Enumeration e = currentnode.children.elements();

        // First, if this node is the top of a gametree, print "("
        if (currentnode.head_p)
            sbuf.append("(");

        // Then print all properties of this node
        currentnode.dump_properties(sbuf);

        // Is this a dead-end node?  If so, close the tree and return
        if (currentnode.children.size() == 0) {
            sbuf.append(")\n");
            return;
        }

        // Otherwise, loop through this node's children:
        while (e.hasMoreElements()) {
            SGFnode child = (SGFnode) e.nextElement();
            print_tree(sbuf, child);
        }

        // Add final close-parenthesis IFF we're back at the root node
        if (currentnode.head_p && (currentnode.parent != null))
            sbuf.append(")\n");

        return;
    }


    // Usful utility: dump all properties of a node into a StringBuffer.
    // This is used by SGFnode.toString()

    public void dump_properties(StringBuffer sbuf) {
        Enumeration e = properties.keys();
        if (!e.hasMoreElements())
            return;
        else
            sbuf.append(";");
        while (e.hasMoreElements()) {
            String currentKey = (String) e.nextElement();
            String currentValue = (String) get_prop(currentKey);
            sbuf.append(currentKey + "[" + currentValue + "] ");
        }
        sbuf.append("\n");  // for visual clarity, one node per line
    }


    public Vector getChildren() {
        return children;
    }


    /** The guts of SGF parsing.
     *
     * How does parsing work?  Well, it's a simple recursive-descent
     * parser, because SGF is a pretty simple format.  It's hand-written
     * not generated.  The tradeoff of writing it by hand is that a lot
     * of wheels get reinvented, but the code is also lot cleaner and
     * more readable this way.  Anyway, here's a quick BNF (see
     * http://www.poboxes.com/sgf/ for the full spec):
     *
     *    Collection = GameTree { GameTree }
     *    GameTree   = "(" Sequence { GameTree } ")"
     *    Sequence   = Node { Node }
     *    Node       = ";" { Property }
     *    Property   = PropIdent PropValue { PropValue }
     *    PropIdent  = UpperCaseLetter { UpperCaseLetter }
     *    PropValue  = "[" CValueType "]"
     *    CValueType = (ValueType | COMPOSE)
     *    ValueType  = (NONE | NUMBER | REAL | DOUBLE | COLOR
     *                  | SIMPLETEXT | TEXT | POINT | MOVE | STONE)
     *
     * ---------------------

     *  Karl, I think you should bow before the brilliance of my single
     *  recursive parsing function.  It's SO elegant, isn't it? */


    // The entry routine for our parser: take an SGF string and build a
    // GameTree in memory (with (*this) being the root node, of course)
    public void fromString(String str) {
        PushbackReader pushrdr = new PushbackReader(new StringReader(str));
        parse(pushrdr, this);
        firsttime = true;
    }


  /* Our recursive parser! */

    private void parse(PushbackReader rdr, SGFnode currentnode) {
        int ch;
        SGFnode new_node;
        try {
            while ((ch = rdr.read()) != -1) {
                if (whitespace((char) ch)) // skip whitespace
                    continue;

                if (isUppercase((char) ch)) {  // is it a property?
                    rdr.unread(ch);
                    String key = get_property_name(rdr); // read whole property NAME
                    String val = get_property_value(rdr);  // read entire [value]
                    if ((key == null) || (val == null)) {
                        parse_debug("Error reading property:");
                        parse_debug("  ...either property name or value was null;");
                        parse_debug("  ...property will be ignored");
                        continue;
                    }
                    // else, add the property to our new node.
                    currentnode.set_prop_literal(key, val);
                    continue;
                }

                // else
                switch (ch) {
                    case '(':
                        currentnode.head_p = true;  // mark this node as Head of a subtree
                        continue;

                    case ')':  // pop back!
                        return;

                    case ';':  // build a new node and recurse
                        if ((currentnode.parent == null) && firsttime) {
                            new_node = currentnode;
                            firsttime = false;
                        } else
                            new_node = new SGFnode(currentnode);
                        parse(rdr, new_node);
                        // after recursion returns:
                        if (!currentnode.head_p)  // keep popping up the stack
                            return;
                        else
                            continue;

                    default:
                        parse_debug("Unrecognized char in SGF: '" + (char) ch + "'");
                        continue;
                }
            }
        } catch (IOException e) {
            System.out.println("SGFnode.parse(): IOException: " + e);
        }
    }


    // We get here because parse_node found an uppercase char and pushed
    // it back;  returns the full name of the property.

    private String get_property_name(PushbackReader rdr) {
        int ch;
        StringBuffer sbuf = new StringBuffer();
        try {
            ch = rdr.read();  // read the first uppercase char back again
            sbuf.append((char) ch);
            while ((ch = rdr.read()) != -1) // start reading chars
            {
                if (isUppercase((char) ch)) { // and append uppercase ones
                    sbuf.append((char) ch);
                    continue;
                } else {              // until we encounter a non-uppercase!
                    rdr.unread(ch);   // push the non-uppercase letter back
                    break;            // and stop reading
                }
            }
        } catch (IOException e) {
            System.out.println("SGFnode.parse_property_name(): IOException: " + e);
        }
        parse_debug("parse_property_name is returning '" + sbuf.toString() + "'");
        return sbuf.toString();
    }

    // Read a entire property value in square brackets, return in a string.
    // Be sure to *preserve* any escaped characters!

    private String get_property_value(PushbackReader rdr) {
        int ch;
        StringBuffer sbuf = new StringBuffer();
        try {

            while ((ch = rdr.read()) != -1) // first, skip all whitespace
            {
                if (!whitespace((char) ch)) {
                    rdr.unread(ch);
                    break;
                }
            }

            ch = rdr.read();  // read the first "real" char -- it better be '['
            if (ch != '[') {
                System.out.println("SGFnode.parse_property_value(): error:");
                System.out.println("  ... didn't find a '[' after property name!");
                return null;
            }
            // else, we *did* read a '[', as expected...
            while ((ch = rdr.read()) != -1) // start reading chars
            {
                if (ch == ']') { // all done.  don't need to push it back, either.
                    break;
                } else if (ch == '\\') { // if we hit a single backslash
                    sbuf.append((char) ch); // write it to our StringBuffer
                    ch = rdr.read();  // and immediately snarf the *next* character!
                }
                // finally, write this character to our StringBuffer
                sbuf.append((char) ch);
                continue;
            }
        } catch (IOException e) {
            System.out.println("SGFnode.parse_property_value(): IOException: " + e);
        }
        parse_debug("parse_property_value is returning '" + sbuf.toString() + "'");
        return sbuf.toString();
    }


    /* Return true iff ch is whitespace.  What could be simpler? */
    private boolean whitespace(char ch) {
        switch (ch) {
            case ' ':
            case '\t':
            case '\r':
            case '\n':
            case '\f':
                return true;
            default:
                return false;
        }
    }

    private boolean isUppercase(char ch) {
        return Character.isUpperCase(ch);
    }


    public Object get_prop(String key) {
        return properties.get(key);
    }


    // Adds a property to a node, but escapes dangerous chars in "value"

    public void set_prop_safely(String key, String value) {
        char ch;
        StringBuffer sbuf = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            ch = value.charAt(i);
            if ((ch == ']') ||
                    (ch == ':') ||
                    (ch == '\\')) // this is just a *single* backslash! :)
            {
                sbuf.append('\\');
            }
            sbuf.append(ch);
        }
        properties.put(key, sbuf.toString());
    }

    // Adds a property to a node, no modification of "value"

    public void set_prop_literal(String key, String value) {
        properties.put(key, value);
    }


    // If parent is null, we're the root node.

    public void set_parent(SGFnode p) {
        parent = p;
    }

    public SGFnode get_parent() {
        return parent;
    }


    // Vector of SGFnode objects.
    // A node can have multiple children, of course; our rule is that
    // the first child (index 0) is the "main" line of play.  As for the
    // others, they might have names, but SGFnode doesn't need to know
    // their names.  At least not the way things are right now.

    public SGFnode get_child(int i) {
        if (i > (children.size() - 1)) {
            children.setSize(i + 1);
        }
        return (SGFnode) children.elementAt(i);
    }

    public void set_child(int i, SGFnode child) {
        if (i > (children.size() - 1)) {
            children.setSize(i + 1);
        }
        children.setElementAt(child, i);
    }

    // just add a child to the vector (create a new index)

    public void add_child(SGFnode child) {
        //    children.addElement(child);
        int size = children.size();
        children.setSize(size + 1);
        children.setElementAt(child, size);
    }

    // Debugging utility to examine the parser's recursion!
    // To turn off debugging, just comment-out this function's one line.

    private void parse_debug(String str) {
        // System.err.println("Parser: " + str);
    }


  /*  public static void main( String[] args ) {
        String test = "\"(;DT[2010-10-13]EV[15th Samsung Cup]\\n\"+\n" +
                "            \"PB[Choi Cheolhan]BR[9p]PW[Park Jungwhan]WR[8p]\\n\"+\n" +
                "            \"KM[6.5]RE[W+R]SO[Go4Go.net]\\n\"+\n" +
                "            \";B[pd];W[dd];B[qp];W[dp];B[fq];W[cn];B[nq];W[qj];B[fc];W[qf];B[qh];W[of];B[mc];W[rd];B[qc];W[pi];B[cf];W[fd];B[gd];W[fe];B[ge];W[ec];B[gc];W[ff];B[cc];W[cd];B[bd];W[fb];B[bb];W[gb];B[ic];W[gf];B[ie];W[eq];B[fp];W[ip];B[en];W[er];B[kq];W[qm];B[db];W[eb];B[dl];W[jo];B[pn];W[pm];B[om];W[lp];B[pk];W[qk];B[kp];W[ko];B[lo];W[on];B[ln];W[nn];B[po];W[be];B[ce];W[ad];B[bc];W[cg];B[dg];W[df];B[bg];W[ch];B[bf];W[mp];B[np];W[mo];B[mn];W[lm];B[mm];W[nm];B[ml];W[no];B[mr];W[jm];B[jk];W[nk];B[mk];W[hl];B[co];W[do];B[dn];W[cm];B[bo];W[cp];B[cl];W[bn];B[dm];W[bl];B[an];W[bm];B[bk];W[bp];B[dh];W[ij];B[nl];W[ol];B[nj];W[ki];B[ok];W[mi];B[ni];W[ph];B[mh];W[fi];B[ei];W[jd];B[id];W[lh];B[mg];W[kf];B[ig];W[md];B[le];W[lf];B[mf];W[ld];B[ke];W[me];B[je];W[nh];B[oh];W[ng];B[nf];W[og];B[ne];W[hi];B[jh];W[jj];B[qe];W[pe];B[pl];W[om];B[qg];W[oe];B[nd];W[oi];B[kk];W[km];B[rf];W[od];B[oc];W[qd];B[pf];W[pc];B[ql];W[rl];B[rk];W[rm];B[rj];W[pj];B[qi];W[sk];B[ri];W[pd];B[lk];W[oj];B[gj];W[gi];B[ik];W[hk];B[fh];W[gh])\\n\";";
        SGFnode tree = new SGFnode();
        tree.fromString(test);
        StringBuffer sb = new StringBuffer();
     *//*   tree.print_tree(sb, tree);
        System.out.println(sb.toString());*//*

        Treewalker walker = new Treewalker(tree);

       // System.out.print(walker.getFirstMove().toString());
      //  walker.getNextState();
       System.out.print(walker.printCurrent());
        walker.getNextState();
        System.out.print(walker.printCurrent());
        walker.getNextState();
        System.out.print(walker.printCurrent());
        walker.getNextState();
        System.out.print(walker.printCurrent());
        walker.getNextState();
        System.out.print(walker.printCurrent());
        walker.getPreviousMove();
        System.out.print(walker.printCurrent());
    }

*/


}


//////////////////////////////////////////////////////////
// Don't think, just hit `y':
//
// local variables:
// eval: (load-file "bkgomagic.el")
// end:
//////////////////////////////////////////////////////////