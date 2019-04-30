package com.star.plugin.emoji;


import com.star.plugin.emoji.model.EmojiCategory;

import androidx.annotation.NonNull;


public interface EmojiProvider {

    @NonNull
    EmojiCategory[] getCategories();

}
