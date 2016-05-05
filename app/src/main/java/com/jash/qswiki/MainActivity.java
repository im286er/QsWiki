package com.jash.qswiki;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jash.qswiki.adapters.ArticleAdapter;
import com.jash.qswiki.entities.ArticleType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //提交测试
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        TabLayout tab = (TabLayout) findViewById(R.id.main_tab);
        pager.setAdapter(new ArticleAdapter(getSupportFragmentManager(), ArticleType.getList()));
        tab.setupWithViewPager(pager);
    }
}
