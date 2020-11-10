package com.jingpai.pos.customer.CitySelect;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.AppOpsManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jingpai.pos.customer.CitySelect.adapter.CityListAdapter;
import com.jingpai.pos.customer.CitySelect.util.PinyinUtils;
import com.jingpai.pos.customer.CitySelect.widget.SideLetterBar;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.City;
import com.jingpai.pos.customer.bean.CityListBean;
import com.jingpai.pos.customer.bean.LocateState;
import com.jingpai.pos.bean.Community;
import com.jingpai.pos.customer.fragment.SearchCityFragment;
import com.jingpai.pos.customer.mvp.YezhuPresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.DialogUtils;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.StatusBarUtil;
import com.jingpai.pos.utils.ToastUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.Nullable;

public class CityPickerActivity extends FragmentActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private ListView mListView;
    private TextView mEtSearch;
    private SideLetterBar mLetterBar;
    private CityListAdapter mCityAdapter;
    private FrameLayout fl_search_content;
    private SearchCityFragment searchMinzuFragment;
    private FragmentManager fm;
    LocationManager locationManager;
    String jingDu;
    String weiDu;
    DialogUtils loading;
    List<CityListBean.DataBean> listBeans = new ArrayList<>();
    YezhuPresenter yezhuPresenter;
    int cityCode;
    //    double jingDu;
//    double weiDu;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private static final int BAIDU_READ_PHONE_STATE = 100;//定位权限请求
    private static final int PRIVATE_CODE = 1315;//开启GPS权限
    static final String[] LOCATIONGPS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cp_activity_city_list);
        initView();
        initData();
//        getLocation();
        showGPSContacts();
    }

    protected void initView() {
        mListView = findViewById(R.id.listview_all_city);
        TextView overlay = findViewById(R.id.tv_letter_overlay);
        mLetterBar = findViewById(R.id.side_letter_bar);
        fl_search_content = findViewById(R.id.fl_search_content1);
        fl_search_content.setVisibility(View.GONE);
        mEtSearch = findViewById(R.id.et_search);
        mEtSearch.setOnClickListener(this);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });
        yezhuPresenter = new YezhuPresenter();
        StatusBarUtil.INSTANCE.setStatusBarMode(this, true, R.color.black);
        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.white));
        mCityAdapter = new CityListAdapter(this);
        mListView.setAdapter(mCityAdapter);
    }


    protected void initData() {
        yezhuPresenter.cityList(jsonArray -> {
            if (jsonArray == null) return;
            HashSet<City> citys = new HashSet<>();
            listBeans = jsonArray.toJavaList(CityListBean.DataBean.class);
            if (listBeans.size() == 0) return;
            for (int i = 0; i < listBeans.size(); i++) {
                String name = listBeans.get(i).getCityName();
                cityCode = Integer.parseInt(listBeans.get(i).getCityCode());
                citys.add(new City(cityCode, name, PinyinUtils.getPinYin(name), false));
            }

//            String json = ReadAssetsFileUtil.getJson(this, "city.json");
//            CityPickerBean bean = GsonUtil.getBean(json, CityPickerBean.class);
//            for (AreasBean areasBean : bean.data.areas) {
//                String name = areasBean.name.replace("　", "");
//                citys.add(new City(areasBean.id, name, PinyinUtils.getPinYin(name), areasBean.is_hot == 1));
//                for (AreasBean.ChildrenBeanX childrenBeanX : areasBean.children) {
//                    citys.add(new City(childrenBeanX.id, childrenBeanX.name, PinyinUtils.getPinYin(childrenBeanX.name), childrenBeanX.is_hot == 1));
//                }
//            }
            //set转换list
            ArrayList<City> cities = new ArrayList<>(citys);
            //按照字母排序
            Collections.sort(cities, new Comparator<City>() {
                @Override
                public int compare(City city, City t1) {
                    return city.getPinyin().compareTo(t1.getPinyin());
                }
            });
            mCityAdapter.setData(cities);
        });
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name, String code) {//选择城市
//                Toast.makeText(CityPickerActivity.this, name, Toast.LENGTH_SHORT).show();
                Community community = new Community();
                community.setCity(code);
                community.setCommunityName(LocalCache.getCurrentCommunityName());
                community.setCommunityId(LocalCache.getCurrentCommunityId());
                LocalCache.setCurrentCommunity(community);

                Intent intent = new Intent();
                intent.putExtra("cityName", name);
                intent.putExtra("code", code);
                setResult(101, intent);
                finish();
            }

            @Override
            public void onLocateClick() {//点击定位按钮
                loading = new DialogUtils(CityPickerActivity.this, R.style.CustomDialog);
                loading.setCanceledOnTouchOutside(false);
                loading.show();
//                getLocation();//重新定位
                getCityName();
                loading.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case PRIVATE_CODE:
                getLocation();
                break;
        }
    }

    /**
     * 得到地理信息
     */
    private void getLocation() {

//        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        //locationManager.setTestProviderEnabled("gps", true);
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(serviceName);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
//        weiDu = location.getLatitude() + "";
//        jingDu = location.getLongitude() + "";
//        getCityName();
//        String city=  getAddressFromLocation(this,location);
//        ToastUtils.INSTANCE.showToast(city);

//        weiDu = location.getLatitude();
//        jingDu = location.getLongitude();
//        ToastUtils.INSTANCE.showToast(a+""+b);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, mLocationListener01);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, mLocationListener01);
    }

    String city;
    String code;

    private void getCityName() {
        if (TextUtils.isEmpty(jingDu) || TextUtils.isEmpty(weiDu)) {
            ToastUtils.INSTANCE.showToast("系统检测到未开启GPS定位服务,请开启");
            mCityAdapter.updateLocateState(LocateState.FAILED, null, code);
            return;
        }
        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("lat", weiDu);
        baseRequest.put("lon", jingDu);
        baseRequest.put("pageNo", 1);
        baseRequest.put("pageSize", 20);

        yezhuPresenter.getCityInfo(baseRequest, getCityInfoBean -> {
            if (getCityInfoBean == null) return;
            city = TextUtils.isEmpty(getCityInfoBean.getCity()) ? "" : getCityInfoBean.getCity();
            code = TextUtils.isEmpty(getCityInfoBean.getCode()) ? "" : getCityInfoBean.getCode();
            Community community = new Community();
            community.setCity(code);
            community.setCommunityName(LocalCache.getCurrentCommunityName());
            community.setCommunityId(LocalCache.getCurrentCommunityId());
            LocalCache.setCurrentCommunity(community);
            if (!TextUtils.isEmpty(city)) {
                mCityAdapter.updateLocateState(LocateState.SUCCESS, city, code);
            } else {
                mCityAdapter.updateLocateState(LocateState.LOCATING, null, code);
            }
        });
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
            getCityName();
//                loadWebIndo();
//                ToastUtils.INSTANCE.showToast(latLongString);
        } else {
            latLongString = "无法获取地理信息，请稍后...";
        }
        if (lat != 0) {
            System.out.println("--------反馈信息----------" + String.valueOf(lat));
        }

//        Toast.makeText(CityPickerActivity.this, latLongString, Toast.LENGTH_SHORT).show();

        return location;

    }

    //声明定位回调监听器
    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    if (amapLocation.getCity() != null && !amapLocation.getCity().equals("")) {
                        mCityAdapter.updateLocateState(LocateState.SUCCESS, amapLocation.getCity().replace("市", ""), code);
                    } else {
                        mCityAdapter.updateLocateState(LocateState.FAILED, null, code);
                    }
                    mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                } else {
                    mCityAdapter.updateLocateState(LocateState.FAILED, null, code);
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("高德地图", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            //销毁定位客户端之后，若要重新开启定位请重新New一个AMapLocationClient对象。
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.et_search:
                fl_search_content.setVisibility(View.VISIBLE);
                mEtSearch.setVisibility(View.GONE);
                initFragment();
//                finish();
                break;
        }
    }

    private void initFragment() {
        searchMinzuFragment = new SearchCityFragment();
        fm = getSupportFragmentManager();

        fm.beginTransaction().add(R.id.fl_search_content1, searchMinzuFragment)
                .show(searchMinzuFragment).commit();
    }


    /**
     * 检测GPS、位置权限是否开启
     */
    public void showGPSContacts() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
                if (ContextCompat.checkSelfPermission(CityPickerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CityPickerActivity.this, LOCATIONGPS,
                            BAIDU_READ_PHONE_STATE);
                } else {
                    getLocation();//getLocation为定位方法
                }
            } else {
                getLocation();//getLocation为定位方法
            }
        } else {
//            Toast.makeText(CityPickerActivity.this, "系统检测到未开启GPS定位服务,请开启", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, PRIVATE_CODE);
        }
    }


    /**
     * Android6.0申请权限的回调方法
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                //如果用户取消，permissions可能为null.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0) {  //有权限
                    // 获取到权限，作相应处理
                    getLocation();
                } else {
//                    showGPSContacts();
                    setCheckOp();
                }
                break;
            default:
                break;
        }
    }
    private void setCheckOp() {
        int checkResult = checkOp(CityPickerActivity.this, 2, AppOpsManager.OPSTR_FINE_LOCATION);//其中2代表AppOpsManager.OP_GPS，如果要判断悬浮框权限，第二个参数需换成24即AppOpsManager。OP_SYSTEM_ALERT_WINDOW及，第三个参数需要换成AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW
        int checkResult2 = checkOp(CityPickerActivity.this, 1, AppOpsManager.OPSTR_FINE_LOCATION);
        if (AppOpsManagerCompat.MODE_IGNORED == checkResult || AppOpsManagerCompat.MODE_IGNORED == checkResult2) {
            //未开启定位权限或者被拒绝的操作
            mCityAdapter.updateLocateState(LocateState.FAILED, null, code);
//            Toast.makeText(CityPickerActivity.this, "系统检测到未开启GPS定位权限,请开启", Toast.LENGTH_SHORT).show();
            ToastUtils.INSTANCE.showToast("系统检测到未开启GPS定位服务,请开启");
        }
    }

    public static int checkOp(Context context, int op, String opString) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            Object object = context.getSystemService(Context.APP_OPS_SERVICE);
//            Object object = context.getSystemService("appops");
            Class c = object.getClass();
            try {
                Class[] cArg = new Class[3];
                cArg[0] = int.class;
                cArg[1] = int.class;
                cArg[2] = String.class;
                Method lMethod = c.getDeclaredMethod("checkOp", cArg);
                return (Integer) lMethod.invoke(object, op, Binder.getCallingUid(), context.getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
                if (Build.VERSION.SDK_INT >= 23) {
                    return AppOpsManagerCompat.noteOp(context, opString, context.getApplicationInfo().uid,
                            context.getPackageName());
                }

            }
        }
        return -1;
    }
}
