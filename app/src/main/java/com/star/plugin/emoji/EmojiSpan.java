package com.star.plugin.emoji;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

import com.star.plugin.emoji.model.Emoji;

import androidx.annotation.NonNull;


public class EmojiSpan extends DynamicDrawableSpan {

    private Context context;
    private Emoji emoji;
    private float size;
    private Drawable drawable;

    public EmojiSpan(Context context, Emoji emoji, float size) {
        this.context = context;
        this.emoji = emoji;
        this.size = size;
    }

    @Override
    public Drawable getDrawable() {
        if (drawable == null) {
            drawable = emoji.getDrawable(context);
            drawable.setBounds(0, 0, (int) size, (int) size);
        }
        return drawable;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fontMetrics) {
        if (fontMetrics != null) {
            Paint.FontMetrics paintFontMetrics = paint.getFontMetrics();
            float fontHeight = paintFontMetrics.descent - paintFontMetrics.ascent;
            float centerY = paintFontMetrics.ascent + fontHeight / 2;

            fontMetrics.ascent = (int) (centerY - size / 2);
            fontMetrics.top = fontMetrics.ascent;
            fontMetrics.bottom = (int) (centerY + size / 2);
            fontMetrics.descent = fontMetrics.bottom;
        }
        return (int) size;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, @NonNull Paint paint) {
        Paint.FontMetrics paintFontMetrics = paint.getFontMetrics();
        float fontHeight = paintFontMetrics.descent - paintFontMetrics.ascent;
        float centerY = y + paintFontMetrics.descent - fontHeight / 2;
        float transitionY = centerY - size / 2;

        canvas.save();
        canvas.translate(x, transitionY);
        getDrawable().draw(canvas);
        canvas.restore();
    }
}
