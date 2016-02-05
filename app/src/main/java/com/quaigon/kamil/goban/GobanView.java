package com.quaigon.kamil.goban;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;


public class GobanView extends View {
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

        paint.getStrokeWidth();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor( "#ffb732" ));
        canvas.drawPaint(paint);

        paint.setStrokeWidth(0);
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
            canvas.drawLine( (1 + i )* squareWidth, squareHeight,(1 + i )* squareWidth , 19 * squareHeight, paint);
        }

        if (gobanModel != null) {

        List<Field> fields = gobanModel.getNonEmptyFields();


        if (radius % 2 != 0) {
            radius = radius -1;
        }
        for (Field field : fields) {
            int x = field.getX() - 1;
            int y = field.getY() - 1;

            if (field.getStone().getColor() == 0) {
                paint.setColor(Color.WHITE);
            } else {
                paint.setColor(Color.BLACK);
            }

            float newx = squareWidth + x * (squareWidth);
            float newy = squareWidth + y * (squareHeight);

            canvas.drawCircle(newx, newy, radius, paint);

        }

        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        Log.d("Goban", " x: "+x + "y: "+y);
        return super.onTouchEvent(event);
    }
}
