package com.jingpai.pos.customer.activity.census.siderbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.census.SortModel;
import com.jingpai.pos.customer.activity.census.activity.SearchMinzuFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 86173
 */
public class SideBarActivity extends AppCompatActivity implements View.OnClickListener {
    private SideBar bar;
    private ListView mListView;
    private String[] mStrings;
    private TextView mTextView;
    private LinearLayout ll_search;
    private SearchMinzuFragment searchMinzuFragment;
    private FragmentManager fm;
    private FrameLayout fl_search_content;
    String minzu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_bar);
        bar = (SideBar) findViewById(R.id.bar);
        bar.setStyle(SideBar.STYLEWAVE);
//        bar.setStyle(SideBar.STYLENOWAVE);
//        bar.setStyle(SideBar.STYLENORMAL);
        mListView = (ListView) findViewById(R.id.list);
        mTextView = (TextView) findViewById(R.id.tv);
        ll_search = findViewById(R.id.ll_search);
        fl_search_content = findViewById(R.id.fl_search_content1);
        fl_search_content.setVisibility(View.GONE);
        mStrings = getResources().getStringArray(R.array.date);

        //数据转换成我们需要的module
        final List<SortModel> sortModule = getSortModule();
        final LVAdapter lvAdapter = new LVAdapter(sortModule, this);
        mListView.setAdapter(lvAdapter);
        //silderbar的回调
        bar.setOnStrSelectCallBack(new ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                mTextView.setText(selectStr);
                for (int i = 0; i < sortModule.size(); i++) {
                    if (sortModule.get(i).getSortLetters().equals(selectStr)) {
                        mListView.setSelection(i);
                        return;
                    }
                }
            }

            @Override
            public void onSelectEnd() {
                //只有SideBar.STYLENORMAL才会调用这个方法
                mTextView.setVisibility(View.GONE);
            }

            @Override
            public void onSelectStart() {
                //只有SideBar.STYLENORMAL才会调用这个方法
                mTextView.setVisibility(View.VISIBLE);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //传递当前的值回去
                String current =sortModule.get(position).getName().toString();
                Intent intent = new Intent();
                intent.putExtra("minzu",current);
                setResult(0,intent);
                finish();
            }
        });
        initFragment();

    }

    private void initFragment() {
        searchMinzuFragment = new SearchMinzuFragment();
        fm = getSupportFragmentManager();

        //dianji
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查找搜搜
                fl_search_content.setVisibility(View.VISIBLE);
            }
        });
        fm.beginTransaction().add(R.id.fl_search_content1, searchMinzuFragment)
                .show(searchMinzuFragment).commit();
    }

    private List<SortModel> getSortModule() {
        List<SortModel> filterDateList = new ArrayList<SortModel>();
        for (int i = 0; i < mStrings.length; i++) {
            String pinYinFirstLetter = Cn2Spell.getPinYinFirstLetter(mStrings[i]);
            SortModel sortModel = new SortModel(mStrings[i], pinYinFirstLetter.toUpperCase().charAt(0) + "");
            filterDateList.add(sortModel);
        }
        Collections.sort(filterDateList, new PinyinComparator());
        return filterDateList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
