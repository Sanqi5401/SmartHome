package linksprite.com.zigbeehouse.module;

/**
 * Created by Administrator on 2017/2/7.
 */

public class UpdateAirRequest {


    /**
     * action : update
     * deviceid : 01ad0253f2
     * apikey : 123e4567-e89b-12d3-a456-426655440000
     * params : {"air":"0"}
     */

    private String action;
    private String deviceid;
    private String apikey;
    /**
     * air : 0
     */

    private ParamsBean params;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        private String air;
        private String air_con;

        public ParamsBean(String air, String air_con) {
            this.air = air;
            this.air_con = air_con;
        }

        public String getAir() {
            return air;
        }

        public void setAir(String air) {
            this.air = air;
        }

        public String getAir_con() {
            return air_con;
        }

        public void setAir_con(String air_con) {
            this.air_con = air_con;
        }
    }
}
