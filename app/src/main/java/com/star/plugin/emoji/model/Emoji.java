package com.star.plugin.emoji.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

public class Emoji implements Serializable {

    private int resource;   //表情资源ID
    private String code;    //表情字符串

    public Emoji(@NonNull String code, @DrawableRes int resource) {
        this.code = code;
        this.resource = resource;
    }

    public String getCode() {
        return code;
    }

    @NonNull
    public Drawable getDrawable(Context context) {
        return AppCompatResources.getDrawable(context, resource);
    }

    public int getLength() {
        return code.length();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }
        final Emoji emoji = (Emoji) o;
        return resource == emoji.resource && code.equals(emoji.code);
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + resource;
        return result;
    }
}
