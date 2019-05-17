package com.star.emoji;

import android.content.Context;
import android.text.Spannable;
import android.text.TextUtils;

import com.star.emoji.model.Emoji;
import com.star.emoji.model.EmojiCategory;
import com.star.emoji.model.EmojiRange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class EmojiManager {

    private static final EmojiManager INSTANCE = new EmojiManager();

    private EmojiCategory[] categories;
    private final Map<String, Emoji> map = new LinkedHashMap<>(500);
    private Pattern pattern;
    private Pattern repetitivePattern;

    private EmojiManager() {}

    /**
     * 获取实例
     */
    public static EmojiManager getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     */
    public static void install(@NonNull EmojiProvider provider) {
        INSTANCE.categories = provider.getCategories();
        INSTANCE.map.clear();
        List<String> patterns = new ArrayList<>(500);

        int categoriesSize = INSTANCE.categories.length;
        for (int i = 0; i < categoriesSize; i++) {
            Emoji[] emojis = INSTANCE.categories[i].getEmojiList();
            int emojisSize = emojis.length;
            for (int j = 0; j < emojisSize; j++) {
                Emoji emoji = emojis[j];
                String unicode = emoji.getCode();
                INSTANCE.map.put(unicode, emoji);
                patterns.add(unicode);
            }
        }

        Collections.sort(patterns, (first, second) -> Integer.compare(second.length(), first.length()));

        StringBuilder patternBuilder = new StringBuilder(500);

        int patternSize = patterns.size();
        for (int i = 0; i < patternSize; i++) {
            patternBuilder.append(Pattern.quote(patterns.get(i))).append('|');
        }

        String regex = patternBuilder.deleteCharAt(patternBuilder.length() - 1).toString();
        INSTANCE.pattern = Pattern.compile(regex);
        INSTANCE.repetitivePattern = Pattern.compile('(' + regex + ")+");
    }

    /**
     * 字符串替换为表情
     */
    public void replace(Context context, Spannable text, float size) {
        EmojiSpan[] spans = text.getSpans(0, text.length(), EmojiSpan.class);
        List<Integer> spanPositions = new ArrayList<>(spans.length);

        for (int i = 0; i < spans.length; i++) {
            spanPositions.add(text.getSpanStart(spans[i]));
        }

        List<EmojiRange> allEmoji = findAllEmoji(text);
        for (int i = 0; i < allEmoji.size(); i++) {
            EmojiRange range = allEmoji.get(i);
            if (!spanPositions.contains(range.getStart())) {
                text.setSpan(new EmojiSpan(context, range.getEmoji(), size),
                        range.getStart(), range.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    @Nullable
    public Emoji findEmoji(@NonNull CharSequence candidate) {
        return map.get(candidate.toString());
    }

    @NonNull
    private List<EmojiRange> findAllEmoji(CharSequence text) {
        List<EmojiRange> result = new ArrayList<>();
        if (!TextUtils.isEmpty(text)) {
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                Emoji found = findEmoji(text.subSequence(matcher.start(), matcher.end()));
                if (found != null) {
                    result.add(new EmojiRange(found, matcher.start(), matcher.end()));
                }
            }
        }
        return result;
    }

    /**
     * 获取表情分类
     */
    public EmojiCategory[] getCategories() {
        return categories; // NOPMD
    }

    /**
     * 获取正则
     */
    public Pattern getRepetitivePattern() {
        return repetitivePattern;
    }

    /**
     * 释放所有资源
     */
    public static void destroy() {
        INSTANCE.map.clear();
        INSTANCE.categories = null;
        INSTANCE.pattern = null;
        INSTANCE.repetitivePattern = null;
    }
}
