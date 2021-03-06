package ultimate.ui.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import ultimate.bean.Rollpage;
import ultimate.ui.adapter.NewsAdapter;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.postman.ultimate.R;
import ultimate.ui.activity.Web;
import ultimate.uilt.mylistview.MyListView;
import ultimate.uilt.tools.DataManager;
import ultimate.uilt.tools.PostmanHelper;
import ultimate.uilt.rollviewpager.OnItemClickListener;
import ultimate.uilt.rollviewpager.RollPagerView;
import ultimate.uilt.rollviewpager.adapter.LoopPagerAdapter;
import ultimate.uilt.rollviewpager.hintview.IconHintView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.List;

import static ultimate.uilt.tools.DataManager.getNews;
import static java.lang.Thread.sleep;


/**
 * Created by Ivory on 2016/7/19.
 * hahahaha
 */
public class NewsFragment extends BaseFragment implements View.OnClickListener {
    private View mContent;//视图
    private RollPagerView mLoopViewPager;//轮播图
    private TestLoopAdapter mLoopAdapter;//轮播图的适配器
    private Handler handler = new Handler();
    //private static DataManager dataManager = new DataManager();//数据管理类，可通过该类获取新闻，资讯的数据
    private MyListView mListView;//新闻的Listview，该类为ListView子类
    private NewsAdapter mAdapter;//新闻的适配器
    private List<Rollpage> rollpages;//存放轮播图的List

    private PullToRefreshScrollView pullToRefreshScrollView;
    @Override
    /**
     * 重写basefragment的initView函数
     */
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContent = inflater.inflate(R.layout.fragment_news,container,false);
        mLoopViewPager = (RollPagerView) mContent.findViewById(R.id.loop_view_pager);
        mListView = (MyListView)mContent.findViewById(R.id.news_list);
        mAdapter = new NewsAdapter(mActivity,R.layout.news_listview, DataManager.getNews());//新闻的适配器加载布局和数据
        mListView.setAdapter(mAdapter);
        mListView.setFocusable(false);
        pullToRefreshScrollView=(PullToRefreshScrollView) mContent.findViewById(R.id.pull_to_refresh_scrollView);
        return mContent;
    }

    @Override
    protected void initEvent() {
        mLoopViewPager.setAdapter(mLoopAdapter = new TestLoopAdapter(mLoopViewPager));
        mLoopViewPager.setHintView(new IconHintView(mActivity,R.drawable.point_focus,R.drawable.point_normal));
        mLoopViewPager.setOnItemClickListener(new OnItemClickListener() {//轮播图加载点击事件监听及动作
            @Override
            public void onItemClick(int position) {
                Intent i=new Intent(mActivity,Web.class);
                switch (position) {
                    case 0:
                        i.putExtra("url", rollpages.get(0).getUrl());
                        break;
                    case 1:
                        i.putExtra("url",rollpages.get(1).getUrl());
                        break;
                    case 2:
                        i.putExtra("url",rollpages.get(2).getUrl());
                        break;
                }
                startActivity(i);
            }
        });
        //通过handler修改轮播图的图片
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] c = new String[3];
                for(int i = 0;i<3;i++){
                    c[i]=new String(rollpages.get(i).getImgs());
                }
                mLoopAdapter.setImgs(c);
            }
        });
        //对新闻的每一项的URL进行设置，通过调用Activity_web来跳转到网页
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)view.findViewById(R.id.tittle)).setTextColor(ContextCompat.getColor(mActivity,R.color.black_not_important));//点击后弱化标题
                ultimate.bean.News t = getNews().get(i);
                //Toast.makeText(mActivity, t.getNews_url(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mActivity,Web.class);
                intent.putExtra("url", t.getUrl());
                startActivity(intent);
            }
        });

        //这几个刷新Label的设置
        pullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(PostmanHelper.SystemTime2String((DataManager.lastUpdateAt)));
        pullToRefreshScrollView.getLoadingLayoutProxy().setPullLabel("拉动加载更多");
        pullToRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        pullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel("松开手指刷新");

        //上拉、下拉设定
       // pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);

        //下拉监听函数
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
               Log.d("刷新","下拉拉动");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("刷新","下拉加载数据");
                        if(DataManager.getNewsData(mAdapter)==true) {
                            Log.i("刷新", "下拉加载数据完成");
                            pullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(PostmanHelper.SystemTime2String((DataManager.lastUpdateAt)));
                            //mAdapter.notifyDataSetChanged();
//                            mListView.refreshDrawableState();
                        }
                        else{
                            Log.i("刷新", "下拉数据无更新");
                        }
                        pullToRefreshScrollView.onRefreshComplete();
                    }
                });
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                Log.d("刷新","上拉拉动");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("刷新","上拉加载数据");
                        if(DataManager.getNewsData(mAdapter)==true) {
                            Log.i("刷新", "上拉加载数据完成");
                            pullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(PostmanHelper.SystemTime2String((DataManager.lastUpdateAt)));
                           // mAdapter.notifyDataSetChanged();
                        }else{
                            Log.i("刷新", "上拉数据无更新");
                        }
                        pullToRefreshScrollView.onRefreshComplete();
                    }
                });
            }


        });
    }

    @Override
    public void onClick(View view) {
    }

    /**
     * 初始化数据，重写父类initData，从dataManager中获取轮播图的相关信息，存放到rollpages这一ArrayList中。
     */
    @Override
    public void initData(){
        rollpages = DataManager.getRoll();
    }
    private class TestLoopAdapter extends LoopPagerAdapter {
        String[] imgs = new String[0];
        public void setImgs(String[] imgs){
            this.imgs = imgs;
            notifyDataSetChanged();
        }

        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            Log.i("RollViewPager","getView:"+imgs[position]);

            ImageView view = new ImageView(container.getContext());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("RollViewPager","onClick");
                }
            });
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Glide.with(mActivity)
                    .load(imgs[position])
                    .into(view);
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }

    }
}

