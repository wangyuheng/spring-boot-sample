package wang.crick.study.allinone.restful;


/**
 * restful 返回值
 *
 * Created by hemo on 2017/11/18.
 */
public class RestApiResponse<T> {

    private static final int SUCCESS_CODE = 10000;

    private T data;
    private int code;
    private String message;

    public static <T> RestApiResponse<T> success(T data){
        RestApiResponse<T> response = new RestApiResponse<>();
        response.setCode(SUCCESS_CODE);
        response.setData(data);
        return response;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
