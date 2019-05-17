package com.star.emoji.view.picker;

import android.view.View;
import android.view.ViewGroup;

import com.star.emoji.EmojiManager;
import com.star.emoji.model.RecentEmoji;
import com.star.emoji.view.picker.listener.OnEmojiClickListener;

import androidx.viewpager.widget.PagerAdapter;

public final class EmojiPagerAdapter extends PagerAdapter {
    private static final int RECENT_POSITION = 0;

    private final OnEmojiClickListener listener;
    private final RecentEmoji recentEmoji;

    private RecentEmojiGridView recentEmojiGridView;

    EmojiPagerAdapter(final OnEmojiClickListener listener,
                      final RecentEmoji recentEmoji) {
        this.listener = listener;
        this.recentEmoji = recentEmoji;
        this.recentEmojiGridView = null;
    }

    @Override
    public int getCount() {
        return EmojiManager.getInstance().getCategories().length + 1;
    }

    @Override
    public Object instantiateItem(final ViewGroup pager, final int position) {
        final View newView;

        if (position == RECENT_POSITION) {
            newView = new RecentEmojiGridView(pager.getContext()).init(listener, recentEmoji);
            recentEmojiGridView = (RecentEmojiGridView) newView;
        } else {
            newView = new EmojiGridView(pager.getContext()).init(listener,
                    EmojiManager.getInstance().getCategories()[position - 1]);
        }
        pager.addView(newView);
        return newView;
    }

    @Override
    public void destroyItem(final ViewGroup pager, final int position, final Object view) {
        pager.removeView((View) view);

        if (position == RECENT_POSITION) {
            recentEmojiGridView = null;
        }
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    int numberOfRecentEmojis() {
        return recentEmoji.getRecentEmojis().size();
    }

    void invalidateRecentEmojis() {
        if (recentEmojiGridView != null) {
            recentEmojiGridView.invalidateEmojis();
        }
    }
}
