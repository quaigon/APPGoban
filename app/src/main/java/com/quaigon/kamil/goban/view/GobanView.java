package com.quaigon.kamil.goban.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.quaigon.kamil.goban.Field;
import com.quaigon.kamil.goban.Goban;
import com.quaigon.kamil.goban.PositionCalculator;

import java.util.List;

import roboguice.util.Ln;


public class GobanView extends View {
    boolean isBlack = true;
    private Context context;
    private Goban gobanModel;
    private DisplayMetrics dm;
    private Paint paint;
    private PositionCalculator positionCalculator;

    public GobanView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public GobanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public GobanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init() {
        this.dm = new DisplayMetrics();
        this.paint = new Paint();
    }

    public void setGobanModel(Goban gobanModel) {
        this.gobanModel = gobanModel;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.getStrokeWidth();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#ffb732"));
        canvas.drawPaint(paint);
        paint.setStrokeWidth(0);
        this.positionCalculator = new PositionCalculator(getWidth());
        float screenWidth = getWidth();
        float squareWidth = screenWidth / 20 + paint.getStrokeWidth();
        float radius = squareWidth / 2;
        squareWidth = Math.round(squareWidth);
        float squareHeight = squareWidth;

        paint.setColor(Color.BLACK);

        for (int i = 1; i <= 19; i++) {
            canvas.drawLine(squareWidth, i * squareHeight, screenWidth - squareWidth, i * squareHeight, paint);
        }


        for (int i = 0; i < 19; i++) {
            canvas.drawLine((1 + i) * squareWidth, squareHeight, (1 + i) * squareWidth, 19 * squareHeight, paint);
        }

        if (gobanModel != null) {
            List<Field> fields = gobanModel.getNonEmptyFields();

            if (radius % 2 != 0) {
                radius = radius - 1;
            }
            for (Field field : fields) {
                int x = field.getX() - 1;
                int y = field.getY() - 1;

                if (field.getStone().getColor() == 0) {
                    paint.setColor(Color.WHITE);
                } else {
                    paint.setColor(Color.BLACK);
                }

                canvas.drawCircle(positionCalculator.xToPixel(x), positionCalculator.yToPixel(y), radius, paint);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {



        float x = event.getX();
        float y = event.getY();

        Ln.d( "ACTION: " + event.getActionMasked());
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            int newx = positionCalculator.fromPixelToX(x);
            int newy = positionCalculator.fromPixelToX(y);

            if (isBlack) {
                Goban nextState = new Goban(gobanModel);
                nextState.putStone(newx, newy, 1);
                if (!nextState.isEmpty(newx, newy)) {
                    isBlack = false;
                    gobanModel = nextState;
                    invalidate();
                }
            } else {
                Goban nextState = new Goban(gobanModel);
                nextState.putStone(newx, newy, 0);
                if (!nextState.isEmpty(newx, newy)) {
                    gobanModel = nextState;
                    isBlack = true;
                    invalidate();
                }
            }
        }

        return super.onTouchEvent(event);
    }


}
