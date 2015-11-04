package com.animation.quickmenu.roulettegame;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;

import com.animation.quickmenu.roulettegame.roulette.RouletteAnim;

public class RouletteActivity extends Activity {

    private RouletteAnim rouletteAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);

        rouletteAnim = (RouletteAnim) findViewById(R.id.roulette_Image);
        rouletteAnim.setWheelImage(setImage());
        rouletteAnim.setWheelAnimListener(new RouletteAnim.RouletteChangeListener() {
            @Override
            public void onSelectionChange(int selectedPosition) {
                Log.d("테스트", "position[테스트] : " + selectedPosition);
            }
        });
    }

    /**
     * 룰렛 이미지 생성 함수
     * (WEB에 설정된 구간 값을 설정한다)
     * @return Bitmap
     */
    public Bitmap setImage() {
        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(bitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        RectF rectF1 = new RectF(0, 0, 200, 200);
        paint.setColor(Color.BLACK);
        canvas.drawArc(rectF1, 0, 120, true, paint);

        RectF rectF2 = new RectF(0, 0, 200, 200);
        paint.setColor(Color.BLUE);
        canvas.drawArc(rectF2, 120, 190, true, paint);

        RectF rectF3 = new RectF(0, 0, 200, 200);
        paint.setColor(Color.CYAN);
        canvas.drawArc(rectF3, 310, 50, true, paint);

        return bitmap;
    }
}
