package com.star.emoji.view.picker;

import android.content.Context;


import com.star.emoji.model.Emoji;
import com.star.emoji.model.RecentEmoji;
import com.star.emoji.view.picker.listener.OnEmojiClickListener;

import java.util.Collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

final class RecentEmojiGridView extends EmojiGridView {

    private RecentEmoji recentEmojis;

    RecentEmojiGridView(@NonNull final Context context) {
        super(context);
    }

    public RecentEmojiGridView init(@Nullable final OnEmojiClickListener onEmojiClickListener,
                                    @NonNull final RecentEmoji recentEmoji) {
        this.recentEmojis = recentEmoji;
        Collection<Emoji> emojis = recentEmojis.getRecentEmojis();
        adapter = new EmojiArrayAdapter(getContext(), emojis.toArray(new Emoji[0]), onEmojiClickListener);
        setAdapter(adapter);
        return this;
    }

    public void invalidateEmojis() {
        adapter.updateEmojis(recentEmojis.getRecentEmojis());
    }
}
