package nl.porsius.enhancedreality;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import nl.porsius.enhancedreality.R;

/**
 * Created by linda on 07/11/14.
 */
public class CustomImageView extends ImageView {
    Context context;
    int initW;
    int initH;
    int initPos = 0;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;
    }

    public void onMeasure(int width, int height)
    {
        super.onMeasure(width, height);

        setMeasuredDimension(width, height);

    }
    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        int w = canvas.getWidth();
        int h = canvas.getHeight();

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.nachtwacht);
        int bitmapW = bitmap.getScaledWidth(canvas);
        int bitmapW1 = bitmap.getWidth();
        System.out.println(" bitmapW "+bitmapW + "  bitmapW1 "+bitmapW1);
        int x = w / bitmapW ;
        canvas.drawBitmap(bitmap, w/2 , h/2, null);


    }

    public void setInitPos(int pos)
    {
        initPos = initPos + pos;
    }
}
