package linksprite.com.zigbeehouse.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import linksprite.com.zigbeehouse.R;
import linksprite.com.zigbeehouse.SharedPreferencesUtils;
import linksprite.com.zigbeehouse.module.UpdateAirRequest;
import linksprite.com.zigbeehouse.module.UpdateRespone;
import linksprite.com.zigbeehouse.module.ZigbeeHouse;
import linksprite.com.zigbeehouse.module.Weater;
import linksprite.com.zigbeehouse.network.ApiManager;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DeviceInfoActivity extends AppCompatActivity {
    private final String TAG = DeviceInfoActivity.class.getSimpleName();
    Timer timer = null;
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.tv_weekday)
    TextView tvWeekday;
    @InjectView(R.id.tv_day)
    TextView tvDay;
    @InjectView(R.id.img_circle_air_con)
    ImageView imgCircleAirCon;
    @InjectView(R.id.tv_tem)
    TextView tvTem;
    @InjectView(R.id.img_air_con_run)
    ImageView imgAirConRun;
    @InjectView(R.id.img_circle_air)
    ImageView imgCircleAir;
    @InjectView(R.id.img_air_run)
    ImageView imgAirRun;
    @InjectView(R.id.tv_weater)
    TextView tvWeater;
    @InjectView(R.id.tv_status)
    TextView tvStatus;
    @InjectView(R.id.tv_tem_1)
    TextView tvTem1;
    @InjectView(R.id.tv_hum)
    TextView tvHum;
    @InjectView(R.id.tv_tem_2)
    TextView tvTem2;
    @InjectView(R.id.img_icon)
    ImageView imgIcon;
    @InjectView(R.id.tv_pm25)
    TextView tvPm25;

    private ZigbeeHouse mZigbeeHouse = null;

    private String jwt, deviceid;
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            ApiManager.getDevInfoService().getZigbeeHouse(jwt, deviceid)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ZigbeeHouse>() {
                        @Override
                        public void onCompleted() {
                            updateWeater();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, e.getMessage());
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(ZigbeeHouse zigbeeHouse) {
                            if (zigbeeHouse != null) {
                                mZigbeeHouse = zigbeeHouse;
                                Log.e(TAG, zigbeeHouse.toString());
                                updateUI(zigbeeHouse.getParams());
                            } else {
                                Log.e(TAG, "Zigbee House is NULL");
                            }
                        }
                    });
        }
    };

    private void updateWeater() {
        ApiManager.getWeaterInfo().getWeater()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weater>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(final Weater weater) {
                        if (weater != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvWeater.setText(weater.getWeatherinfo().getTemp1() + "-" + weater.getWeatherinfo().getTemp2());
                                    tvStatus.setText(weater.getWeatherinfo().getWeather());
                                }
                            });
                        } else {
                            Log.e(TAG, "Weater is NULL");
                        }
                    }
                });
    }


    private void updateUI(final ZigbeeHouse.ParamsBean paramsBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvTem.setText(paramsBean.getTemperature() + "℃");
                switch (paramsBean.getAir_con()) {
                    case "0":
                        imgCircleAirCon.setImageDrawable(getResources().getDrawable(R.mipmap.circle_close));
                        imgAirConRun.setImageDrawable(getResources().getDrawable(R.mipmap.run));
                        break;
                    case "1":
                        imgCircleAirCon.setImageDrawable(getResources().getDrawable(R.mipmap.circle_open));
                        imgAirConRun.setImageDrawable(getResources().getDrawable(R.mipmap.close));
                        break;
                }

                switch (paramsBean.getAir()) {
                    case "0":
                        imgCircleAir.setImageDrawable(getResources().getDrawable(R.mipmap.circle_close));
                        imgAirRun.setImageDrawable(getResources().getDrawable(R.mipmap.run));
                        break;
                    case "1":
                        imgCircleAir.setImageDrawable(getResources().getDrawable(R.mipmap.circle_open));
                        imgAirRun.setImageDrawable(getResources().getDrawable(R.mipmap.close));
                        break;
                }

                tvTem1.setText(paramsBean.getTemperature() + "℃");
                tvHum.setText(paramsBean.getHumidity());
                tvTem2.setText((Integer.parseInt(paramsBean.getTemperature()) + 5) + "℃");
                tvPm25.setText(paramsBean.getPm25());
                int pm25 = Integer.parseInt(paramsBean.getPm25());
                if (pm25 < 300) {
                    imgIcon.setImageDrawable(getResources().getDrawable(R.mipmap.very_good));
                } else if (pm25 < 1050) {
                    imgIcon.setImageDrawable(getResources().getDrawable(R.mipmap.good));
                } else {
                    imgIcon.setImageDrawable(getResources().getDrawable(R.mipmap.bad));
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_device_info);
        ButterKnife.inject(this);
        jwt = (String) SharedPreferencesUtils.getParam(this, "jwt", "");
        deviceid = (String) SharedPreferencesUtils.getParam(this, "deviceid", "");
        Log.i(TAG, jwt + "   \n" + deviceid);
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }

        tvDay.setText(getDay());
        tvWeekday.setText("星期" + mWay);
        tvTime.setText(getTime());
        if (timer == null)
            timer = new Timer();
        timer.schedule(timerTask, 0, 30000);
    }

    public String getDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
    }

    @OnClick({R.id.img_air_con_run, R.id.img_air_run})
    public void onClick(View view) {
        final UpdateAirRequest updateAirRequest = new UpdateAirRequest();
        updateAirRequest.setAction("update");
        updateAirRequest.setApikey(mZigbeeHouse.getApikey());
        updateAirRequest.setDeviceid(mZigbeeHouse.getDeviceid());
        final ZigbeeHouse.ParamsBean paramsBean = mZigbeeHouse.getParams();
        String air = paramsBean.getAir();
        String air_con = paramsBean.getAir_con();
        switch (view.getId()) {
            case R.id.img_air_con_run:
                air_con = air_con.equals("0") ? "1" : "0";
                break;
            case R.id.img_air_run:
                air = air.equals("0") ? "1" : "0";
                break;
        }
        updateAirRequest.setParams(new UpdateAirRequest.ParamsBean(air, air_con));
        paramsBean.setAir(air);
        paramsBean.setAir_con(air_con);

        Log.e(TAG, updateAirRequest.toString());
        ApiManager.getDevUpdateService().updateWeater(updateAirRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateRespone>() {
                    @Override
                    public void onCompleted() {
                        updateUI(paramsBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(UpdateRespone updateRespone) {
                        Log.e(TAG, updateRespone.toString() + "  " + updateRespone.getError());
                    }
                });
    }
}
