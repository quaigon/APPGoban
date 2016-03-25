package com.quaigon.kamil.goban.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.quaigon.kamil.goban.R;
import com.quaigon.kamil.goban.gametree.GameTreeManagerImpl;
import com.quaigon.kamil.goban.gametree.SGFGameTreeGenerator;
import com.quaigon.kamil.goban.gobaninterface.CommentaryInterface;
import com.quaigon.kamil.goban.gobaninterface.GobanInterface;
import com.quaigon.kamil.goban.gobaninterface.GobanInterfaceImpl;
import com.quaigon.kamil.goban.view.GobanView;
import com.quaigon.kamil.sgfparser.FileSGFProvider;
import com.quaigon.kamil.sgfparser.SGFProvider;
import com.quaigon.kamil.sgfparser.StringSGFProvider;

import java.io.File;
import java.util.HashSet;

import roboguice.activity.RoboActionBarActivity;
import roboguice.adapter.IterableAdapter;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

public class GobanActivity extends RoboActionBarActivity {

    private static final int FILE_SELECT_CODE = 10;
    //    @InjectExtra(value = "sgf", optional = true)
//    private String gameSgf = "(;DT[2010-10-13]EV[15th Samsung Cup]\n" +
//            "PB[Choi Cheolhan]BR[9p]PW[Park Jungwhan]WR[8p]\n" +
//            "KM[6.5]RE[W+R]SO[Go4Go.net]\n" +
//            ";B[pd];W[dd];B[qp];W[dp];B[fq];W[cn];B[nq];W[qj];B[fc];W[qf];B[qh];W[of];B[mc];W[rd];B[qc];W[pi];B[cf];W[fd];B[gd];W[fe];B[ge];W[ec];B[gc];W[ff];B[cc];W[cd];B[bd];W[fb];B[bb];W[gb];B[ic];W[gf];B[ie];W[eq];B[fp];W[ip];B[en];W[er];B[kq];W[qm];B[db];W[eb];B[dl];W[jo];B[pn];W[pm];B[om];W[lp];B[pk];W[qk];B[kp];W[ko];B[lo];W[on];B[ln];W[nn];B[po];W[be];B[ce];W[ad];B[bc];W[cg];B[dg];W[df];B[bg];W[ch];B[bf];W[mp];B[np];W[mo];B[mn];W[lm];B[mm];W[nm];B[ml];W[no];B[mr];W[jm];B[jk];W[nk];B[mk];W[hl];B[co];W[do];B[dn];W[cm];B[bo];W[cp];B[cl];W[bn];B[dm];W[bl];B[an];W[bm];B[bk];W[bp];B[dh];W[ij];B[nl];W[ol];B[nj];W[ki];B[ok];W[mi];B[ni];W[ph];B[mh];W[fi];B[ei];W[jd];B[id];W[lh];B[mg];W[kf];B[ig];W[md];B[le];W[lf];B[mf];W[ld];B[ke];W[me];B[je];W[nh];B[oh];W[ng];B[nf];W[og];B[ne];W[hi];B[jh];W[jj];B[qe];W[pe];B[pl];W[om];B[qg];W[oe];B[nd];W[oi];B[kk];W[km];B[rf];W[od];B[oc];W[qd];B[pf];W[pc];B[ql];W[rl];B[rk];W[rm];B[rj];W[pj];B[qi];W[sk];B[ri];W[pd];B[lk];W[oj];B[gj];W[gi];B[ik];W[hk];B[fh];W[gh])\n";
    @InjectExtra(value = "sgf", optional = true)
    private String gameSgf = null;

    private GobanInterface gobanInterface;

    private CommentaryInterface commentaryInterface;

    @InjectView(R.id.moveNo)
    private TextView moveNoView;

    @InjectView(R.id.goban_view)
    private GobanView gobanView;

    @InjectView(R.id.prev)
    private Button buttonPrev;

    @InjectView(R.id.next)
    private Button buttonNext;

    @InjectExtra(value = "sgfPath", optional = true)
    private String sgfPath;

    private HashSet<String> messagesList;
    private IterableAdapter<String> messageAdapter;

    @InjectView(R.id.android_messagesList)
    private ListView messagesListView;

    private void loadURI(String uri) {
        File file = new File(uri);
        initGobanInterface(new FileSGFProvider(file));
        buttonNext.setEnabled(true);
    }

    private void loadSgf(String sgf) {
        initGobanInterface(new StringSGFProvider(sgf));
        buttonNext.setEnabled(true);
    }

    private void initGobanInterface(SGFProvider sgfProvider) {
        SGFGameTreeGenerator sgfGameTreeGenerator = new SGFGameTreeGenerator(sgfProvider);
        GameTreeManagerImpl gameTreeManagerImpl = new GameTreeManagerImpl(sgfGameTreeGenerator.getTree());
        GobanInterfaceImpl gobanInterfaceImpl = new GobanInterfaceImpl(gameTreeManagerImpl, gobanView, moveNoView);
        this.gobanInterface = gobanInterfaceImpl;
        this.commentaryInterface = gameTreeManagerImpl;
    }

    private int getMoveNo() {
        return Integer.parseInt(moveNoView.getText().toString());
    }

    private void refreshCommentariesView() {
        this.messageAdapter = new IterableAdapter<>(this, android.R.layout.simple_list_item_1, messagesList);
        this.messagesListView.setAdapter(this.messageAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goban_view_layout);

        if (this.sgfPath != null) loadURI(sgfPath);

        buttonNext.setEnabled(false);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPrev.setEnabled(true);
                if (commentaryInterface.getComment() != null) {
                    messagesList.add(commentaryInterface.getComment());
                    refreshCommentariesView();
                }
                gobanInterface.nextState();
            }
        });

        buttonPrev.setEnabled(false);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNext.setEnabled(true);
                gobanInterface.prevState();
                if (getMoveNo() == 0) {
                    buttonPrev.setEnabled(false);
                }
                if (commentaryInterface.getComment() != null) {
                    messagesList.remove(commentaryInterface.getComment());
                    refreshCommentariesView();
                }
            }
        });

        this.messagesList = new HashSet<>();

        if (sgfPath != null) {
            loadURI(sgfPath);
        } else if (gameSgf != null) {
            loadSgf(this.gameSgf);
        } else {
            GobanInterfaceImpl gobanInterfaceImpl = new GobanInterfaceImpl(new GameTreeManagerImpl(), gobanView, moveNoView);
            gobanInterface = gobanInterfaceImpl;
            gobanView.setTouchListener(gobanInterfaceImpl);
        }
    }
}