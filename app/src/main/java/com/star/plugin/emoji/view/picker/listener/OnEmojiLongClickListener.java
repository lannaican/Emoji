package com.star.plugin.emoji.view.picker.listener;


import com.star.plugin.emoji.model.Emoji;
import com.star.plugin.emoji.view.picker.EmojiImageView;

import androidx.annotation.NonNull;

public interface OnEmojiLongClickListener {
    void onLongClick(@NonNull EmojiImageView view, @NonNull Emoji emoji);
}
