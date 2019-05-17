package com.star.emoji.view.picker.listener;


import com.star.emoji.model.Emoji;
import com.star.emoji.view.picker.EmojiImageView;

import androidx.annotation.NonNull;

public interface OnEmojiClickListener {
    void onClick(@NonNull EmojiImageView view, @NonNull Emoji emoji);
}
