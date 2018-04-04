package com.crscd.passengerservice.authority.dto;

import com.crscd.passengerservice.result.base.BaseMessage;
import com.crscd.passengerservice.result.base.ResultMessage;

/**
 * @author zs
 * @date 2017/8/29
 */
public class UserInfoMessage extends BaseMessage {
    private boolean result;
    private UserInfoDto UserInfoDto;

    private UserInfoMessage() {
    }

    public UserInfoMessage(UserInfoDto UserInfoDto) {
        super();
        this.UserInfoDto = UserInfoDto;
        this.result = true;
    }

    public UserInfoMessage(ResultMessage resultMessage) {
        this.UserInfoDto = null;
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


    public UserInfoDto getUserInfoDto() {
        return UserInfoDto;
    }

    public void setUserInfoDto(UserInfoDto userInfoDto) {
        UserInfoDto = userInfoDto;
    }
}
