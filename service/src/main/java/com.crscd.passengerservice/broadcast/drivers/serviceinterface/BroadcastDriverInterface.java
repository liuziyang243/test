package com.crscd.passengerservice.broadcast.drivers.serviceinterface;

import com.crscd.passengerservice.result.base.ResultMessage;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author lzy
 * Date: 2017/9/14
 * Time: 12:57
 */
public interface BroadcastDriverInterface {
    /*合成广播下发*/
    ResultMessage SyntheticBroadcastSend(String stationName, List<Integer> broadcastZoneNum, String broadcastData, int priority, String recordKey);

    /*合成广播停止*/
    ResultMessage SyntheticBroadcastStop(String stationName, List<Integer> broadcastZoneNum, String recordKey);

    /*人工信源开启(DVD/收音机/车站话筒)*/
    ResultMessage ArtificialSourceOpen(int stationCode, List<Integer> broadcastZoneNum, int sourceNum);

    /*获取广播区名称及编号*/
    Map<Integer, String> GetBroadcastZoneNumandName(int stationCode);

    /*获取广播区占用信源编号及是否被监听的状态*/
//    List<BroadcastZoneInfo> GetBroadcastZoneInfo(int stationCode);

    /*信源占用情况*/
    Map<Integer, Integer> SourceState(int stationCode, List<Integer> sourceNumList);

    /*关闭广播区设备(中断其与信源的连接)*/
//    List<Integer> CloseBroadcastZone(int stationCode, List<Integer> broadcastZoneNum);
    Map<Integer, Boolean> CloseBroadcastZone(int stationCode, List<Integer> broadcastZoneNum);

    /*关闭信源*/
    Map<Integer, Boolean> CloseSource(int stationCode, List<Integer> sourceNum);

    /*车站人工监听*/
    ResultMessage StationBroadcastMonitor(int stationCode, int broadcastNum);

    /*中心人工监听*/
    ResultMessage CentralBroadcastMonitor(int stationCode, int broadcastNum);

    /*车站合成广播试听接口*/
    ResultMessage stationSynthesisBroadcastAudition(int stationCode, String broadcastData);

    /*车站人工信源试听接口*/
    ResultMessage stationManualSourceBroadcastAudition(int station, int sourceNum);

    /*中心合成广播试听接口*/
    ResultMessage centerSynthesisBroadcastAudition(int stationCode, String broadcastData);

    /*中心人工信源试听接口*/
    ResultMessage centerManualSourceBroadcastAudition(int station, int sourceNum);

    /*广播区设备音量调节*/
    ResultMessage SetBroadcastZoneVoice(int stationCode, int broadcastZoneNum, int nVolume);

    /*获取广播区设备音量*/
    int GetBroadcastZoneVoice(int stationCode, int broadcastZoneNum);

    Map<Integer, Integer> GetBroadcastZoneVoice(int stationCode, List<Integer> broadcastZoneNum);

    /*获取专题素材列表*/
    List<String> GetThematicNameList(int stationCode);

    /*专题素材添加*/
    boolean AddThematicMatetial(int stationCode, File file);

    /*回路功放运行状态获取*/
    Map<Integer, Integer> GetPowerAmplifierStatuse(int stationCode, List<Integer> deviceNum);

    /*服务端与控制器通信状态获取*/
    boolean GetCommunicationState(int stationCode);

    /*远程话筒开启*/
    ResultMessage RemoteMicrophoneOpen(int stationCode, List<Integer> broadcastZoneNum);

    /*关闭远程试听*/
    ResultMessage remoteAuditionClose(int stationCode);

    /*关闭一个信源*/
    ResultMessage closeOneSource(int stationCode, int sourceNum);
}
