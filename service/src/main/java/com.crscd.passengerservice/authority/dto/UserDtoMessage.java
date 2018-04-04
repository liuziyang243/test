package com.crscd.passengerservice.authority.dto;

import com.crscd.passengerservice.result.base.BaseMessage;
import com.crscd.passengerservice.result.base.ResultMessage;

/**
 * @author zs
 * @date 2017/8/29
 */
public class UserDtoMessage extends BaseMessage {
    private boolean result;
    private UserDto userDto;

    private UserDtoMessage() {
    }

    public UserDtoMessage(UserDto userDto) {
        super();
        this.userDto = userDto;
        this.result = true;
    }

    public UserDtoMessage(ResultMessage resultMessage) {
        this.userDto = null;
        this.result = resultMessage.getResult();
        this.setMessage(resultMessage.getMessage());
        this.setStatusCode(resultMessage.getStatusCode());
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
