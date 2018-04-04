package com.crscd.passengerservice.ctc.domainobject;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/24.
 */
public class CtcBasicMapInfo {
    /**
     * 生成实例的uuid
     */
    private UUID uuid;
    // 版本号，目前来自CTC推送时带有的版本号
    private int listVersion;
    private int bureauCode;
    private LocalDateTime receiveTime;
    // key值采用车次号
    private LinkedHashMap<String, CtcBasicTrainInfo> planMap;

    public CtcBasicMapInfo() {
        uuid = UUID.randomUUID();
        planMap = new LinkedHashMap<>();
        //初始化，代表是组装的各ctc服务器数据；解析数据时会进行更新
        bureauCode = -1;
        receiveTime = LocalDateTime.now();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getListVersion() {
        return listVersion;
    }

    public void setListVersion(int listVersion) {
        this.listVersion = listVersion;
    }

    public int getBureauCode() {
        return bureauCode;
    }

    public void setBureauCode(int bureauCode) {
        this.bureauCode = bureauCode;
    }

    public LinkedHashMap<String, CtcBasicTrainInfo> getPlanMap() {
        return planMap;
    }

    public void setPlanMap(LinkedHashMap<String, CtcBasicTrainInfo> planMap) {
        this.planMap = planMap;
    }

    public LocalDateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getListVersion();
        result = 31 * result + this.getBureauCode();
        result = 31 * result + this.getPlanMap().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.getClass() == obj.getClass()) {
            CtcBasicMapInfo outerObj = (CtcBasicMapInfo) obj;
            return this.getListVersion() == outerObj.getListVersion()
                    && this.getBureauCode() == outerObj.getBureauCode()
                    && this.getPlanMap().equals(outerObj.getPlanMap());
        }
        return false;
    }


}
