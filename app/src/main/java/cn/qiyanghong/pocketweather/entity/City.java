package cn.qiyanghong.pocketweather.entity;

/**
 * Created by QYH on 2016/5/7.
 */
public class City {
    int id; // 1        /*城市ID*/
    String province;  //  北京/*省份名称*/
    String city;//   "北京",	/*城市*/
    String district;    //北京"	/*城市/区名称*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
