package com.example.way;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class TravelHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_history);

        MaterialButton addHistoryBtn = findViewById(R.id.addnewhistorybtn);

        addHistoryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(TravelHistoryActivity.this, AddHistoryActivity.class));
            }
        });

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        // historyList에 title 순으로 정렬
        RealmResults<History> historyList = realm.where(History.class).sort("title", Sort.DESCENDING).findAll();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HistoryAdapter myAdapter = new HistoryAdapter(getApplicationContext(), historyList);
        recyclerView.setAdapter(myAdapter);

        historyList.addChangeListener(new RealmChangeListener<RealmResults<History>>() {
            @Override
            public void onChange(RealmResults<History> histories) {
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}