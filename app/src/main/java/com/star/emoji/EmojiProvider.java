package com.star.emoji;


import com.star.emoji.model.EmojiCategory;

import androidx.annotation.NonNull;


public interface EmojiProvider {

    @NonNull
    EmojiCategory[] getCategories();

}
