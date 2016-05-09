package cn.qiyanghong.pocketweather;

/**
 * Created by QYH on 2016/5/8.
 */
public class RequestErrorException extends Exception {

//    public RequestErrorException(String detailMessage, String reason) {
//        super(detailMessage);
//        this.reason = reason;
//    }
//
//    @Override
//    public String getMessage() {
//        if (reason==null|| reason.equals(""))
//            reason="返回的Errorcode不为0";
//        return reason;
//    }

    public RequestErrorException(String detailMessage) {
        super(detailMessage);
    }
}
