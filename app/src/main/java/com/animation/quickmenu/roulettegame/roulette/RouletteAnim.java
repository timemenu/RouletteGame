package com.animation.quickmenu.roulettegame.roulette;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import static android.view.animation.Animation.*;

/**
 * Created by quickmenu on 2015-10-25.
 */
public class RouletteAnim extends ImageView {

    private Bitmap imageOriginal, imageScaled;
    private Matrix matrix;
    private int wheelHeight, wheelWidth;
    private int selectedPosition;
    private boolean snapToCenterFlag = true;
    private Context context;
    private RouletteChangeListener rouletteChangeListener;

    public RouletteAnim(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.setScaleType(ScaleType.MATRIX);
        selectedPosition = 0;

        // initialize the matrix only once
        if (matrix == null) {
            matrix = new Matrix();
        } else {
            matrix.reset();
        }

        //touch events listener
        //this.setOnTouchListener(new WheelTouchListener());
        //this.setAnimationListenr(new WheelChangeListener());
        // 1. 랜덤함수로 각도 구간을 WEB API로 생성한다.
        // 2. 화면상의 각도를 저장하면 해당 구간에 자연스럽게 멈추도록 애니메이션을 셋팅한다.
        // 3. 서서히 멈추는 애니메이션 셋팅한다.
        RotateAnimation rotate = new RotateAnimation(0, 7200, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setInterpolator(new DecelerateInterpolator());
        rotate.setDuration(5000);
        //rotate.setRepeatMode(Animation.RESTART);
        //rotate.setRepeatCount(Animation.INFINITE);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setAnimationListener(new RouletteAnimListener());
        this.startAnimation(rotate);
    }

    public void setWheelImage(Bitmap bitmap) {
        imageOriginal = bitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (wheelHeight == 0 || wheelWidth == 0) {
            wheelHeight = h;
            wheelWidth = w;
            // resize the image
            Matrix resize = new Matrix();
            resize.postScale((float) Math.min(wheelWidth, wheelHeight) / (float) imageOriginal.getWidth(), (float) Math.min(wheelWidth, wheelHeight) / (float) imageOriginal.getHeight());
            imageScaled = Bitmap.createBitmap(imageOriginal, 0, 0, imageOriginal.getWidth(), imageOriginal.getHeight(), resize, false);
            // translate the matrix to the image view's center
            float translateX = wheelWidth / 2 - imageScaled.getWidth() / 2;
            float translateY = wheelHeight / 2 - imageScaled.getHeight() / 2;
            matrix.postTranslate(translateX, translateY);
            this.setImageBitmap(imageScaled);
            this.setImageMatrix(matrix);
        }
    }

    public void setWheelAnimListener(RouletteChangeListener rouletteChangeListener) {
        this.rouletteChangeListener = rouletteChangeListener;
    }

    public interface RouletteChangeListener {
        public void onSelectionChange(int selectedPosition);
    }

    private class RouletteAnimListener implements AnimationListener {
        private int degree = -1;

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO 값을 계산(랜덤함수 생성)
            // 웹 API 구간 카운트 제거[성공, 실패]
            degree = 60;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO 결과 값이 출력되면 새로운 기존 애니메이션 stop, 천천히 멈추는 애니메이션 추가
            Log.d("RouletteAnim", "onAnimationEnd[테스트]");
            if (rouletteChangeListener != null) {
                rouletteChangeListener.onSelectionChange(degree);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
