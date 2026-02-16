package raisetech.student.management.exception;

public class ApiError {
    private String messsage;
    private String code;

    public String getMesssage() {
        return messsage;
    }

    public String getCode() {
        return code;
    }

    public ApiError(String messsage, String code) {
        this.messsage = messsage;
        this.code = code;
    }
}
