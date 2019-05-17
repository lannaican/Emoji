package com.star.emoji.view.picker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.star.emoji.R;
import com.star.emoji.model.Emoji;
import com.star.emoji.view.picker.listener.OnEmojiClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EmojiArrayAdapter extends ArrayAdapter<Emoji> {

    @Nullable
    private final OnEmojiClickListener listener;

    public EmojiArrayAdapter(@NonNull final Context context, @NonNull final Emoji[] emojis,
                             @Nullable final OnEmojiClickListener listener) {
        super(context, 0, new ArrayList<>(Arrays.asList(emojis)));
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        EmojiImageView image = (EmojiImageView) convertView;

        final Context context = getContext();

        if (image == null) {
            image = (EmojiImageView) LayoutInflater.from(context).inflate(R.layout.emoji_item_emoji, parent, false);
            image.setOnEmojiClickListener(listener);
        }

        Emoji emoji = getItem(position);
        image.setEmoji(emoji);
        return image;
    }

    public void updateEmojis(final Collection<Emoji> emojis) {
        clear();
        addAll(emojis);
        notifyDataSetChanged();
    }
}
