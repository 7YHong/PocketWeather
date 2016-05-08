package cn.qiyanghong.pocketweather.entity;

/**
 * Created by QYH on 2016/5/8.
 */
public class PM {
    String city;//": "苏州",  /*城市*/
    String PM;//": "73",  /*PM2.5指数*/

    public String getPM() {
        return PM;
    }

    public void setPM(String PM) {
        this.PM = PM;
    }

    String AQI;//": "98",    /*空气质量指数*/
    String quality;//": "良", /*空气质量*/
    String PM10;//": "50",/*PM10*/
    String CO;//": "0.79",  /*一氧化碳*/
    String NO2;//": "65",  /*二氧化氮*/
    String O3;//": "28",    /*臭氧*/
    String SO2;//": "41",  /*二氧化硫*/
    String time;//": "2014-12-26 11:48:40"/*更新时间*/

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAQI() {
        return AQI;
    }

    public void setAQI(String AQI) {
        this.AQI = AQI;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getPM10() {
        return PM10;
    }

    public void setPM10(String PM10) {
        this.PM10 = PM10;
    }

    public String getCO() {
        return CO;
    }

    public void setCO(String CO) {
        this.CO = CO;
    }

    public String getNO2() {
        return NO2;
    }

    public void setNO2(String NO2) {
        this.NO2 = NO2;
    }

    public String getO3() {
        return O3;
    }

    public void setO3(String o3) {
        O3 = o3;
    }

    public String getSO2() {
        return SO2;
    }

    public void setSO2(String SO2) {
        this.SO2 = SO2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
