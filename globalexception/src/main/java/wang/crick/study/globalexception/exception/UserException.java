package wang.crick.study.globalexception.exception;

import wang.crick.study.globalexception.directory.ErrorCode;

public class UserException extends CustomException {
    public UserException(String message) {
        super(ErrorCode.UserIdError.getCode(), message);
    }

    public UserException() {
        super(ErrorCode.UserIdError.getCode(), ErrorCode.UserIdError.getMessage());
    }
}
