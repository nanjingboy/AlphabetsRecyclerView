package me.tom.fastscrollrecyclerview.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import me.tom.fastscrollrecyclerview.FastScrollRecyclerView;

public class DemoWithHeaderActivity extends AppCompatActivity {

    private DemoWithHeaderAdapter mAdapter;
    private FastScrollRecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_with_header);

        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        List<String> indexTitles = Arrays.asList(new String[] { "A", "B", "C", "D", "E", "X"});
        for (String indexTitle: indexTitles) {
            ArrayList<String> records = new ArrayList<>();
            records.add(String.format("%s_1", indexTitle));
            records.add(String.format("%s_2", indexTitle));
            records.add(String.format("%s_3", indexTitle));
            records.add(String.format("%s_4", indexTitle));
            records.add(String.format("%s_5", indexTitle));
            records.add(String.format("%s_6", indexTitle));
            HashMap<String, Object> item = new HashMap<>();
            item.put("key", indexTitle);
            item.put("records", records);
            data.add(item);
        }
        mAdapter = new DemoWithHeaderAdapter(this, data);
        mRecyclerView = (FastScrollRecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHeaderViewIndexTitle("#");
        mRecyclerView.setIndexTitles(indexTitles);
        mRecyclerView.setOnItemClickListener(new FastScrollRecyclerView.IItemClickListener() {
            @Override
            public void onItemClick(int groupPosition, int groupItemPosition) {
                Toast.makeText(
                        DemoWithHeaderActivity.this,
                        String.format(Locale.CHINA, "onItemClick\ngroupPosition: %d\ngroupItemPosition:%d", groupPosition, groupItemPosition),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        mRecyclerView.setOnItemLongClickListener(new FastScrollRecyclerView.IItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int groupPosition, int groupItemPosition) {
                Toast.makeText(
                        DemoWithHeaderActivity.this,
                        String.format(Locale.CHINA, "onItemLongClick\ngroupPosition: %d\ngroupItemPosition:%d", groupPosition, groupItemPosition),
                        Toast.LENGTH_SHORT
                ).show();
                return true;
            }
        });
    }
}
