package com.star.emoji.model;

import androidx.annotation.NonNull;

public class EmojiRange {

    private Emoji emoji;
    private int start;
    private int end;

    public EmojiRange(@NonNull Emoji emoji, int start, int end) {
        this.emoji = emoji;
        this.start = start;
        this.end = end;
    }

    public Emoji getEmoji() {
        return emoji;
    }

    public void setEmoji(Emoji emoji) {
        this.emoji = emoji;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final EmojiRange that = (EmojiRange) o;

        return start == that.start && end == that.end && emoji.equals(that.emoji);
    }

    @Override
    public int hashCode() {
        int result = start;
        result = 31 * result + end;
        result = 31 * result + emoji.hashCode();
        return result;
    }
}
