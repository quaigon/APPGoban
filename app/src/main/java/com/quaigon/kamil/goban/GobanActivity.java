package com.quaigon.kamil.goban;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.quaigon.kamil.sgfparser.FileSGFProvider;
import com.quaigon.kamil.sgfparser.StringSGFProvider;

import java.io.File;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

public class GobanActivity extends RoboActionBarActivity {

    @InjectExtra(value = "sgf", optional = true)
    private String gameSgf = "(;DT[2010-10-13]EV[15th Samsung Cup]\n" +
            "PB[Choi Cheolhan]BR[9p]PW[Park Jungwhan]WR[8p]\n" +
            "KM[6.5]RE[W+R]SO[Go4Go.net]\n" +
            ";B[pd];W[dd];B[qp];W[dp];B[fq];W[cn];B[nq];W[qj];B[fc];W[qf];B[qh];W[of];B[mc];W[rd];B[qc];W[pi];B[cf];W[fd];B[gd];W[fe];B[ge];W[ec];B[gc];W[ff];B[cc];W[cd];B[bd];W[fb];B[bb];W[gb];B[ic];W[gf];B[ie];W[eq];B[fp];W[ip];B[en];W[er];B[kq];W[qm];B[db];W[eb];B[dl];W[jo];B[pn];W[pm];B[om];W[lp];B[pk];W[qk];B[kp];W[ko];B[lo];W[on];B[ln];W[nn];B[po];W[be];B[ce];W[ad];B[bc];W[cg];B[dg];W[df];B[bg];W[ch];B[bf];W[mp];B[np];W[mo];B[mn];W[lm];B[mm];W[nm];B[ml];W[no];B[mr];W[jm];B[jk];W[nk];B[mk];W[hl];B[co];W[do];B[dn];W[cm];B[bo];W[cp];B[cl];W[bn];B[dm];W[bl];B[an];W[bm];B[bk];W[bp];B[dh];W[ij];B[nl];W[ol];B[nj];W[ki];B[ok];W[mi];B[ni];W[ph];B[mh];W[fi];B[ei];W[jd];B[id];W[lh];B[mg];W[kf];B[ig];W[md];B[le];W[lf];B[mf];W[ld];B[ke];W[me];B[je];W[nh];B[oh];W[ng];B[nf];W[og];B[ne];W[hi];B[jh];W[jj];B[qe];W[pe];B[pl];W[om];B[qg];W[oe];B[nd];W[oi];B[kk];W[km];B[rf];W[od];B[oc];W[qd];B[pf];W[pc];B[ql];W[rl];B[rk];W[rm];B[rj];W[pj];B[qi];W[sk];B[ri];W[pd];B[lk];W[oj];B[gj];W[gi];B[ik];W[hk];B[fh];W[gh])\n";


    private static final int FILE_SELECT_CODE = 10;
    private Goban goban;
    private GameManager gameManager;

    @InjectView(R.id.moveNo)
    private TextView moveNoView;

    @InjectView(R.id.goban_view)
    private GobanView gobanView;

    @InjectView(R.id.prev)
    private Button buttonPrev;

    @InjectView(R.id.next)
    private Button buttonNext;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = data.getData();
        loadURI(uri.getPath());
    }

    private void loadURI(String uri) {
        File file = new File(uri);
        FileSGFProvider provider = new FileSGFProvider(file);
        this.gameManager = new GameManager(provider);
        goban = new Goban();
        gobanView.setGobanModel(goban);
        gobanView.invalidate();
        buttonNext.setEnabled(true);
        buttonPrev.setEnabled(true);
    }

    private void refreshView() {
        gobanView.invalidate();
        if (gameManager != null) {
            moveNoView.setText(String.valueOf(gameManager.getMoveNo()));
        }
    }

    private void loadSgf(String sgf) {
        StringSGFProvider provider = new StringSGFProvider(sgf);
        this.gameManager = new GameManager(provider);
        goban = new Goban();
        gobanView.setGobanModel(goban);
        gobanView.invalidate();
        buttonNext.setEnabled(true);
        buttonPrev.setEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goban_view_layout);


        buttonNext.setEnabled(false);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPrev.setEnabled(true);
                Goban goban = gameManager.getNextState();
                if (goban == null){
                    buttonNext.setEnabled(false);
                    return;
                }
                    gobanView.setGobanModel(goban);
                    refreshView();


            }
        });


        buttonPrev.setEnabled(false);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNext.setEnabled(true);
                if (gameManager.getMoveNo() == 0) {
                    buttonPrev.setEnabled(false);
                    return;
                }
                Goban goban = gameManager.getPreviousState();
                gobanView.setGobanModel(goban);
                refreshView();
            }
        });


        final Button buttonLoadSGF = (Button) findViewById(R.id.loadsgf);
        buttonLoadSGF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("file/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Wybierz SGF"), 1000);
                refreshView();

            }
        });

        loadSgf(this.gameSgf);
        gobanView.setGobanModel(goban);
        refreshView();
    }

}
