package linksprite.com.zigbeehouse.module;

import static linksprite.com.zigbeehouse.R.string.air_con_1;
import static linksprite.com.zigbeehouse.R.string.air_con_2;

/**
 * Created by Administrator on 2017/2/5.
 */

public class ZigbeeHouse {

    /**
     * _id : 5896d6100725bdf21c7a13b9
     * name : test
     * type : 00
     * deviceid : 000000022a
     * apikey : 18d09360-f0e2-4a1a-aac5-d21d9ea92e92
     * __v : 0
     * params : {"air_con_1":"0","humidity":"30","air":"0","temperature":"10","pm25":"300","air_con_2":"0"}
     * online : false
     * createdAt : 2017-02-05T07:36:48.357Z
     * group : test
     */

    private String _id;
    private String name;
    private String type;
    private String deviceid;
    private String apikey;
    private int __v;
    /**
     * air_con_1 : 0
     * humidity : 30
     * air : 0
     * temperature : 10
     * pm25 : 300
     * air_con_2 : 0
     */

    private ParamsBean params;
    private boolean online;
    private String createdAt;
    private String group;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public static class ParamsBean {
        private String air_con;
        private String humidity;
        private String air;
        private String temperature;
        private String pm25;

        public String getAir_con() {
            return air_con;
        }

        public void setAir_con(String air_con) {
            this.air_con = air_con;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getAir() {
            return air;
        }

        public void setAir(String air) {
            this.air = air;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        @Override
        public String toString() {
            return "ParamsBean{" +
                    "air_con='" + air_con + '\'' +
                    ", humidity='" + humidity + '\'' +
                    ", air='" + air + '\'' +
                    ", temperature='" + temperature + '\'' +
                    ", pm25='" + pm25 + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ZigbeeHouse{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", apikey='" + apikey + '\'' +
                ", __v=" + __v +
                ", params=" + params +
                ", online=" + online +
                ", createdAt='" + createdAt + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
