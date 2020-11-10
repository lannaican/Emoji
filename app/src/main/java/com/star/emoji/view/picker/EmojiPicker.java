package com.star.emoji.view.picker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.star.emoji.EmojiManager;
import com.star.emoji.R;
import com.star.emoji.RecentEmojiManager;
import com.star.emoji.model.EmojiCategory;
import com.star.emoji.model.RecentEmoji;

import androidx.viewpager.widget.ViewPager;

@SuppressLint("ViewConstructor")
public final class EmojiPicker extends LinearLayout implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;

    private TextView[] emojiTabs;
    private EmojiPagerAdapter emojiPagerAdapter;

    private View dividerView;

    private EditInterface editInterface;

    private RecentEmoji recentEmoji;

    private int tabIndex = -1;
    private int tabColor = 0xFF666666;
    private int tabSelectColor = 0xFF333333;

    private int tabLayoutId;

    public EmojiPicker(Context context) {
        this(context, null);
    }

    public EmojiPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EmojiPicker);
        int layoutId = array.getResourceId(R.styleable.EmojiPicker_emoji_layout, R.layout.emoji_view_emoji_picker);
        tabLayoutId = array.getResourceId(R.styleable.EmojiPicker_emoji_tab_layout, R.layout.emoji_view_emoji_category);
        array.recycle();
        LayoutInflater.from(context).inflate(layoutId, this, true);

        findViewById(R.id.back_image).setOnClickListener(v -> {
            editInterface.backspace();
        });

        viewPager = findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);

        dividerView = findViewById(R.id.divider_view);

        final EmojiCategory[] categories = EmojiManager.getInstance().getCategories();

        LinearLayout tabLayout = findViewById(R.id.tab_layout);
        emojiTabs = new TextView[categories.length + 1];
        emojiTabs[0] = inflateButton(getContext(), getResources().getString(R.string.emoji_recent), tabLayout);
        for (int i = 0; i < categories.length; i++) {
            emojiTabs[i + 1] = inflateButton(getContext(), categories[i].getTitle(), tabLayout);
        }
        handleOnClicks(viewPager);
    }

    /**
     * 设置Tab颜色
     */
    public void setTabColor(int color, int selectedColor) {
        this.tabColor = color;
        this.tabSelectColor = selectedColor;
        for (int i=0; i<emojiTabs.length; i++) {
            emojiTabs[i].setTextColor(i == tabIndex ? tabSelectColor : tabColor);
        }
    }

    /**
     * 设置分割线颜色
     */
    public void setDividerColor(int color) {
        dividerView.setBackgroundColor(color);
    }

    @Override
    public void onPageSelected(final int i) {
        if (tabIndex != i) {
            if (i == 0) {
                emojiPagerAdapter.invalidateRecentEmojis();
            }
            if (tabIndex >= 0 && tabIndex < emojiTabs.length) {
                emojiTabs[tabIndex].setTextColor(tabColor);
            }
            emojiTabs[i].setTextColor(tabSelectColor);
            tabIndex = i;
        }
    }

    @Override
    public void onPageScrolled(final int i, final float v, final int i2) {
        // No-op.
    }

    @Override
    public void onPageScrollStateChanged(final int i) {
        // No-op.
    }

    public void setEditInterface(EditInterface editInterface) {
        this.editInterface = editInterface;
        recentEmoji = new RecentEmojiManager(getContext());
        emojiPagerAdapter = new EmojiPagerAdapter((view, emoji) -> {
            editInterface.input(emoji);
            recentEmoji.addEmoji(emoji);
            view.updateEmoji(emoji);
        }, recentEmoji);
        viewPager.setAdapter(emojiPagerAdapter);
        int startIndex = emojiPagerAdapter.numberOfRecentEmojis() > 0 ? 0 : 1;
        viewPager.setCurrentItem(startIndex);
        onPageSelected(startIndex);
    }

    public void saveRecent() {
        recentEmoji.persist();
    }

    private void handleOnClicks(final ViewPager emojisPager) {
        for (int i = 0; i < emojiTabs.length; i++) {
            emojiTabs[i].setOnClickListener(new EmojiTabsClickListener(emojisPager, i));
        }
    }

    private TextView inflateButton(final Context context, String text, final ViewGroup parent) {
        TextView textView = (TextView) LayoutInflater.from(context).inflate(tabLayoutId, parent, false);
        textView.setText(text);
        parent.addView(textView);
        return textView;
    }

    static class EmojiTabsClickListener implements View.OnClickListener {
        private final ViewPager emojisPager;
        private final int position;

        EmojiTabsClickListener(final ViewPager emojisPager, final int position) {
            this.emojisPager = emojisPager;
            this.position = position;
        }

        @Override
        public void onClick(final View v) {
            emojisPager.setCurrentItem(position);
        }
    }
}
