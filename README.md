# StarPlugin-Emoji

![](http://stars.costars.cn/f5458e9611a396e6ec04576fb9eed75.jpg)

依赖：
```java
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
implementation 'com.github.lannaican:StarPlugin-Emoji:1.0.0'
```

使用：
```java
//创建Category
public class QQEmojiCategory implements EmojiCategory {
    @NonNull
    @Override
    public Emoji[] getEmojiList() {
        return new Emoji[]{
                new Emoji("xx", R.mipmap.xxx),
}
//创建Provider
public class CustomEmojiProvide implements EmojiProvider {
    @NonNull
    @Override
    public EmojiCategory[] getCategories() {
        return new EmojiCategory[]{
            ...
        };
    }
}
//初始化
EmojiManager.install(new CustomEmojiProvide());
//布局
<com.star.plugin.emoji.view.picker.EmojiPicker
        android:id="@+id/emojiPicker"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        //app:emoji_layout="@layout/layout_emoji_picker"
        //app:emoji_tab_layout="@layout/layout_emoji_category"/>
```
