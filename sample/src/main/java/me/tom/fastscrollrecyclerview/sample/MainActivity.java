package me.tom.fastscrollrecyclerview.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import me.tom.fastscrollrecyclerview.FastScrollRecyclerView;


public class MainActivity extends AppCompatActivity {

    private DemoAdapter mAdapter;
    private FastScrollRecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        String[] keys = new String[] { "A", "B", "C", "D", "E", "X"};
        for (int index = 0; index < keys.length;  index++) {
            ArrayList<String> records = new ArrayList<>();
            records.add(String.format("%s_1", keys[index]));
            records.add(String.format("%s_2", keys[index]));
            records.add(String.format("%s_3", keys[index]));
            records.add(String.format("%s_4", keys[index]));
            records.add(String.format("%s_5", keys[index]));
            records.add(String.format("%s_6", keys[index]));
            HashMap<String, Object> item = new HashMap<>();
            item.put("key", keys[index]);
            item.put("records", records);
            data.add(item);
        }
        mAdapter = new DemoAdapter(this, data);
        mRecyclerView = (FastScrollRecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }
}
