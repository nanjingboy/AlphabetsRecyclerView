package me.tom.fastscrollrecyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FastScrollRecyclerViewSliderBar extends View {

    public static final int MAX_INDEX_COUNT = 28;

    protected Paint mPaint;

    protected int mHeight;
    protected int mWidth;

    protected int mTextColor;
    protected int mSelectedTextColor;

    protected Drawable mBackground;
    protected Drawable mSelectBackground;

    protected IIndexChangedListener mListener;

    protected int mSelectedIndex = -1;
    protected List<String> mIndexTitles = new ArrayList<>();

    public interface IIndexChangedListener {
        void onIndexChanged(int position, String indexTitle);
    }

    public FastScrollRecyclerViewSliderBar(Context context) {
        this(context, null);
    }

    public FastScrollRecyclerViewSliderBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastScrollRecyclerViewSliderBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTextColor = ContextCompat.getColor(context, R.color.colorFastScrollRecyclerViewSlideBarText);
        mSelectedTextColor = ContextCompat.getColor(context, R.color.colorFastScrollRecyclerViewSlideBarSelectedText);
        mBackground = getBackground();
        mSelectBackground = ContextCompat.getDrawable(context, R.color.colorFastScrollRecyclerViewOverlayBackground);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.fastScrollRecyclerViewSliderBarFontSize));
        mPaint.setAntiAlias(true);
        setClickable(true);
    }

    public void setIndexTitles(List<String> indexTitles) {
        if (indexTitles == null) {
            mIndexTitles = new ArrayList<>();
        } else {
            mIndexTitles = indexTitles;
        }
        invalidate();
    }

    public void setListener(IIndexChangedListener listener) {
        mListener = listener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float y = event.getY();
        int currentSelectedIndex = mSelectedIndex;
        if (y < getPaddingTop() || y > mHeight + getPaddingTop()) {
            mSelectedIndex = -1;
        } else {
            mSelectedIndex = (int) ((y - getPaddingTop()) / mHeight * MAX_INDEX_COUNT);
            if (mSelectedIndex >= mIndexTitles.size() - 1) {
                mSelectedIndex = mIndexTitles.size() - 1;
            }
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                mSelectedIndex = -1;
                setBackground(mBackground);
            default:
                if (currentSelectedIndex != mSelectedIndex && mSelectedIndex != -1) {
                    setBackground(mSelectBackground);
                }
        }
        if (mListener != null) {
            if (mSelectedIndex == -1) {
                mListener.onIndexChanged(-1, null);
            } else {
                mListener.onIndexChanged(mSelectedIndex, mIndexTitles.get(mSelectedIndex));
            }
        }
        invalidate();
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        mWidth = width;
        mHeight = height - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int index = 0; index < mIndexTitles.size(); index++) {
            String indexTitle = mIndexTitles.get(index);
            float indexTitleWidth = mPaint.measureText(indexTitle);
            if (index == mSelectedIndex) {
                mPaint.setColor(mSelectedTextColor);
            } else {
                mPaint.setColor(mTextColor);
            }
            canvas.drawText(
                    indexTitle,
                    (mWidth - indexTitleWidth) / 2,
                    mHeight / MAX_INDEX_COUNT * (index + 1) + getPaddingTop(),
                    mPaint
            );
        }
        super.onDraw(canvas);
    }
}
