package com.jingpai.pos.customer.activity.authentication.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.customer.CitySelect.CityPickerActivity;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.authentication.adapter.YezhuSelectVillageAdapter;
import com.jingpai.pos.customer.bean.CommunityByCityNameBean;
import com.jingpai.pos.customer.views.CustomToolBar;
import com.jingpai.pos.customer.mvp.YezhuPresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.StatusBarUtil;
import com.jingpai.pos.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author 86173
 * <p>
 *  小区
 */
public class YezhuRegisterSearchActivity extends AppCompatActivity /*implements UpdataInterface*/ {
    private CustomToolBar toolbar;
    private LinearLayout llSearch;
    private TextView etSearch;
    private TextView tvCancel;
    private View view;
    private TextView tv_dinwei;
    private TextView tvXiaoqu;
    private View underlineOnlyChild;
    private TextView tvTitle;
    private TextView tv_refresh_dinwei;
    private RecyclerView rvList;

    private YezhuPresenter yezhuPresenter;
    YezhuSelectVillageAdapter yezhuSelectVillageAdapter;
    private static final int SELECT_CITY = 101;
    private static final int REQUEST_CODE_TEST = 999;
//    LocationManager locationManager;
    String jingDu;
    String weiDu;
    private static final int BAIDU_READ_PHONE_STATE = 100;//定位权限请求
    private static final int PRIVATE_CODE = 1315;//开启GPS权限
    static final String[] LOCATIONGPS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};
    //    List<> list = new ArrayList();
    String city;
    List<CommunityByCityNameBean.PageBean.RecordsBean> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_yezhu_xiaoqu);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
    }


    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        llSearch = findViewById(R.id.ll_search);
        etSearch = findViewById(R.id.et_search);
        tvCancel = findViewById(R.id.tv_cancel);
        view = findViewById(R.id.view);
        tv_refresh_dinwei = findViewById(R.id.tv_refresh_dinwei);
        tv_dinwei = findViewById(R.id.tv_dinwei);
        tvXiaoqu = findViewById(R.id.tv_xiaoqu);
        underlineOnlyChild = findViewById(R.id.underline_only_child);
        tvTitle = findViewById(R.id.tv_title);
        rvList = findViewById(R.id.rv_list);

        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, true);
        StatusBarUtil.INSTANCE.setTranslucentStatus(this);
        StatusBarUtil.INSTANCE.setStatusBarDarkTheme(this, true);
        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.white));


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(layoutManager);
        yezhuSelectVillageAdapter = new YezhuSelectVillageAdapter(this);
        rvList.setAdapter(yezhuSelectVillageAdapter);
        yezhuPresenter = new YezhuPresenter();
//        mTvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        showGPSContacts();
        tv_refresh_dinwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YezhuRegisterSearchActivity.this, CityPickerActivity.class);
                startActivityForResult(intent,88);
            }
        });
    }

    private void initData(/*String content*/) {

//        list.clear();
//        getCityName();



        //小区列表请求
//        CommunityPresenter communityPresenter = new CommunityPresenter();
//        communityPresenter.getSelectVillage(jsonArray -> {
//            List<VillageBean.DataBean> villageBeans = jsonArray.toJavaList(VillageBean.DataBean.class);
//            if (villageBeans == null) return;
//            yezhuSelectVillageAdapter.setData(villageBeans);
//        });
    }

    private void getCommunityInfo(String city) {
        list.clear();
        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("city", city);
        baseRequest.put("pageNo", 1);
        baseRequest.put("pageSize", 999);
        yezhuPresenter.communityByCityName(baseRequest,communityByCityNameBean -> {
            if (communityByCityNameBean ==null || communityByCityNameBean.getPage()==null
                    || communityByCityNameBean.getPage().getRecords().size()==0 ){
                yezhuSelectVillageAdapter.notifyDataSetChanged();
                rvList.setVisibility(View.GONE);
                return;
            }
            rvList.setVisibility(View.VISIBLE);
            list = communityByCityNameBean.getPage().getRecords();
            yezhuSelectVillageAdapter.setData(list);
        });
        yezhuSelectVillageAdapter.notifyDataSetChanged();
    }

    private void getCityName(String jingdu ,String weiDu) {
        if (TextUtils.isEmpty(jingDu) || TextUtils.isEmpty(weiDu))
            ToastUtils.INSTANCE.showToast("经纬度为空");
        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("lat", weiDu);
        baseRequest.put("lon", jingDu);
        baseRequest.put("pageNo", 1);
        baseRequest.put("pageSize", 20);
        yezhuPresenter.getCityInfo(baseRequest, getCityInfoBean -> {
            if (getCityInfoBean == null) return;
            city = TextUtils.isEmpty(getCityInfoBean.getCity()) ? "" : getCityInfoBean.getCity();
            tv_dinwei.setText(city);
            getCommunityInfo(city);
        });
    }

//    @Override
//    public void updataInterface() {
//        initData(content);
//    }

    /**
     * 检测GPS、位置权限是否开启
     */
    public void showGPSContacts() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
//            if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
//                if (ContextCompat.checkSelfPermission(YezhuRegisterSearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(YezhuRegisterSearchActivity.this, LOCATIONGPS,
//                            BAIDU_READ_PHONE_STATE);
//                } else {
//                    getLocation();//getLocation为定位方法
//                }
//            } else {
                getLocation();//getLocation为定位方法
//            }
        } else {
            Toast.makeText(YezhuRegisterSearchActivity.this, "系统检测到未开启GPS定位服务,请开启", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 得到地理信息
     */
    private void getLocation() {
//        locationManager = (LocationManager) YezhuRegisterSearchActivity.this.getSystemService(Context.LOCATION_SERVICE);
        //locationManager.setTestProviderEnabled("gps", true);
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(serviceName);
        if (ActivityCompat.checkSelfPermission(YezhuRegisterSearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(YezhuRegisterSearchActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
//        city = getAddressFromLocation(YezhuRegisterSearchActivity.this, location);
//        tvMyTip.setText(city);
//        ToastUtils.INSTANCE.showToast(city);

//        weiDu = location.getLatitude()+"";
//        jingDu = location.getLongitude()+"";
//        ToastUtils.INSTANCE.showToast(a+""+b);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, mLocationListener01);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, mLocationListener01);
    }




    // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
    public final LocationListener mLocationListener01 = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (jingDu == null) {
                updateToNewLocation(location);
            } else {
                return;
            }
        }
        @Override
        public void onProviderDisabled(String provider) {
            updateToNewLocation(null);
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private Location updateToNewLocation(Location location) {
        System.out.println("--------zhixing--2--------");
        String latLongString;
        double lat = 0;
        double lng = 0;

        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();

            latLongString = "纬度:" + lat + "\n经度:" + lng;
            weiDu = lat + "";
            jingDu = lng + "";
            getCityName(jingDu,weiDu);
//                loadWebIndo();
//                ToastUtils.INSTANCE.showToast(latLongString);
        } else {
            latLongString = "无法获取地理信息，请稍后...";
        }
        if (lat != 0) {
            System.out.println("--------反馈信息----------" + String.valueOf(lat));
        }

//        Toast.makeText(getActivity(), latLongString, Toast.LENGTH_SHORT).show();

        return location;

    }

    private static String getAddressFromLocation(final Activity activity, Location location) {
        //Geocoder初始化
        Geocoder geocoder = new Geocoder(activity);
        //判断Geocoder地理编码是否可用
        boolean falg = geocoder.isPresent();
        try {
            //获取纬度和经度
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            //根据经纬度获取地理信息
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                //返回当前位置，精度可调
                Address address = addresses.get(0);
                String sAddress;
                if (!TextUtils.isEmpty(address.getLocality())) {
                    if (!TextUtils.isEmpty(address.getFeatureName())) {
                        //存储 市 + 周边地址
                       sAddress = address.getLocality() + " " + address.getFeatureName();

                        //address.getCountryName() 国家
                        //address.getPostalCode() 邮编
                        //address.getCountryCode() 国家编码
                        //address.getAdminArea() 省份
                        //address.getSubAdminArea() 二级省份
                        //address.getThoroughfare() 道路
                        //address.getSubLocality() 二级城市
                    } else {
                        sAddress = address.getLocality();
                    }
                } else {
                    sAddress = "定位失败";
                }
                return sAddress;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 88) {
            if (data!=null){
                String cityName = data.getStringExtra("cityName");
                tv_dinwei.setText(TextUtils.isEmpty(cityName) ? city : cityName);
                getCommunityInfo(cityName);
            }
        }
    }
}
