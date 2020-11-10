package com.jingpai.pos.customer.activity.census.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.census.GatherInfoPresenterOld;
import com.jingpai.pos.customer.activity.census.bean.CommunityBean;
import com.jingpai.pos.customer.activity.census.bean.JsonBean;
import com.jingpai.pos.customer.activity.census.bean.PopulationBean;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.mvp.presenter.show.home.CommunityPresenter;
import com.jingpai.pos.customer.utils.GetJsonDataUtil;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.utils.ToastUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 86173
 */
public class SecondStepInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_house_register_select)
    TextView tvHouseRegisterSelect;
    @BindView(R.id.tv_residential_address_select)
    TextView tvResidentialAddressSelect;
    @BindView(R.id.tv_address_select)
    EditText tvAddressSelect;
    @BindView(R.id.et_area_selector)
    EditText etAreaSelector;
    @BindView(R.id.btn_next_info)
    Button btnNextInfo;
    @BindView(R.id.ll_nationality)
    RelativeLayout llNationality;
    @BindView(R.id.underline_nationality)
    View underlineNationality;

    private int id;
    private String idNum;
    private GatherInfoPresenterOld gatherInfoPresenter;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private static boolean isLoaded = false;
    private PopulationBean populationBean;
    private String sheng;
    private String shi;
    private String qu;
    private String jie;
    private CommunityPresenter communityPresenter;
    private String position;
    @Override
    protected int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.activity_second_step;
    }

    @Override
    protected void initData() {
        initView();
        initListener();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
//                        Toast.makeText(SecondStepInfoActivity.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
//                    Toast.makeText(SecondStepInfoActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
//                    Toast.makeText(SecondStepInfoActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void initView() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        gatherInfoPresenter = new GatherInfoPresenterOld();
        communityPresenter = new CommunityPresenter();
        tianChong();
    }

    //获取户主地址
    private void initCommunity() {
        communityPresenter.getSelectVillage(communityArray -> {
            if (communityArray != null) {
                String id = LocalCache.getCurrentCommunityId() + "";
                List<CommunityBean> communityList = communityArray.toJavaList(CommunityBean.class);
                for (int i = 0; i < communityList.size(); i++) {
                    if (id.equals(communityList.get(i).getCommunityId() + "")) {
                        sheng = communityList.get(i).getProvince();//sehng
                        shi = communityList.get(i).getCity(); // shi
                        qu = communityList.get(i).getCounty(); // qu
                        jie = communityList.get(i).getAddress(); // jie
                    }
                }


            }
            if (!TextUtils.isEmpty(sheng) && !TextUtils.isEmpty(shi) && !TextUtils.isEmpty(qu)) {
                tvResidentialAddressSelect.setText(sheng + shi + qu);
            }
            if (!TextUtils.isEmpty(jie)) {
                tvAddressSelect.setText(jie);
            }
        });
    }


    private void tianChong() {
        idNum = getIntent().getStringExtra("idNum");
        position = getIntent().getStringExtra("position");
        if (!TextUtils.isEmpty(idNum)) {
            populationBean = (PopulationBean) LocalCache.getInfo(idNum);
            if (populationBean != null) {
                if (!TextUtils.isEmpty(populationBean.getPermanentResidenceAddress())) {
                    tvHouseRegisterSelect.setText(populationBean.getPermanentResidenceAddress());
                }
                if (!TextUtils.isEmpty(populationBean.getCurrentResidentialAddress())) {
                    tvResidentialAddressSelect.setText(populationBean.getCurrentResidentialAddress());
                }
                if (!TextUtils.isEmpty(populationBean.getDetailedAddress())) {
                    tvAddressSelect.setText(populationBean.getDetailedAddress());
                }
                if (populationBean.getId() != 0) {
                    id = populationBean.getId();
                }
                if (null==populationBean.getRelationshipHouseOwner()) {//业主为null
                    llNationality.setVisibility(View.VISIBLE);
                    underlineNationality.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(populationBean.getPropertyArea())) {
                        etAreaSelector.setText(populationBean.getPropertyArea());
                    }
                }else{
                    llNationality.setVisibility(View.GONE);
                    underlineNationality.setVisibility(View.GONE);
                }
            }
        }
    }


    private void initListener() {
        tvHouseRegisterSelect.setOnClickListener(this);
        tvResidentialAddressSelect.setOnClickListener(this);
        btnNextInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //点击选择户籍地址
            case R.id.tv_house_register_select:
                showPickerView(1);

                break;

            //点击选择现居住地址
            case R.id.tv_residential_address_select:
                showPickerView(2);
                break;

            //点击下一步
            case R.id.btn_next_info:
                //校验 传参 跳转
                String tvHouseRegisterSelectStr = tvHouseRegisterSelect.getText().toString();
                String tvResidentialAddressSelectStr = tvResidentialAddressSelect.getText().toString();
                String tvAddressSelectStr = tvAddressSelect.getText().toString();
                String etAreaSelectorStr = etAreaSelector.getText().toString();

                if ("请选择".equals(tvHouseRegisterSelectStr)) {
                    ToastUtils.INSTANCE.showToast("请选择户籍地址");
                    return;
                }
                if ("请选择".equals(tvResidentialAddressSelectStr)) {
                    ToastUtils.INSTANCE.showToast("请选择现居住地址");
                    return;
                }
                if (TextUtils.isEmpty(tvAddressSelectStr)) {
                    ToastUtils.INSTANCE.showToast("请输入详细地址");
                    return;
                }
                if (tvAddressSelectStr.length()<4){
                    ToastUtils.INSTANCE.showToast("详细地址应在4-50字符内");
                    return;
                }
                if (TextUtils.isEmpty(etAreaSelectorStr)&&(null==populationBean.getRelationshipHouseOwner())) {//业主为null
                    ToastUtils.INSTANCE.showToast("请输入产权面积");
                    return;
                }
                TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                baseRequest.put("permanentResidenceAddress", tvHouseRegisterSelectStr);             //请选择户籍地址
                baseRequest.put("currentResidentialAddress", tvResidentialAddressSelectStr);        //请选择现居住地址
                baseRequest.put("detailedAddress", tvAddressSelectStr);                            //请输入详细地址 todo
                baseRequest.put("propertyArea", etAreaSelectorStr);                                 //    请输入产权面积
                baseRequest.put("id", idNum);                                       //id
                gatherInfoPresenter.gatherInfo(baseRequest, integer -> {
                    saveCache();
                    Intent intent1 = new Intent(this, ThirdStepInfoActivity.class);
                    intent1.putExtra("idNum", idNum);
                    startActivity(intent1);
                    finish();
                });
                break;
        }
    }
    @OnClick(R.id.btn_pre_info)
    public void preStep(){
        saveCache();
        Intent intent1 = new Intent(this, OwnerInfoActivity.class);
        intent1.putExtra("idNum", idNum);
        intent1.putExtra("position", position+"");
        startActivity(intent1);
        finish();
    }
    private void saveCache(){
        PopulationBean populationBean = LocalCache.getInfo(idNum);
        if (populationBean!=null){
            populationBean.setPermanentResidenceAddress(tvHouseRegisterSelect.getText().toString());
            populationBean.setCurrentResidentialAddress(tvResidentialAddressSelect.getText().toString());
            populationBean.setDetailedAddress(tvAddressSelect.getText().toString());
            populationBean.setPropertyArea(etAreaSelector.getText().toString());
            LocalCache.saveInfo(idNum+"",populationBean);
        }
    }
    //  解析json填充集合
    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    private void showPickerView(int i) {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;
                String tvHouseRegisterSelectStr = tvHouseRegisterSelect.getText().toString();
                String tvResidentialAddressSelectStr = tvResidentialAddressSelect.getText().toString();
                if (i == 1) {
                    tvHouseRegisterSelect.setText(tx);
                } else if (i == 2) {
                    tvResidentialAddressSelect.setText(tx);
                }
//                Toast.makeText(SecondStepInfoActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器

        pvOptions.show();
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
