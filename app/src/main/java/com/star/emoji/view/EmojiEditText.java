package com.star.emoji.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.star.emoji.EmojiManager;
import com.star.emoji.model.Emoji;
import com.star.emoji.view.picker.EditInterface;

import androidx.annotation.CallSuper;
import androidx.appcompat.widget.AppCompatEditText;

public class EmojiEditText extends AppCompatEditText implements EditInterface {

    private float emojiSize;

    public EmojiEditText(final Context context) {
        this(context, null);
    }

    public EmojiEditText(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        emojiSize = fontMetrics.descent - fontMetrics.ascent;
        setText(getText());
    }

    @Override
    @CallSuper
    protected void onTextChanged(final CharSequence text, final int start, final int lengthBefore, final int lengthAfter) {
        EmojiManager.getInstance().replace(getContext(), getText(), emojiSize);
    }

    /**
     * 回退表情
     */
    public void backspace() {
        final KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL,
                0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
        dispatchKeyEvent(event);
    }

    /**
     * 输入表情
     * @param emoji
     */
    public void input(Emoji emoji) {
        if (emoji != null) {
            final int start = getSelectionStart();
            final int end = getSelectionEnd();
            if (start < 0) {
                append(emoji.getCode());
            } else {
                getText().replace(Math.min(start, end), Math.max(start, end), emoji.getCode(), 0, emoji.getCode().length());
            }
        }
    }
}
