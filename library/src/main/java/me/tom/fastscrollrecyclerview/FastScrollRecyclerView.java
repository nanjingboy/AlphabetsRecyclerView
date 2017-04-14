package me.tom.fastscrollrecyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class FastScrollRecyclerView extends RecyclerView {

    private Context mContext;

    public FastScrollRecyclerView(Context context) {
        this(context, null);
    }

    public FastScrollRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof LinearLayoutManager) {
            super.setLayoutManager(layout);
        } else {
            super.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }

    static abstract class DefaultViewHolder extends RecyclerView.ViewHolder {
        public DefaultViewHolder(View view) {
            super(view);
        }
    }

    public static class GroupHeaderViewHolder extends DefaultViewHolder {
        public GroupHeaderViewHolder(View view) {
            super(view);
        }
    }

    public static class GroupItemViewHolder extends DefaultViewHolder {

        public GroupItemViewHolder(View view) {
            super(view);
        }
    }
}
