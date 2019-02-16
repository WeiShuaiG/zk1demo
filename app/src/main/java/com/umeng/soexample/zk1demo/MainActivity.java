package com.umeng.soexample.zk1demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.umeng.soexample.zk1demo.adapter.GjzAdapter;
import com.umeng.soexample.zk1demo.entity.Gjz;
import com.umeng.soexample.zk1demo.iview.Iview;
import com.umeng.soexample.zk1demo.presenter.PresenterImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity<T> extends AppCompatActivity implements Iview<T>, XRecyclerView.LoadingListener {

    @BindView(R.id.shou_gjc_fen)
    ImageView shouGjcFen;
    @BindView(R.id.shou_gjc_sou)
    EditText shouGjcSou;
    @BindView(R.id.shou_gjz_recy)
    XRecyclerView shouGjzRecy;
    @BindView(R.id.gjz_sou)
    TextView gjzSou;
    private PresenterImpl presenter;
    private GjzAdapter adapter;
    private List<Gjz.ResultBean> mList = new ArrayList<>();
    private int page = 1;
    private int count  = 6;
    private HashMap<String,String> map = new HashMap<>();
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //沉浸式
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        /*View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/


        presenter = new PresenterImpl(this);
        adapter = new GjzAdapter(mList,this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        shouGjzRecy.setLayoutManager(staggeredGridLayoutManager);
        shouGjzRecy.setAdapter(adapter);
        shouGjzRecy.setLoadingListener(this);
        shouGjzRecy.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        shouGjzRecy.getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        bundle = new Bundle();
        adapter.setClick(new GjzAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int positon, int id) {
                Gjz.ResultBean bean = new Gjz.ResultBean();
                bean.setSaleNum(mList.get(positon).getSaleNum());
                bean.setPrice(mList.get(positon).getPrice());
                bean.setCommodityId(mList.get(positon).getCommodityId());
                bean.setCommodityName(mList.get(positon).getCommodityName());
                bean.setMasterPic(mList.get(positon).getMasterPic());
                bundle.putParcelable("bean",bean);
                Intent intent = new Intent(MainActivity.this, XiangqingActivity.class);
                intent.putExtra("Bean",bundle);
                startActivity(intent);
            }
        });
    }


    @OnClick(R.id.gjz_sou)
    public void onViewClicked() {
        String keyword = shouGjcSou.getText().toString().trim();
        map.put("count", count + "");
        map.put("page", page + "");
        map.put("keyword", keyword + "");
        presenter = new PresenterImpl(this);
        presenter.get(Contacts.SHOU_GJZ, map,null, Gjz.class);

    }

    @Override
    public void success(T success) {

        Gjz gjz = (Gjz) success;
        List<Gjz.ResultBean> list = gjz.getResult();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void error(T error) {

    }

    @Override
    public void onRefresh() {
        page=1;
        mList.clear();
        String keyword = shouGjcSou.getText().toString().trim();
        map.put("keyword", keyword + "");
        map.put("count", count + "");
        map.put("page", page + "");
        presenter.get(Contacts.SHOU_GJZ, map, null,Gjz.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                shouGjzRecy.refreshComplete();
            }
        }, 2000);



    }

    @Override
    public void onLoadMore() {
        page++;
        String keyword = shouGjcSou.getText().toString().trim();
        map.put("keyword", keyword + "");
        map.put("count", count + "");
        map.put("page", page + "");
        presenter.get(Contacts.SHOU_GJZ, map,null, Gjz.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                shouGjzRecy.refreshComplete();
            }
        }, 2000);

    }
}
