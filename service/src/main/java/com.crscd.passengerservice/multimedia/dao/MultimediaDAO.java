package com.crscd.passengerservice.multimedia.dao;

import com.crscd.framework.orm.DataSet;
import com.crscd.passengerservice.multimedia.domainobject.PlayListInfo;
import com.crscd.passengerservice.multimedia.domainobject.PlayListSendingStatusInfo;
import com.crscd.passengerservice.multimedia.po.PlayListInfoBean;
import com.crscd.passengerservice.multimedia.po.PlayListSendingStatusBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/11/23.
 */
public class MultimediaDAO {
    //数据库
    private DataSet oracleDataSet;

    public void setOracleDataSet(DataSet oracleDataSet) {
        this.oracleDataSet = oracleDataSet;
    }

    /**
     * 插入播放列表
     *
     * @param playListInfo
     * @return
     */
    public boolean insertPlayListInfoBean(PlayListInfo playListInfo) {
        PlayListInfoBean playListInfoBean = new PlayListInfoBean();
        playListInfoBean.setListName(playListInfo.getListName());
        playListInfoBean.setVersion(playListInfo.getVersion());
        playListInfoBean.setListType(playListInfo.getListType());
        playListInfoBean.setPlayType(playListInfo.getPlayType());
        String content = playListInfo.getContent();
        List<String> contentList = splitPlayListContent(content);
        if (null == contentList) {
            return false;
        } else {
            playListInfoBean.setContent1(contentList.get(0));
            playListInfoBean.setContent2(contentList.get(1));
        }

        return oracleDataSet.insert(playListInfoBean);
    }

    /**
     * 更新播放列表
     *
     * @param playListInfo
     * @return
     */
    public boolean updatePlanListInfoBean(PlayListInfo playListInfo) {
        int id = playListInfo.getId();
        PlayListInfoBean playListInfoBean = oracleDataSet.select(PlayListInfoBean.class, "id = ?", id);
        playListInfoBean.setVersion(playListInfo.getVersion());
        playListInfoBean.setListType(playListInfo.getListType());
        playListInfoBean.setPlayType(playListInfo.getPlayType());
        String content = playListInfo.getContent();
        List<String> contentList = splitPlayListContent(content);
        if (null == contentList) {
            return false;
        } else {
            playListInfoBean.setContent1(contentList.get(0));
            playListInfoBean.setContent2(contentList.get(1));
        }

        return oracleDataSet.update(playListInfoBean);
    }

    /**
     * 删除播放列表
     *
     * @param playListId
     * @return
     */
    public boolean deletePlayListInfoBean(int playListId) {
        return oracleDataSet.delete(PlayListInfoBean.class, "id = ?", playListId);
    }

    /**
     * 按播放列表类型获取播放列表
     *
     * @param listType
     * @return
     */
    public List<PlayListInfo> getPlayListInfosByListType(String listType) {
        List<PlayListInfoBean> playListInfoBeans = oracleDataSet.selectListWithCondition(PlayListInfoBean.class, "listType = ?", listType);
        List<PlayListInfo> playListInfos = new ArrayList<>();
        for (PlayListInfoBean bean : playListInfoBeans) {
            PlayListInfo playListInfo = new PlayListInfo(bean);
            playListInfos.add(playListInfo);
        }
        return playListInfos;
    }

    /**
     * 按播放列表ID获取播放列表
     *
     * @param id
     * @return
     */
    public PlayListInfo getPlayListInfoById(int id) {
        PlayListInfoBean playListInfoBean = oracleDataSet.select(PlayListInfoBean.class, "id = ?", id);
        PlayListInfo playListInfo = new PlayListInfo(playListInfoBean);
        return playListInfo;
    }

    /**
     * 存储播放列表下发状态
     *
     * @param playListSendingStatusInfo
     * @return
     */
    public boolean insertPlayListSendingStatus(PlayListSendingStatusInfo playListSendingStatusInfo) {
        PlayListSendingStatusBean playListSendingStatusBean = PlayListSendingStatusInfoToBean(playListSendingStatusInfo);
        return oracleDataSet.insert(playListSendingStatusBean);
    }

    /**
     * 更新播放列表下发状态
     *
     * @param playListSendingStatusInfo
     * @return
     */
    public boolean updatePlayListSendingStatus(PlayListSendingStatusInfo playListSendingStatusInfo) {
        PlayListSendingStatusBean playListSendingStatusBean = PlayListSendingStatusInfoToBean(playListSendingStatusInfo);
        int playListId = playListSendingStatusInfo.getPlayListId();
        return oracleDataSet.update(playListSendingStatusBean, "playListId = ?", playListId);
    }

    /**
     * 读取所有播放列表下发状态
     *
     * @return
     */
    public List<PlayListSendingStatusInfo> selectAllPlayListSendingStatus() {
        List<PlayListSendingStatusBean> beanList = oracleDataSet.selectList(PlayListSendingStatusBean.class);
        List<PlayListSendingStatusInfo> infoList = new ArrayList<>();
        for (PlayListSendingStatusBean bean : beanList) {
            infoList.add(PlayListSendingStatusBeanToInfo(bean));
        }
        return infoList;
    }

    /**
     * 根据列表编号读取播放列表下发状态
     *
     * @param playListId
     * @return
     */
    public List<PlayListSendingStatusInfo> selectPlayListSendingStatusByFileName(String playListId) {
        List<PlayListSendingStatusBean> beanList = oracleDataSet.selectListWithCondition(PlayListSendingStatusBean.class, "playListId = ?", playListId);
        List<PlayListSendingStatusInfo> infoList = new ArrayList<>();
        for (PlayListSendingStatusBean bean : beanList) {
            infoList.add(PlayListSendingStatusBeanToInfo(bean));
        }
        return infoList;
    }

    /*播放列表下发状态Info转Bean*/
    private PlayListSendingStatusBean PlayListSendingStatusInfoToBean(PlayListSendingStatusInfo playListSendingStatusInfo) {
        PlayListSendingStatusBean playListSendingStatusBean = new PlayListSendingStatusBean();
        playListSendingStatusBean.setPlayListId(playListSendingStatusInfo.getPlayListId());
        playListSendingStatusBean.setVersion(playListSendingStatusInfo.getVersion());
        playListSendingStatusBean.setServerIp(playListSendingStatusInfo.getServerIp());
        playListSendingStatusBean.setSendingStatus(playListSendingStatusInfo.getSendingStatus());
        return playListSendingStatusBean;
    }

    /*播放列表下发状态Bean转Info*/
    private PlayListSendingStatusInfo PlayListSendingStatusBeanToInfo(PlayListSendingStatusBean playListSendingStatusBean) {
        PlayListSendingStatusInfo playListSendingStatusInfo = new PlayListSendingStatusInfo();
        playListSendingStatusInfo.setPlayListId(playListSendingStatusBean.getPlayListId());
        playListSendingStatusInfo.setVersion(playListSendingStatusBean.getVersion());
        playListSendingStatusInfo.setServerIp(playListSendingStatusBean.getServerIp());
        playListSendingStatusInfo.setSendingStatus(playListSendingStatusBean.getSendingStatus());
        return playListSendingStatusInfo;
    }

    /*播放列表内容长度限制为7998个字符
     * 当播放列表内容小于4000字符存入content1字段
     * 当播放列表内容大于等于4000字符小于7999字符，将字段拆为两个字符串
     * 分别存入content1和content2*/
    private List<String> splitPlayListContent(String content) {
        List<String> contentList = new ArrayList<>();
        int contentSize = content.length();
        if (contentSize < 4000) {
            contentList.add(content);
            contentList.add("");
        } else if ((4000 <= contentSize) && (contentSize < 7999)) {
            contentList.add(content.substring(0, 3998));
            contentList.add(content.substring(3999, contentSize - 1));
        } else {
            return null;
        }

        return contentList;
    }

}
