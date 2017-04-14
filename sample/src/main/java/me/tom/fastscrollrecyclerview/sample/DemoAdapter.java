package me.tom.fastscrollrecyclerview.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import me.tom.fastscrollrecyclerview.FastScrollRecyclerView;
import me.tom.fastscrollrecyclerview.FastScrollRecyclerViewAdapter;

public class DemoAdapter extends FastScrollRecyclerViewAdapter<DemoAdapter.DemoHeadViewHolder, DemoAdapter.DemoItemViewHolder> {

    private LayoutInflater mInflater;

    private ArrayList<HashMap<String, Object>> mData;

    public DemoAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getGroupItemCount(int groupPosition) {
        return ((ArrayList<String>) mData.get(groupPosition).get("records")).size();
    }

    @Override
    public DemoHeadViewHolder onCreateGroupHeaderViewHolder(ViewGroup parent) {
        return new DemoHeadViewHolder(mInflater.inflate(R.layout.demo_header, parent, false));
    }

    @Override
    public DemoItemViewHolder onCreateGroupItemViewHolder(ViewGroup parent) {
        return new DemoItemViewHolder(mInflater.inflate(R.layout.demo_item, parent, false));
    }

    @Override
    public void onBindGroupHeaderViewHolder(DemoHeadViewHolder holder, int groupPosition) {
        holder.titleView.setText(mData.get(groupPosition).get("key").toString());
    }

    @Override
    public void onBindGroupItemViewHolder(DemoItemViewHolder holder, int groupPosition, int groupItemPosition) {
        ArrayList<String> records = ((ArrayList<String>) mData.get(groupPosition).get("records"));
        holder.titleView.setText(records.get(groupItemPosition));
    }

    public static class DemoHeadViewHolder extends FastScrollRecyclerView.GroupHeaderViewHolder {

        public TextView titleView;

        public DemoHeadViewHolder(View view) {
            super(view);
            titleView = (TextView) view.findViewById(R.id.titleView);
        }
    }

    public static class DemoItemViewHolder extends FastScrollRecyclerView.GroupItemViewHolder {

        public TextView titleView;

        public DemoItemViewHolder(View view) {
            super(view);
            titleView = (TextView) view.findViewById(R.id.titleView);
        }
    }
}