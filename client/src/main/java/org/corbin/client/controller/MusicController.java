package org.corbin.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.corbin.client.base.controller.BaseClientController;
import org.corbin.client.service.SongInfoService;
import org.corbin.client.service.SongStatisticsDayLogService;
import org.corbin.client.service.UserActiveInfoService;
import org.corbin.client.service.UserInfoService;
import org.corbin.client.vo.music.SongInfoVo;
import org.corbin.client.vo.search.SearchSongVo;
import org.corbin.common.base.Response.ResponseCode;
import org.corbin.common.base.Response.ResponseResult;
import org.corbin.common.base.vo.BaseVo;
import org.corbin.common.entity.SongInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/music")
@RestController
@Slf4j
public class MusicController extends BaseClientController {

    @Autowired
    private SongInfoService songInfoService;
    @Autowired
    private SongStatisticsDayLogService songStatisticsDayLogService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserActiveInfoService userActiveInfoService;


    /**
     * 今日热点推荐，collect：star：listen=5:3:2
     *
     * @return
     */
    @PostMapping("/hot/recommend-list")
    ResponseResult songListRecommendToday(@RequestBody BaseVo vo) {
        recordLoginActiveUpdate(vo);
        Page<SongInfo> page = songInfoService.songListRecommendToday(vo.of());

        Page<SongInfoVo> pageVo = convertWithCollectStatus(vo.getUserId(), page);
        return ResponseResult.newInstance(ResponseCode.SUCC_0, pageVo);

    }


    /**
     * 新歌速递
     *
     * @return
     */
    @PostMapping("/last/song-list")
    ResponseResult newSongList(@RequestBody BaseVo vo) {
        recordLoginActiveUpdate(vo);

        Page<SongInfo> page = songInfoService.getLast30DaysSongList(vo.of());

        Page<SongInfoVo> pageVo = convertWithCollectStatus(vo.getUserId(), page);
        return ResponseResult.newInstance(ResponseCode.SUCC_0, pageVo);
    }

    /**
     * 热门神曲
     *
     * @return
     */
    @PostMapping("/hot/song-list")
    ResponseResult hotSongList(@RequestBody BaseVo vo) {
        recordLoginActiveUpdate(vo);

        Pageable pageable = vo.of();
        Page<SongInfo> page = songInfoService.songListHotToday(pageable);
        Page<SongInfoVo> pageVo = convertWithCollectStatus(vo.getUserId(), page);
        return ResponseResult.newInstance(ResponseCode.SUCC_0, pageVo);

    }


    /**
     * 查找歌曲
     *
     * @return
     */
    @PostMapping("/search")
    public ResponseResult searchSongs(@RequestBody SearchSongVo vo) {
        recordLoginActiveUpdate(vo);

        Page<SongInfo> page = songInfoService.searchSongPage(vo.getSearchValue(),vo.of());
        Page<SongInfoVo> pageVo = convertWithCollectStatus(vo.getUserId(), page);
        return ResponseResult.newInstance(ResponseCode.SUCC_0, pageVo);


    }


    /**
     * 推荐用户喜好的歌曲类型
     *
     * @param vo
     * @return
     */
    @PostMapping("/my/recommend")
    ResponseResult myRecommendSong(@RequestBody BaseVo vo) {
        isUserLogin(vo);
        Page<SongInfo> songInfoPage = songInfoService.recommendSongByTypePage(vo.getUserId(), vo.of());

        Page<SongInfoVo> pageVo = convertWithCollectStatus(vo.getUserId(), songInfoPage);
        /*SongInfoVo.convert2PageVO(songInfoPage);*/
        return ResponseResult.newInstance(ResponseCode.SUCC_0, pageVo);
    }


    /**
     * 格局用户收藏的歌曲的歌手推荐歌手的歌曲
     * 收藏歌曲最多的三名歌手
     *
     * @return
     */
    @PostMapping("/singer-song")
    public ResponseResult mySingerSong(@RequestBody BaseVo vo) {
        isUserLogin(vo);

        Page<SongInfo> songInfoPage = songInfoService.recommendSongBySinger(vo.getUserId(), vo.of());

        Page<SongInfoVo> pageVo = convertWithCollectStatus(vo.getUserId(), songInfoPage);
        return ResponseResult.newInstance(ResponseCode.SUCC_0, pageVo);
    }


}