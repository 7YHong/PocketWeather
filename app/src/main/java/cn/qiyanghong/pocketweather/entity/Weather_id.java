package cn.qiyanghong.pocketweather.entity;

/**
 * Created by QYH on 2016/5/8.
 */
public class Weather_Id {
    public String getFa() {
        return fa;
    }

    public void setFa(String fa) {
        this.fa = fa;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    String fa,//": "00",	/*天气标识00：晴*/
            fb;//": "53"	/*天气标识53：霾 如果fa不等于fb，说明是组合天气*/
    public boolean isSingle(){
        if (fa.equals(fb)) return true;
        return false;
    }
}
