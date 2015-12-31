package com.jash.qswiki.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.jash.qswiki.R;
import com.jash.qswiki.adapters.ArticleItemAdapter;
import com.jash.qswiki.entities.ArticleResult;
import com.jash.qswiki.utils.HttpUtils;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment implements Callback<ArticleResult>, PtrHandler, PtrUIHandler {


    private ArticleItemAdapter adapter;
    private String type;
    private PtrClassicFrameLayout swipe;
    private int page;
    private ImageView image;

    public ArticleFragment() {
        // Required empty public constructor
    }

    public static ArticleFragment newInstance(String type) {

        Bundle args = new Bundle();
        
        ArticleFragment fragment = new ArticleFragment();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getString("type");
        ListView list = (ListView) view.findViewById(R.id.article_list);
        adapter = new ArticleItemAdapter(getContext());
        list.setAdapter(adapter);
        page = 1;
        swipe = ((PtrClassicFrameLayout) view.findViewById(R.id.article_swipe));
        // header
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setTextColor(Color.BLACK);
//        header.setPadding(0, 200, 0, 0);

        /**
         * using a string, support: A-Z 0-9 - .
         * you can add more letters by {@link in.srain.cube.views.ptr.header.StoreHousePath#addChar}
         */
//        header.initWithString("loading");
        header.initWithStringArray(R.array.loading);
        header.setTextColor(Color.RED);
        swipe.setKeepHeaderWhenRefresh(true);
        image = new ImageView(getContext());
        image.setImageResource(R.mipmap.ic_launcher);
        swipe.setHeaderView(image);
        swipe.addPtrUIHandler(this);
        swipe.setPtrHandler(this);
        Call<ArticleResult> article = HttpUtils.getService().getArticle(type, page);
        article.enqueue(this);
    }

    @Override
    public void onResponse(Response<ArticleResult> response, Retrofit retrofit) {
        if (page == 1) {
            adapter.clear();
        }
        adapter.addAll(response.body().getList());
        swipe.refreshComplete();
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
        Toast.makeText(getContext(), "网络问题", Toast.LENGTH_SHORT).show();
        swipe.refreshComplete();
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        HttpUtils.getService().getArticle(type, page).enqueue(this);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        ViewCompat.animate(image).rotationBy(360000).setDuration(50000).setInterpolator(new FastOutLinearInInterpolator()).start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        ViewCompat.animate(image).cancel();
        ViewCompat.setRotation(image, 0);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}
