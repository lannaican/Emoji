package com.star.plugin.emoji.model;

import androidx.annotation.NonNull;

public interface EmojiCategory {

    @NonNull
    Emoji[] getEmojiList();

    String getTitle();

}
