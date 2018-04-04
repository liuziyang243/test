package com.crscd.passengerservice.display.ScreenControlServer;

import com.crscd.framework.util.text.JsonUtil;
import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.display.screencontrolserver.business.PlayList;
import com.crscd.passengerservice.multimedia.domainobject.PlayListInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/12/21.
 */
public class PlayListTest {
    PlayList playList = ContextHelper.getPlayList();

    @Test
    public void playListSendTest() {
        PlaylistContentInfo playlistContentInfo = new PlaylistContentInfo();
        playlistContentInfo.setTitle("123.avi");
        playlistContentInfo.setDuration("3000");
        playlistContentInfo.setStart("--");
        playlistContentInfo.setEnd("--");

        List<PlaylistContentInfo> playlistContentInfos = new ArrayList<>();
        playlistContentInfos.add(playlistContentInfo);

        String content = JsonUtil.toJSON(playlistContentInfos);

        PlayListInfo playListInfo = new PlayListInfo();
        playListInfo.setId(123456);
        playListInfo.setVersion("12345678");
        playListInfo.setListType("vid");
        playListInfo.setListName("list1");
        playListInfo.setPlayType("0");
        playListInfo.setContent(content);

        List<PlayListInfo> playListInfos = new ArrayList<>();
        playListInfos.add(playListInfo);

        boolean result = playList.playListSend("Achi River", playListInfos);

    }


}
