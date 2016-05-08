package cn.qiyanghong.pocketweather;

/**
 * Created by QYH on 2016/5/8.
 */
public class RequestErrorException extends Exception {
    @Override
    public String getMessage() {
        return "返回的Errorcode不为0";
    }
}
