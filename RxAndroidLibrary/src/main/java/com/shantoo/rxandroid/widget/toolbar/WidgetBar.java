package com.shantoo.rxandroid.widget.toolbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shantoo.rxandroid.R;
import com.shantoo.rxandroid.RxAndroid;
import com.shantoo.rxandroid.common.utils.UIUtil;

/**
 * 带有搜索框的ToolBar
 */
public class WidgetBar extends Toolbar {

    //WidgetBar的根视图
    private View root;
    //WidgetBar的導航按鈕
    private ImageView mNavigationIcon;
    //WidgetBar的标题
    private TextView mTitle;
    //WidgetBar右侧按钮
    private ImageView mRightButtonIcon;
    //WidgetBar的搜索框
    private EditText mSearchView;
    private LayoutInflater mInflater;

    private final static int TITLE_TEXT_SIZE_NORMAL = 18;
    private final static int PADDING_SIZE_NORMAL = 16;

    private TextView mRightButtonText;
    private TextView mNavigationText;

    public WidgetBar(Context context) {
        this(context, null);
    }

    public WidgetBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setFitsSystemWindows(true);

        initView();
        int padding = UIUtil.dp2px(PADDING_SIZE_NORMAL);
        setContentInsetsRelative(padding, padding);


        TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs, R.styleable.WidgetBar, defStyleAttr, 0);
        try{
            if (attrs != null) {

                //初始化左侧导航
                CharSequence navigationText = a.getText(R.styleable.WidgetBar_rx_navigation_text);
                if (navigationText != null) {
                    setNavigationText(navigationText);
                }

                setNavigationIcon(getResources().getDrawable(R.mipmap.ic_rx_widget_back));
                final Drawable navigationIcon = a.getDrawable(R.styleable.WidgetBar_rx_navigation_icon);
                if (navigationIcon != null) {
                    setNavigationIcon(navigationIcon);
                }

                //初始化标题
                final float titleTextSize = a.getDimension(1, R.styleable.WidgetBar_rx_title_text_size);
                if (titleTextSize != 0) {
                    setTitleTextSize(titleTextSize);
                }
                setTitleTextSize(TITLE_TEXT_SIZE_NORMAL);

                //搜索框
                boolean isShowSearchView = a.getBoolean(R.styleable.WidgetBar_rx_show_search_view, false);

                if (isShowSearchView) {
                    showSearchView();
                    hideTitleView();
                }

                //右侧按钮
                CharSequence rightButtonText = a.getText(R.styleable.WidgetBar_rx_right_button_text);
                if (rightButtonText != null) {
                    setRightButtonText(rightButtonText);
                }

                final Drawable rightButtonIcon = a.getDrawable(R.styleable.WidgetBar_rx_right_button_icon);
                if (rightButtonIcon != null) {
                    setRightButtonIcon(rightButtonIcon);
                }
            }
        }finally {
            a.recycle();
        }
    }

    private void initView() {

        if (root == null) {
            mInflater = LayoutInflater.from(RxAndroid.getContext());
            root = mInflater.inflate(R.layout.widget_toolbar, null);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            addView(root, lp);
        }

        mNavigationIcon = (ImageView) root.findViewById(R.id.rx_navigation_icon);
        mNavigationText = (TextView) root.findViewById(R.id.rx_navigation_text);

        mTitle = (TextView) root.findViewById(R.id.toolbar_title);
        mSearchView = (EditText) root.findViewById(R.id.toolbar_search_view);

        mRightButtonIcon = (ImageView) root.findViewById(R.id.right_button_icon);
        mRightButtonText = (TextView) root.findViewById(R.id.right_button_text);

        mNavigationIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    //左侧导航
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setNavigationIcon(Drawable resId) {
        if (mNavigationIcon != null) {
            mNavigationIcon.setBackground(resId);
            mNavigationIcon.setVisibility(VISIBLE);
            hiddenNavigationText();
        }
    }

    public void setNavigationIcon(@DrawableRes int resId) {
        if (mNavigationIcon != null) {
            mNavigationIcon.setBackgroundResource(resId);
            mNavigationIcon.setVisibility(VISIBLE);
            hiddenNavigationText();
        }
    }

    public void setNavigationText(CharSequence text){
        if (mNavigationText != null) {
            mNavigationText.setText(text);
            mNavigationText.setVisibility(VISIBLE);
            hiddenNavigationIcon();
        }
    }

    private void hiddenNavigationIcon(){
        if (mNavigationIcon != null) {
            mNavigationIcon.setVisibility(GONE);
        }
    }

    private void hiddenNavigationText(){
        if (mNavigationText != null) {
            mNavigationText.setVisibility(GONE);
        }
    }

    //标题相关

    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if (mTitle != null) {
            mTitle.setText(title);
            showTitleView();
        }
    }

    public void setTitleTextSize(float size) {
        if (mTitle != null) {
            mTitle.setTextSize(size);
        }
    }

    @Override
    public void setTitleTextColor(@ColorInt int color) {
        if (mTitle != null) {
            mTitle.setTextColor(color);
            showTitleView();
        }
    }

    public void showTitleView() {
        if (mTitle != null)
            mTitle.setVisibility(VISIBLE);
    }

    public void hideTitleView() {
        if (mTitle != null)
            mTitle.setVisibility(GONE);
    }



    //搜索框相关
    public void showSearchView() {
        if (mSearchView != null) {
            mSearchView.setVisibility(VISIBLE);
            mTitle.setVisibility(GONE);
        }
    }

    public void hideSearchView() {
        if (mSearchView != null) {
            mSearchView.setVisibility(GONE);
            mTitle.setVisibility(VISIBLE);
        }
    }



    //右側按鈕
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setRightButtonIcon(Drawable icon) {
        if (mRightButtonIcon != null) {
            mRightButtonIcon.setBackground(icon);
            mRightButtonIcon.setVisibility(VISIBLE);
            hiddenRightButtonText();
        }
    }

    public void setRightButtonText(CharSequence text) {
        if(mRightButtonText!=null){
            mRightButtonText.setText(text);
            mRightButtonText.setVisibility(VISIBLE);
            hiddenRightButtonIcon();
        }
    }

    public void hiddenRightButtonIcon(){
        if (mRightButtonIcon != null) {
            mRightButtonIcon.setVisibility(GONE);
        }
    }

    public void hiddenRightButtonText(){
        if (mRightButtonText != null) {
            mRightButtonText.setVisibility(GONE);
        }
    }

    public void setOnNavigationFinishListener(final Activity activity){
        mNavigationIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        mNavigationText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    public void setOnNavigationClickListener(OnClickListener listener){
        if(mNavigationIcon!=null){
            mNavigationIcon.setOnClickListener(listener);
            Toast.makeText(getContext(), "setOnNavigationClickListener", Toast.LENGTH_SHORT).show();
        }
        if(mNavigationText!=null){
            mNavigationText.setOnClickListener(listener);
        }
    }

    public void setOnRightButtonClickListener(OnClickListener listener) {
        if(mRightButtonIcon!=null){
            mRightButtonIcon.setOnClickListener(listener);
        }
        if(mRightButtonText!=null){
            mRightButtonText.setOnClickListener(listener);
        }
    }
}