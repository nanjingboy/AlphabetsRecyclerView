package me.tom.fastscrollrecyclerview.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.tom.fastscrollrecyclerview.FastScrollRecyclerViewAdapter;

public class LanguagesAdapter extends FastScrollRecyclerViewAdapter<LanguagesAdapter.LanguageViewHolder> {

    private List<String> mData;
    protected LayoutInflater mInflater;

    public LanguagesAdapter(Context context, List<String> data) {
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public LanguageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LanguageViewHolder(mInflater.inflate(R.layout.language_item, parent, false));
    }

    @Override
    public void onBindViewHolder(LanguageViewHolder holder, int position) {
        holder.titleView.setText(mData.get(position));
    }

    public static class LanguageViewHolder extends RecyclerView.ViewHolder {

        public TextView titleView;

        public LanguageViewHolder(View view) {
            super(view);
            titleView = (TextView) view.findViewById(R.id.titleView);
        }
    }
}
