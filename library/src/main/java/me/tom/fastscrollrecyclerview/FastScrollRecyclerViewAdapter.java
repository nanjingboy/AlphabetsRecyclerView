package me.tom.fastscrollrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

abstract public class FastScrollRecyclerViewAdapter<GroupHeaderViewHolder extends FastScrollRecyclerView.GroupHeaderViewHolder, GroupItemViewHolder extends FastScrollRecyclerView.GroupItemViewHolder> extends RecyclerView.Adapter<FastScrollRecyclerView.DefaultViewHolder> {

    protected final static int VIEW_TYPE_HEADER = -2;
    protected final static int VIEW_TYPE_ITEM = -1;

    protected SparseArray mGroupHeaderPositions = new SparseArray();

    protected IItemClickListener mItemClickListener;
    protected IItemLongClickListener mItemLongClickListener;

    @Override
    final public int getItemCount() {
        int itemCount = 0;
        int groupCount = getGroupCount();
        mGroupHeaderPositions.clear();
        for (int index = 0; index < groupCount; index++) {
            mGroupHeaderPositions.append(itemCount, index);
            itemCount += getGroupItemCount(index) + 1;
        }
        return itemCount;
    }

    @Override
    final public long getItemId(int position) {
        int[] positions = relativePositions(position);
        if (positions[1] == -1) {
            return getGroupId(positions[0]);
        }
        return getGroupItemId(positions[0], positions[1]);
    }

    @Override
    final public int getItemViewType(int position) {
        if (mGroupHeaderPositions.indexOfKey(position) > -1) {
            return VIEW_TYPE_HEADER;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    final public FastScrollRecyclerView.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return onCreateGroupHeaderViewHolder(parent);
        }

        FastScrollRecyclerView.GroupItemViewHolder holder = onCreateGroupItemViewHolder(parent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(view);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mItemLongClickListener != null) {
                    return mItemLongClickListener.onItemLongClick(view);
                }
                return false;
            }
        });
        return holder;
    }

    @Override
    final public void onBindViewHolder(FastScrollRecyclerView.DefaultViewHolder holder, int position) {
        int[] positions = relativePositions(position);
        if (positions[1] == -1) {
            onBindGroupHeaderViewHolder((GroupHeaderViewHolder) holder, positions[0]);
        } else {
            onBindGroupItemViewHolder((GroupItemViewHolder) holder, positions[0], positions[1]);
        }
    }

    @Override
    final public void onBindViewHolder(FastScrollRecyclerView.DefaultViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public long getGroupItemId(int groupPosition, int groupItemPosition) {
        return groupItemPosition;
    }

    int groupHeaderAbsolutePosition(int position) {
        int index = mGroupHeaderPositions.indexOfValue(position);
        if (index > -1) {
            return mGroupHeaderPositions.keyAt(index);
        }
        return -1;
    }

    int[] relativePositions(int position) {
        int headerPosition = -1;
        if (mGroupHeaderPositions.indexOfKey(position) > -1) {
            headerPosition = position;
        } else {
            int count = mGroupHeaderPositions.size();
            for (int index = 0; index < count; index++) {
                int groupHeaderPosition = mGroupHeaderPositions.keyAt(index);
                if (position < groupHeaderPosition) {
                    break;
                }
                headerPosition = groupHeaderPosition;
            }
        }
        int groupIndex = Integer.parseInt(mGroupHeaderPositions.get(headerPosition).toString());
        return new int[] { groupIndex, position - headerPosition - 1 };
    }

    void setOnItemClickListener(IItemClickListener listener) {
        mItemClickListener = listener;
    }

    void setOnItemLongClickListener(IItemLongClickListener listener) {
        mItemLongClickListener = listener;
    }

    interface IItemClickListener {
        void onItemClick(View view);
    }

    interface IItemLongClickListener {
        boolean onItemLongClick(View view);
    }

    abstract public int getGroupCount();
    abstract public int getGroupItemCount(int groupPosition);

    abstract public GroupHeaderViewHolder onCreateGroupHeaderViewHolder(ViewGroup parent);
    abstract public GroupItemViewHolder onCreateGroupItemViewHolder(ViewGroup parent);

    abstract public void onBindGroupHeaderViewHolder(GroupHeaderViewHolder holder, int groupPosition);
    abstract public void onBindGroupItemViewHolder(GroupItemViewHolder holder, int groupPosition, int groupItemPosition);
}
