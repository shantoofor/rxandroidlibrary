package com.shantoo.rxandroid.widget.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.Checkable;

import com.shantoo.rxandroid.R;

/**
 * 实现图片两种状态间切换的ImageView
 */
public class SelectorImageView extends AppCompatImageView implements Checkable {
    private boolean isChecked;
    private Drawable mDrawable;
    private Drawable mSelectorDrawable;

    public SelectorImageView(Context context) {
        this(context, null);
    }

    public SelectorImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if(isInEditMode()){
            return;
        }

        /**获取默认属性src的Drawable并用成员变量保存*/
        mDrawable = getDrawable();
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectorImageView);

        /**获取自定义属性selector_src的Drawable并用成员变量保存*/
        Drawable d = typedArray.getDrawable(R.styleable.SelectorImageView_rx_selected_src);
        mSelectorDrawable = d;

        /**获取自定义属性checked的值并用成员变量保存*/
        isChecked = typedArray.getBoolean(R.styleable.SelectorImageView_rx_checked, false);

        setChecked(isChecked);
        if (isChecked && d != null) {
            /**如果在布局中设置了selector_src与checked = true，我们就要设置ImageView的图片为mSelectorDrawable */
            setImageDrawable(d);
        }
        typedArray.recycle();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
    }

    @Override
    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        /**此处依据是否选中来设置不同的图片*/
        if (isChecked()) {
            setImageDrawable(mSelectorDrawable);
        } else {
            setImageDrawable(mDrawable);
        }
    }

    public void toggle(boolean checked) {
        /**外部通过调用此方法传入checked参数，然后把值传入给setChecked（）方法改变当前的选中状态*/
        setChecked(checked);
        toggle();
    }
}