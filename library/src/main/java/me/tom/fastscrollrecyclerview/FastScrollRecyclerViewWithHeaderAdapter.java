package me.tom.fastscrollrecyclerview;

import android.view.ViewGroup;

abstract public class FastScrollRecyclerViewWithHeaderAdapter<HeaderViewHolder extends FastScrollRecyclerView.HeaderViewHolder, GroupHeaderViewHolder extends FastScrollRecyclerView.GroupHeaderViewHolder, GroupItemViewHolder extends FastScrollRecyclerView.GroupItemViewHolder> extends FastScrollRecyclerViewAdapter<GroupHeaderViewHolder, GroupItemViewHolder> {

    protected final static int VIEW_TYPE_HEADER = -3;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        if (position == 0) {
            return getHeaderViewId();
        }
        return super.getItemId(position);
    }

    @Override
    public FastScrollRecyclerView.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return onCreateHeaderViewHolder(parent);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(FastScrollRecyclerView.DefaultViewHolder holder, int position) {
        if (position == 0) {
            onBindHeaderViewHolder((HeaderViewHolder) holder);
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    public long getHeaderViewId() {
        return 0;
    }

    @Override
    int getStartPosition() {
        return 1;
    }

    abstract public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent);
    abstract public void onBindHeaderViewHolder(HeaderViewHolder holder);
}
