package com.crscd.passengerservice.soapinterface.implement;

import com.crscd.passengerservice.cctv.dto.CCTVSystemUserInfoDTO;
import com.crscd.passengerservice.cctv.dto.RoundRollingGroupDTO;
import com.crscd.passengerservice.cctv.serviceinterface.CCTVServiceInterfaceImpl;
import com.crscd.passengerservice.result.base.ResultMessage;
import com.crscd.passengerservice.soapinterface.ExosystemServiceInterface;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/18
 * Time: 12:39
 */
@WebService(endpointInterface = "com.crscd.passengerservice.soapinterface.ExosystemServiceInterface")
@HandlerChain(file = "handlersdef.xml")
public class ExosystemServiceInterfaceImpl implements ExosystemServiceInterface {
    private CCTVServiceInterfaceImpl cctvServiceInterface;
    @Resource
    private WebServiceContext context;

    public ExosystemServiceInterfaceImpl() {
    }

    public WebServiceContext getContext() {
        return context;
    }

    public void setCctvServiceInterface(CCTVServiceInterfaceImpl cctvServiceInterface) {
        this.cctvServiceInterface = cctvServiceInterface;
    }

    @Override
    public CCTVSystemUserInfoDTO getCCTVSystemUserInfo() {
        return cctvServiceInterface.getCCTVSystemUserInfo();
    }

    @Override
    public List<RoundRollingGroupDTO> getRoundRollingGroupList(String stationName) {
        return cctvServiceInterface.getRoundRollingGroupList(stationName);
    }

    @Override
    public ResultMessage checkGroupNameExit(String stationName, String groupName) {
        return cctvServiceInterface.checkGroupNameExit(stationName, groupName);
    }

    @Override
    public ResultMessage addRoundRollingGroup(RoundRollingGroupDTO dto) {
        return cctvServiceInterface.addRoundRollingGroup(dto);
    }

    @Override
    public ResultMessage modifyRoundRollingGroup(RoundRollingGroupDTO dto) {
        return cctvServiceInterface.modifyRoundRollingGroup(dto);
    }

    @Override
    public ResultMessage delRoundRollingGroup(long id) {
        return cctvServiceInterface.delRoundRollingGroup(id);
    }
}
