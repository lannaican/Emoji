package com.star.plugin.emoji.view;

import android.content.Context;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;


import com.star.plugin.emoji.EmojiManager;

import androidx.annotation.CallSuper;
import androidx.appcompat.widget.AppCompatTextView;

public class EmojiTextView extends AppCompatTextView {

    private float emojiSize;

    public EmojiTextView(Context context) {
        this(context, null);
    }

    public EmojiTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    @CallSuper
    public void setText(CharSequence text, BufferType type) {
        if (TextUtils.isEmpty(text)) {
            super.setText(text, type);
            return;
        }
        updateEmojiSize();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        EmojiManager.getInstance().replace(getContext(), spannableStringBuilder, emojiSize);
        super.setText(spannableStringBuilder, type);
    }

    private void updateEmojiSize() {
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        emojiSize = fontMetrics.descent - fontMetrics.ascent;
    }
}
