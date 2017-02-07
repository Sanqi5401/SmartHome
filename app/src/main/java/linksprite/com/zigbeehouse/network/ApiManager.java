package linksprite.com.zigbeehouse.network;


import java.util.List;
import java.util.concurrent.TimeUnit;

import linksprite.com.zigbeehouse.Constant;
import linksprite.com.zigbeehouse.module.DevListRespone;
import linksprite.com.zigbeehouse.module.LoginRequest;
import linksprite.com.zigbeehouse.module.LoginResponse;
import linksprite.com.zigbeehouse.module.ZigbeeHouse;
import linksprite.com.zigbeehouse.module.UpdateAirRequest;
import linksprite.com.zigbeehouse.module.UpdateRespone;
import linksprite.com.zigbeehouse.module.Weater;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/6/2.
 */
public class ApiManager {
    private static com.squareup.okhttp.OkHttpClient getOkHttpClient() {
        com.squareup.okhttp.OkHttpClient okHttpClient = new com.squareup.okhttp.OkHttpClient();
        okHttpClient.setConnectTimeout(100000, TimeUnit.SECONDS);
        return okHttpClient;
    }

    private static Retrofit builder = new Retrofit.Builder()
            .baseUrl(Constant.URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    public interface LoginService {
        @Headers({"Content-Type: application/json"})
        @POST(Constant.LOGIN)
        Observable<LoginResponse> Login(@Body LoginRequest request);
    }


    public interface DevListService {
        @GET(Constant.DEV)
        Observable<List<DevListRespone>> getDevList(@Header("Authorization") String jwt);
    }

    public interface DeviceInfoService {
        @GET(Constant.DEV + "/{id}")
        Observable<ZigbeeHouse> getZigbeeHouse(
                @Header("Authorization") String jwt,
                @Path("id") String id);
    }
    public interface UpdateService {
        @Headers({"Content-Type: application/json"})
        @POST(Constant.UPDATE)
        Observable<UpdateRespone> updateWeater(@Body UpdateAirRequest request);

    }
    private static LoginService LOGIN = builder.create(LoginService.class);
    private static DevListService DEVLISTSERVICE = builder.create(DevListService.class);

    private static DeviceInfoService DEVINFO = builder.create(DeviceInfoService.class);
    private static UpdateService UPDATE = builder.create(UpdateService.class);

    public static DevListService getDevListService() {
        return DEVLISTSERVICE;
    }

    public static LoginService getLoginService() {
        return LOGIN;
    }

    public static DeviceInfoService getDevInfoService() {
        return DEVINFO;
    }

    public static UpdateService getDevUpdateService() {
        return UPDATE;
    }


    private static Retrofit weater = new Retrofit.Builder()
            .baseUrl(Constant.WEATER_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();


    public interface WeaterInfoService {
        @GET(Constant.TAI_ZHOU_WEATER)
        Observable<Weater> getWeater();
    }

    private static WeaterInfoService WEATERINFO = weater.create(WeaterInfoService.class);

    public static WeaterInfoService getWeaterInfo() {
        return WEATERINFO;
    }


}
