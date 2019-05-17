package com.star.emoji.view.picker;

import android.content.Context;
import android.util.AttributeSet;

import com.star.emoji.model.Emoji;
import com.star.emoji.view.picker.listener.OnEmojiClickListener;
import com.star.emoji.view.picker.listener.OnEmojiLongClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public final class EmojiImageView extends AppCompatImageView {

    private Emoji emoji;

    private OnEmojiClickListener clickListener;
    private OnEmojiLongClickListener longClickListener;

    private ImageLoadingTask imageLoadingTask;

    public EmojiImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int measuredWidth = getMeasuredWidth();
        //noinspection SuspiciousNameCombination
        setMeasuredDimension(measuredWidth, measuredWidth);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (imageLoadingTask != null) {
            imageLoadingTask.cancel(true);
            imageLoadingTask = null;
        }
    }

    public void setEmoji(@NonNull final Emoji emoji) {
        if (!emoji.equals(this.emoji)) {
            setImageDrawable(null);

            this.emoji = emoji;

            if (imageLoadingTask != null) {
                imageLoadingTask.cancel(true);
            }

            setOnClickListener(view -> {
                if (clickListener != null) {
                    clickListener.onClick(EmojiImageView.this, this.emoji);
                }
            });

            imageLoadingTask = new ImageLoadingTask(this);
            imageLoadingTask.execute(emoji);
        }
    }

    /**
     * Updates the emoji image directly. This should be called only for updating the variant
     * displayed (of the same base emoji), since it does not run asynchronously and does not update
     * the internal listeners.
     *
     * @param emoji The new emoji variant to show.
     */
    public void updateEmoji(@NonNull final Emoji emoji) {
        if (!emoji.equals(this.emoji)) {
            this.emoji = emoji;
            setImageDrawable(emoji.getDrawable(this.getContext()));
        }
    }

    public void setOnEmojiClickListener(@Nullable final OnEmojiClickListener listener) {
        this.clickListener = listener;
    }

    public void setOnEmojiLongClickListener(@Nullable final OnEmojiLongClickListener listener) {
        this.longClickListener = listener;
    }
}
