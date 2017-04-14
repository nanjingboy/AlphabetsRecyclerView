package me.tom.fastscrollrecyclerview.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import me.tom.fastscrollrecyclerview.FastScrollRecyclerView;


public class MainActivity extends AppCompatActivity {

    private LanguagesAdapter mAdapter;
    private FastScrollRecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> languages = Arrays.asList(new String[] { "Ruby", "Python", "Swift", "Java" });
        mAdapter = new LanguagesAdapter(this, languages);
        mRecyclerView = (FastScrollRecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }
}
