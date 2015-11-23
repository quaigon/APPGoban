package com.quaigon.kamil.goban;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.List;


public class GobanView extends View {
    private static final float marg = 50;
    private Context context;
    private Goban gobanModel;
    private DisplayMetrics dm;
    private Paint paint;

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

    public void init () {
        this.dm = new DisplayMetrics();
        this.paint = new Paint();
    }

    public void setGobanModel(Goban gobanModel) {
        this.gobanModel = gobanModel;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension( widthMeasureSpec, widthMeasureSpec )        ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;

        paint.getStrokeWidth();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor( "#ffb732" ));
        canvas.drawPaint(paint);

        paint.setStrokeWidth(0);
        float screenWidth = getWidth();
        float squareWidth = ((screenWidth - 2 * marg) / 18) + paint.getStrokeWidth();
        float squareHeight = squareWidth;


        paint.setColor(Color.BLACK);

        for (int i = 1; i <= 19; i++) {
            canvas.drawLine(marg, i * squareHeight, screenWidth - marg, i * squareHeight, paint);
        }


        for (int i = 0; i < 19; i++) {
            canvas.drawLine(marg + i * squareWidth, squareHeight, marg + squareWidth * i, 19 * squareHeight, paint);
        }


        List<Field> fields = gobanModel.getNonEmptyFields();
        float radius = squareWidth / 2;

        if (radius % 2 != 0) {
            radius = radius -1;
        }
        int count = 1;
        for (Field field : fields) {
            count++;
            int x = field.getX()-1;
            int y = field.getY()-1;

            if (field.getStone().getColor() == 0) {
                paint.setColor(Color.WHITE);
            } else {
                paint.setColor(Color.BLACK);
            }

            float newx = marg + x * (squareWidth);
            float newy = marg + y * (squareHeight);

            canvas.drawCircle(newx, newy, radius, paint);

//            if (fields.size() == count) {
//                paint.setColor(Color.YELLOW);
//                canvas.drawCircle(newx, newy, radius-20, paint);
//            }
        }
    }
}
