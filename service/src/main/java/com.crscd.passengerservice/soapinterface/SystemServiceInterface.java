package com.crscd.passengerservice.soapinterface;

import com.crscd.passengerservice.authority.serviceinterface.AuthorityBusiness;
import com.crscd.passengerservice.config.serviceinterface.SystemConfigInterface;
import com.crscd.passengerservice.config.serviceinterface.SystemInfoInterface;
import com.crscd.passengerservice.log.serviceinterface.OperationLogInterface;
import com.crscd.passengerservice.warning.serviceinterface.WarningServiceInterface;

import javax.jws.WebService;

/**
 * @author lzy
 * Date: 2017/8/18
 * Time: 14:24
 * 其他业务放在这个接口下：
 * 包括用户权限、操作日志记录、设备状态及告警信息管理等
 */
@WebService
public interface SystemServiceInterface
        extends AuthorityBusiness,
        SystemConfigInterface,
        SystemInfoInterface,
        WarningServiceInterface,
        OperationLogInterface {
}
