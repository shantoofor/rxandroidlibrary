package com.shantoo.rxandroid.widget.popupwindow;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shantoo.rxandroid.R;
import com.shantoo.rxandroid.common.utils.UIUtil;
import com.shantoo.rxandroid.widget.model.ActionItem;

import java.util.ArrayList;

/***
 * 功能描述：标题按钮上的弹窗（继承自PopupWindow）
 */
public class WidgetPopup extends PopupWindow implements OnItemClickListener{

    // 列表弹窗的间隔
    protected final int LIST_PADDING = 10;
    // 坐标的位置（x、y）
    private final int[] mLocation = new int[2];
    private Context mContext;
    // 实例化一个矩形
    private Rect mRect = new Rect();
    // 屏幕的宽度和高度
    private int mScreenWidth, mScreenHeight;

    // 判断是否需要添加或更新列表子类项
    private boolean mIsDirty;

    // 位置不在中心
    private int popupGravity = Gravity.NO_GRAVITY;

    // 弹窗子类项选中时的监听
    private OnItemOnClickListener mItemOnClickListener;

    // 定义列表对象
    private RecyclerView mRecyclerView;

    // 定义弹窗子类项列表
    private ArrayList<ActionItem> mActionItems = new ArrayList<>();
    private int colorItemText = 0;

    private AbsListView currentDataView;
    private ListView listView;
    private GridView gridView;

    public WidgetPopup(Context context) {
        // 设置布局的参数
        this(context, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public WidgetPopup(Context context, int width, int height) {
        this.mContext = context;

        // 设置可以获得焦点
        setFocusable(true);
        // 设置弹窗内可点击
        setTouchable(true);
        // 设置弹窗外可点击
        setOutsideTouchable(true);

        // 获得屏幕的宽度和高度
        mScreenWidth = UIUtil.getScreenWidth();
        mScreenHeight = UIUtil.getScreenHeight();

        // 设置弹窗的宽度和高度
        setWidth(width);
        setHeight(height);

        setBackgroundDrawable(new BitmapDrawable());

        // 设置弹窗的布局界面
        setContentView(LayoutInflater.from(mContext).inflate(R.layout.widget_popupwindow_default, null));

        initUI();
    }

    public WidgetPopup(Context context, int width, int height, int layout) {
        this.mContext = context;

        // 设置可以获得焦点
        setFocusable(true);
        // 设置弹窗内可点击
        setTouchable(true);
        // 设置弹窗外可点击
        setOutsideTouchable(true);

        // 获得屏幕的宽度和高度
        mScreenWidth = UIUtil.getScreenWidth();
        mScreenHeight = UIUtil.getScreenHeight();

        // 设置弹窗的宽度和高度
        setWidth(width);
        setHeight(height);

        setBackgroundDrawable(new BitmapDrawable());

        // 设置弹窗的布局界面
        setContentView(LayoutInflater.from(mContext).inflate(layout, null));

        initUI();
    }

    /**
     * 初始化弹窗列表
     */
    private void initUI() {
        View view = getContentView().findViewById(R.id.popup_window_data_list);
        if(view instanceof ListView){
            currentDataView = (ListView) view;
        }else if(view instanceof GridView){
            currentDataView = (GridView) view;
        }
        currentDataView.setOnItemClickListener(this);
    }

    /**
     * 显示弹窗列表界面
     */
    public void show(View view) {
        // 获得点击屏幕的位置坐标
        view.getLocationOnScreen(mLocation);

        // 设置矩形的大小
        mRect.set(mLocation[0], mLocation[1], mLocation[0] + view.getWidth(), mLocation[1] + view.getHeight());

        // 判断是否需要添加或更新列表子类项
        if (mIsDirty) {
            populateActions();
        }

        // 显示弹窗的位置
        showAtLocation(view, popupGravity, mScreenWidth - LIST_PADDING - (getWidth() / 2), mRect.bottom + UIUtil.dp2px(7.5f));
    }

    /**
     * 显示弹窗列表界面
     */
    public void show(View view, int dex) {
        // 获得点击屏幕的位置坐标
        view.getLocationOnScreen(mLocation);

        // 设置矩形的大小
        mRect.set(mLocation[0], mLocation[1], mLocation[0] + view.getWidth(), mLocation[1] + view.getHeight());

        // 判断是否需要添加或更新列表子类项
        if (mIsDirty) {
            populateActions();
        }

        // 显示弹窗的位置
        showAtLocation(view, popupGravity, mLocation[0], mRect.bottom + dex);
    }

    public void setColorItemText(int colorItemText) {
        this.colorItemText = colorItemText;
    }

    /**
     * 设置弹窗列表子项
     */
    private void populateActions() {
        mIsDirty = false;

        // 设置列表的适配器
        currentDataView.setAdapter(new BaseAdapter() {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv_pop;
                ImageView iv_pop;
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.widget_item_popup, null);
                }
                tv_pop = (TextView) convertView.findViewById(R.id.tv_pop);
                iv_pop = (ImageView) convertView.findViewById(R.id.iv_pop);
                if (colorItemText == 0) {
                    colorItemText = mContext.getResources().getColor(android.R.color.white);
                }
                tv_pop.setTextColor(colorItemText);
                tv_pop.setTextSize(14);
                // 设置文本居中
                tv_pop.setGravity(Gravity.CENTER);
                // 设置文本域的范围
                tv_pop.setPadding(0, 10, 0, 10);
                // 设置文本在一行内显示（不换行）
                tv_pop.setSingleLine(true);

                ActionItem item = mActionItems.get(position);

                // 设置文本文字
                tv_pop.setText(item.mTitle);
                if (item.mResourcesId == 0) {
                    iv_pop.setVisibility(View.GONE);
                } else {
                    iv_pop.setVisibility(View.VISIBLE);
                    iv_pop.setImageResource(item.mResourcesId);
                }

                return convertView;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public Object getItem(int position) {
                return mActionItems.get(position);
            }

            @Override
            public int getCount() {
                return mActionItems.size();
            }
        });
    }

    /**
     * 添加子类项
     */
    public void addAction(ActionItem action) {
        if (action != null) {
            mActionItems.add(action);
            mIsDirty = true;
        }
    }

    /**
     * 清除子类项
     */
    public void cleanAction() {
        if (mActionItems.isEmpty()) {
            mActionItems.clear();
            mIsDirty = true;
        }
    }

    /**
     * 根据位置得到子类项
     */
    public ActionItem getAction(int position) {
        if (position < 0 || position > mActionItems.size())
            return null;
        return mActionItems.get(position);
    }

    /**
     * 设置监听事件
     */
    public void setItemOnClickListener(
            OnItemOnClickListener onItemOnClickListener) {
        this.mItemOnClickListener = onItemOnClickListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 点击子类项后，弹窗消失
        dismiss();
        if (mItemOnClickListener != null)
            mItemOnClickListener.onItemClick(mActionItems.get(position), position);
    }

    /**
     * @author yangyu 功能描述：弹窗子类项按钮监听事件
     */
    public interface OnItemOnClickListener {
        void onItemClick(ActionItem item, int position);
    }
}
