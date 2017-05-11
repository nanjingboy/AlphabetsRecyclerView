package me.tom.fastscrollrecyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FastScrollRecyclerView extends RelativeLayout {

    protected RecyclerView mRecyclerView;
    protected TextView mDialogTextView;
    protected FastScrollRecyclerViewSliderBar mSliderBar;
    protected FastScrollRecyclerViewAdapter mAdapter;

    protected IItemClickListener mItemClickListener;
    protected IItemLongClickListener mItemLongClickListener;

    protected String mHeaderIndexTitle;
    protected List<String> mIndexTitles = new ArrayList<>();

    public FastScrollRecyclerView(Context context) {
        this(context, null);
    }

    public FastScrollRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FastScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.fast_scroll_recycler_view, this);
        mRecyclerView = (RecyclerView) getChildAt(0);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mSliderBar = (FastScrollRecyclerViewSliderBar) getChildAt(1);
        mSliderBar.setListener(new FastScrollRecyclerViewSliderBar.IIndexChangedListener() {
            @Override
            public void onIndexChanged(int position, String indexTitle) {
                if (indexTitle == null) {
                    mDialogTextView.setVisibility(GONE);
                } else {
                    mDialogTextView.setVisibility(VISIBLE);
                    mDialogTextView.setText(indexTitle);
                    if (mHeaderIndexTitle != null && mHeaderIndexTitle.length() > 0) {
                        if (position == 0) {
                            ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(0, 0);
                        } else {
                            scrollToPosition(position - 1, -1);
                        }
                    } else {
                        scrollToPosition(position, -1);
                    }
                }
            }
        });
        mDialogTextView = (TextView) getChildAt(2);
    }

    public void setAdapter(FastScrollRecyclerViewAdapter adapter) {
        mAdapter = adapter;
        mAdapter.setOnItemClickListener(new FastScrollRecyclerViewAdapter.IItemClickListener() {
            @Override
            public void onItemClick(View view) {
                if (mItemClickListener != null) {
                    int[] positions = mAdapter.relativePositions(mRecyclerView.getChildAdapterPosition(view));
                    mItemClickListener.onItemClick(positions[0], positions[1]);
                }
            }
        });

        mAdapter.setOnItemLongClickListener(new FastScrollRecyclerViewAdapter.IItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view) {
                if (mItemLongClickListener != null) {
                    int[] positions = mAdapter.relativePositions(mRecyclerView.getChildAdapterPosition(view));
                    return mItemLongClickListener.onItemLongClick(positions[0], positions[1]);
                }
                return false;
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    public void setSelection(final int groupPosition, final int groupItemPosition) {
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollToPosition(groupPosition, groupItemPosition);
                mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void scrollToPosition(int groupPosition, int groupItemPosition) {
        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(
                mAdapter.absolutePosition(groupPosition, groupItemPosition), 0
        );
    }

    public void setHeaderViewIndexTitle(String title) {
        mHeaderIndexTitle = title;
        setSliderBarIndexTitles();
    }

    public void setIndexTitles(List<String> titles) {
        mIndexTitles = titles;
        setSliderBarIndexTitles();
    }

    public void setOnItemClickListener(IItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(IItemLongClickListener listener) {
        mItemLongClickListener = listener;
    }

    protected void setSliderBarIndexTitles() {
        ArrayList<String> titles = new ArrayList<>();
        if (mHeaderIndexTitle != null && mHeaderIndexTitle.length() > 0) {
            titles.add(mHeaderIndexTitle);
        }
        for (String title: mIndexTitles) {
            titles.add(title);
        }
        mSliderBar.setIndexTitles(titles);
    }

    static abstract class DefaultViewHolder extends RecyclerView.ViewHolder {
        DefaultViewHolder(View view) {
            super(view);
        }
    }

    public static class HeaderViewHolder extends DefaultViewHolder {
        public HeaderViewHolder(View view) {
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

    public interface IItemClickListener {
        void onItemClick(int groupPosition, int groupItemPosition);
    }

    public interface IItemLongClickListener {
        boolean onItemLongClick(int groupPosition, int groupItemPosition);
    }
}
