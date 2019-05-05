package com.liuchen.photosshow.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.liuchen.photosshow.Adapter.MainAdapter;
import com.liuchen.photosshow.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<List<String>> photos;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        photos = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        stringList.add("http://img2.imgtn.bdimg.com/it/u=234634259,4236876085&fm=26&gp=0.jpg");
        stringList.add("http://img5.imgtn.bdimg.com/it/u=3300305952,1328708913&fm=26&gp=0.jpg");
        stringList.add("http://img5.imgtn.bdimg.com/it/u=1979198230,3799788659&fm=26&gp=0.jpg");
        stringList.add("http://img3.imgtn.bdimg.com/it/u=1055727654,337004439&fm=26&gp=0.jpg");
        stringList.add("http://img2.imgtn.bdimg.com/it/u=1451330793,2242997567&fm=26&gp=0.jpg");
        stringList.add("http://img2.imgtn.bdimg.com/it/u=3254402190,978386270&fm=26&gp=0.jpg");
        stringList.add("http://img1.imgtn.bdimg.com/it/u=2174909441,2495215020&fm=26&gp=0.jpg");
        stringList.add("http://img0.imgtn.bdimg.com/it/u=2968231382,1111539856&fm=26&gp=0.jpg");
        stringList.add("http://img2.imgtn.bdimg.com/it/u=1718395925,3485808025&fm=26&gp=0.jpg");
        photos.add(stringList);
        photos.add(stringList);
        photos.add(stringList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter mainAdapter = new MainAdapter(this, R.layout.item_mian, photos);
        recyclerView.setAdapter(mainAdapter);
    }
}
