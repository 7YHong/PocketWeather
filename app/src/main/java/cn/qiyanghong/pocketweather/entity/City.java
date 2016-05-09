package cn.qiyanghong.pocketweather.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by QYH on 2016/5/7.
 */
@DatabaseTable(tableName = "city")
public class City {
    @DatabaseField
    String province;  //  北京/*省份名称*/
    @DatabaseField
    String city;//   "北京",	/*城市*/
    @DatabaseField
    String district;    //北京"	/*城市/区名称*/
    @DatabaseField
    int id; // 1        /*城市ID*/

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

    @Override
    public String toString(){
        return city+"\t"+district;
    }
}
