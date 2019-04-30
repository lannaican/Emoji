package com.star.plugin.emoji.view.picker;

import android.content.Context;
import android.widget.GridView;

import com.star.plugin.emoji.R;
import com.star.plugin.emoji.model.EmojiCategory;
import com.star.plugin.emoji.view.picker.listener.OnEmojiClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EmojiGridView extends GridView {

    protected EmojiArrayAdapter adapter;

    public EmojiGridView(final Context context) {
        super(context);

        final int width = context.getResources().getDimensionPixelSize(R.dimen.emoji_image_size);
        final int spacing = context.getResources().getDimensionPixelSize(R.dimen.emoji_image_space);

        setColumnWidth(width);
        setHorizontalSpacing(spacing);
        setVerticalSpacing(spacing);
        setPadding(spacing, spacing, spacing, spacing);
        setNumColumns(7);
        setClipToPadding(false);
        setVerticalScrollBarEnabled(false);
    }

    public EmojiGridView init(@Nullable final OnEmojiClickListener onEmojiClickListener,
                              @NonNull final EmojiCategory category) {
        adapter = new EmojiArrayAdapter(getContext(), category.getEmojiList(), onEmojiClickListener);
        setAdapter(adapter);
        return this;
    }
}
