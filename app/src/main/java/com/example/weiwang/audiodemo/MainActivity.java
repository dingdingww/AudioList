package com.example.weiwang.audiodemo;

import android.drm.DrmUtils;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

    boolean show;

    private TextView tvShow;
    private TextView tvShow2;
    TimeBar timeBar;
    private boolean scrubbing;
    List<MediaEntity> mediaEntityList=new ArrayList<>();
    List<MediaSource> sourceList=new ArrayList<>();
    AutioControl control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaEntityList.add(new MediaEntity("http://5.595818.com/2015/ring/000/140/6731c71dfb5c4c09a80901b65528168b.mp3","00:00",
                "00:49",false));
        mediaEntityList.add(new MediaEntity("http://file.kuyinyun.com/group1/M00/90/B7/rBBGdFPXJNeAM-nhABeMElAM6bY151.mp3","00:00",
                "00:22",false));

        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        AutioControl autioControl=new AutioControl(this);
        MyBaseAdapter myBaseAdapter = new MyBaseAdapter(mediaEntityList, autioControl);
        myBaseAdapter.bindToRecyclerView(recyclerView);
        DefaultTimeBar  exo_progress1=findViewById(R.id.exo_progress1);
        DefaultTimeBar  exo_progress2=findViewById(R.id.exo_progress2);
        exo_progress2.setPosition(0);
        exo_progress2.setDuration(1);
//        initView();
//        control=new AutioControl(this);
//        control.setOnAutioControlListener(new AutioControl.AutioControlListener() {
//            @Override
//            public void setCurPositionTime(long curPositionTime) {
//                timeBar.setPosition(curPositionTime);
//            }
//
//            @Override
//            public void setDurationTime(long durationTime) {
//                timeBar.setDuration(durationTime);
//            }
//
//            @Override
//            public void setBufferedPositionTime(long bufferedPosition) {
//                timeBar.setBufferedPosition(bufferedPosition);
//            }
//
//            @Override
//            public void setCurTimeString(String curTimeString) {
//                tvShow.setText(curTimeString);
//            }
//
//            @Override
//            public void isPlayEnd(boolean isPlayEnd) {
//
//            }
//
//            @Override
//            public void setDurationTimeString(String durationTimeString) {
//                tvShow2.setText(durationTimeString);
//            }
//        });
//        control.onPrepare("http://file.kuyinyun.com/group1/M00/90/B7/rBBGdFPXJNeAM-nhABeMElAM6bY151.mp3");;
//        adGroupTimesMs = new long[0];
//        playedAdGroups = new boolean[0];
//        extraAdGroupTimesMs = new long[0];
//        extraPlayedAdGroups = new boolean[0];
//        initTimeShowFormat();
//        controlDispatcher = new com.google.android.exoplayer2.DefaultControlDispatcher();

//        period = new Timeline.Period();
//        window = new Timeline.Window();
        //获取player的一个实例，大多数情况可以直接使用 DefaultTrackSelector
        // DefaultTrackSelector 该类可以对当前播放的音视频进行操作，比如设置音轨，设置约束曲目选择，禁用渲染器
//        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
//        DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(this, "audio/mpeg");
//        //创建一个媒体连接源
//        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();
//        final ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(defaultDataSourceFactory)
//                //创建一个播放数据源
//                .createMediaSource(Uri.parse("http://5.595818.com/2015/ring/000/140/6731c71dfb5c4c09a80901b65528168b.mp3"));
//        final ExtractorMediaSource mediaSource2 = new ExtractorMediaSource.Factory(defaultDataSourceFactory)
//                //创建一个播放数据源
//                .createMediaSource(Uri.parse("http://file.kuyinyun.com/group1/M00/90/B7/rBBGdFPXJNeAM-nhABeMElAM6bY151.mp3"));
//        //把数据源添加到concatenatingMediaSource里面，相当于添加到一个播放池
//        sourceList.add(mediaSource);
//        concatenatingMediaSource.addMediaSource(mediaSource);
//        simpleExoPlayer.setPlayWhenReady(false);
//        //把Player和数据源关联起来
////        simpleExoPlayer.prepare(concatenatingMediaSource);
//        simpleExoPlayer.prepare(mediaSource);
//        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(show){
//
////                    if (simpleExoPlayer.getPlaybackState() == Player.STATE_IDLE) {
////                    } else if (simpleExoPlayer.getPlaybackState() == Player.STATE_ENDED) {
//////                        controlDispatcher.dispatchSeekTo(simpleExoPlayer, simpleExoPlayer.getCurrentWindowIndex(), C.TIME_UNSET);
////                    }
////                    controlDispatcher.dispatchSetPlayWhenReady(simpleExoPlayer, true);
//                    control.onPause();
//                }else {
//
//                    control.onStart();;
////                    controlDispatcher.dispatchSetPlayWhenReady(simpleExoPlayer, false);
//                }
//                show=!show;
//            }
//        });
//        initListener();
    }

//    private void initListener() {
//        timeBar.addListener(new TimeBar.OnScrubListener() {
//            @Override
//            public void onScrubStart(TimeBar timeBar, long position) {
//
//            }
//            @Override
//            public void onScrubMove(TimeBar timeBar, long position) {
//                if (tvShow != null) {
//                    tvShow.setText(Util.getStringForTime(control.getFormatBuilder(), control.getFormatter(), position));
//                }
//            }
//
//            @Override
//            public void onScrubStop(TimeBar timeBar, long position, boolean canceled) {
//                control.seekToTimeBarPosition(position);
//            }
//        });
//
////        simpleExoPlayer.addListener(new Player.EventListener() {
////            @Override
////            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
////                int currentWindowIndex = simpleExoPlayer.getCurrentWindowIndex();
////                Log.e("www","currentWindowIndex:"+currentWindowIndex);
////            }
////
////            @Override
////            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
////                int currentWindowIndex = simpleExoPlayer.getCurrentWindowIndex();
////                Log.e("www","onTracksChanged:"+currentWindowIndex);
////            }
////
////            @Override
////            public void onLoadingChanged(boolean isLoading) {
////
////                Log.e("www","onLoadingChanged:"+isLoading);
////                if(isLoading){
////                    handler.postDelayed(loadStatusRunable,3000);
////                }
////            }
////
////            @Override
////            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
////                int currentWindowIndex = simpleExoPlayer.getCurrentWindowIndex();
////                Log.e("www","onPlayerStateChanged:"+currentWindowIndex);
////            }
////
////            @Override
////            public void onRepeatModeChanged(int repeatMode) {
////                int currentWindowIndex = simpleExoPlayer.getCurrentWindowIndex();
////                Log.e("www","onRepeatModeChanged:"+currentWindowIndex);
////            }
////
////            @Override
////            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
////                int currentWindowIndex = simpleExoPlayer.getCurrentWindowIndex();
////                Log.e("www","onShuffleModeEnabledChanged:"+currentWindowIndex);
////            }
////
////            @Override
////            public void onPlayerError(ExoPlaybackException error) {
////                Log.e("www","onPlayerError:");
////            }
////
////            @Override
////            public void onPositionDiscontinuity(int reason) {
////                int currentWindowIndex = simpleExoPlayer.getCurrentWindowIndex();
////                Log.e("www","onPositionDiscontinuity:"+currentWindowIndex);
////            }
////
////            @Override
////            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
////                int currentWindowIndex = simpleExoPlayer.getCurrentWindowIndex();
////                Log.e("www","onPlaybackParametersChanged:"+currentWindowIndex);
////
////            }
////
////            @Override
////            public void onSeekProcessed() {
////                int currentWindowIndex = simpleExoPlayer.getCurrentWindowIndex();
////                Log.e("www","onSeekProcessed:"+currentWindowIndex);
////            }
////        });
//
//    }
//
//    private void seekToTimeBarPosition(long positionMs) {
//        Timeline timeline = simpleExoPlayer.getCurrentTimeline();
//        int windowIndex;
//        if(!timeline.isEmpty()){
//            int windowCount = timeline.getWindowCount();
//            windowIndex = 0;
//            while (true) {
//                long windowDurationMs = timeline.getWindow(windowIndex, window).getDurationMs();
//                if (positionMs < windowDurationMs) {
//                    break;
//                } else if (windowIndex == windowCount - 1) {
//                    // Seeking past the end of the last window should seek to the end of the timeline.
//                    positionMs = windowDurationMs;
//                    break;
//                }
//                positionMs -= windowDurationMs;
//                windowIndex++;
//            }
//        } else {
//            windowIndex = simpleExoPlayer.getCurrentWindowIndex();
//        }
//        boolean dispatched = controlDispatcher.dispatchSeekTo(simpleExoPlayer, windowIndex, positionMs);
//        if (!dispatched) {
//            handler.post(loadStatusRunable);
//        }
//    }


//    private void initView() {
//        tvShow = findViewById(R.id.show);
//        tvShow2 = findViewById(R.id.show2);
//        timeBar=findViewById(R.id.exo_progress);
//       findViewById(R.id.pause).setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               control.onPause();
////               if(  simpleExoPlayer.getPlaybackState()==Player.STATE_READY){
////
////               }
//           }
//       });
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        control.release();
    }

    private void initTimeShowFormat() {
//        formatBuilder = new StringBuilder();
//        formatter = new Formatter(formatBuilder, Locale.getDefault());
    }



//    Handler handler=new Handler(){};
//
//    Runnable loadStatusRunable= new Runnable() {
//        @Override
//        public void run() {
//            long durationUs = 0;
//            int adGroupCount = 0;
//            long currentWindowTimeBarOffsetMs = 0;
//            Timeline currentTimeline = simpleExoPlayer.getCurrentTimeline();
////            Timeline timeline = player.getCurrentTimeline();
//            if (!currentTimeline.isEmpty()) {
//                int currentWindowIndex = simpleExoPlayer.getCurrentWindowIndex();
//                int firstWindowIndex = currentWindowIndex;
//                int lastWindowIndex = currentWindowIndex;
//                for (int i = firstWindowIndex; i <= lastWindowIndex; i++) {
//                    if (i == currentWindowIndex) {
//                        currentWindowTimeBarOffsetMs = C.usToMs(durationUs);
//                    }
//                    currentTimeline.getWindow(i, window);
//                    if (window.durationUs == C.TIME_UNSET) {
////                       /**/ Assertions.checkState(!multiWindowTimeBar);
//                        break;
//                    }
//                    for (int j = window.firstPeriodIndex; j <= window.lastPeriodIndex; j++) {
//                        currentTimeline.getPeriod(j, period);
//                        int periodAdGroupCount = period.getAdGroupCount();
//                        for (int adGroupIndex = 0; adGroupIndex < periodAdGroupCount; adGroupIndex++) {
//                            long adGroupTimeInPeriodUs = period.getAdGroupTimeUs(adGroupIndex);
//                            if (adGroupTimeInPeriodUs == C.TIME_END_OF_SOURCE) {
//                                if (period.durationUs == C.TIME_UNSET) {
//                                    // Don't show ad markers for postrolls in periods with unknown duration.
//                                    continue;
//                                }
//                                adGroupTimeInPeriodUs = period.durationUs;
//                            }
//                            long adGroupTimeInWindowUs = adGroupTimeInPeriodUs + period.getPositionInWindowUs();
//                            if (adGroupTimeInWindowUs >= 0 && adGroupTimeInWindowUs <= window.durationUs) {
//                                if (adGroupCount == adGroupTimesMs.length) {
//                                    int newLength = adGroupTimesMs.length == 0 ? 1 : adGroupTimesMs.length * 2;
//                                    adGroupTimesMs = Arrays.copyOf(adGroupTimesMs, newLength);
//                                    playedAdGroups = Arrays.copyOf(playedAdGroups, newLength);
//                                }
//                                adGroupTimesMs[adGroupCount] = C.usToMs(durationUs + adGroupTimeInWindowUs);
//                                playedAdGroups[adGroupCount] = period.hasPlayedAdGroup(adGroupIndex);
//                                adGroupCount++;
//                            }
//                        }
//                    }
//                    durationUs += window.durationUs;
//                }
//            }
////            currentTimeline.getWindow(simpleExoPlayer.getCurrentWindowIndex(),window);
//
//             durationUs = C.usToMs(window.durationUs);
//
//            long  curtime = currentWindowTimeBarOffsetMs + simpleExoPlayer.getContentPosition();
//            long  bufferedPosition = currentWindowTimeBarOffsetMs + simpleExoPlayer.getContentBufferedPosition();
//
//            tvShow.setText(""+  Util.getStringForTime(formatBuilder, formatter, curtime)+"     " +
//                    "     end:"+ Util.getStringForTime(formatBuilder, formatter, durationUs));
//
//            if (timeBar != null) {
//                int extraAdGroupCount = extraAdGroupTimesMs.length;
//                int totalAdGroupCount = adGroupCount + extraAdGroupCount;
//                if (totalAdGroupCount > adGroupTimesMs.length) {
//                    adGroupTimesMs = Arrays.copyOf(adGroupTimesMs, totalAdGroupCount);
//                    playedAdGroups = Arrays.copyOf(playedAdGroups, totalAdGroupCount);
//                }
//                System.arraycopy(extraAdGroupTimesMs, 0, adGroupTimesMs, adGroupCount, extraAdGroupCount);
//                System.arraycopy(extraPlayedAdGroups, 0, playedAdGroups, adGroupCount, extraAdGroupCount);
//                timeBar.setAdGroupTimesMs(adGroupTimesMs, playedAdGroups, totalAdGroupCount);
//            }
//
//            handler.removeCallbacks(loadStatusRunable);
//            int playbackState=simpleExoPlayer==null?Player.STATE_IDLE:simpleExoPlayer.getPlaybackState();
//            if(timeBar!=null){
//                timeBar.setBufferedPosition(bufferedPosition);
//                timeBar.setPosition(curtime);
//                timeBar.setDuration(durationUs);
//            }
//            //播放器未开始播放后者播放器播放结束
//            if(playbackState!=Player.STATE_IDLE&&playbackState!=Player.STATE_ENDED){
//                long delayMs=0;
//                //当正在播放状态时
//                if(simpleExoPlayer.getPlayWhenReady()&&playbackState==Player.STATE_READY){
//                    float playBackSpeed = simpleExoPlayer.getPlaybackParameters().speed;
//                    if(playBackSpeed<=0.1f){
//                        delayMs=1000;
//                    }else if(playBackSpeed<=5f){
//
//                        //中间更新周期时间
//                        long mediaTimeUpdatePeriodMs= 1000 / Math.max(1, Math.round(1 / playBackSpeed));
//                        //当前进度时间与中间更新周期之间的多出的不足一个中间更新周期时长的时间
//                        long surplusTimeMs = curtime % mediaTimeUpdatePeriodMs;
//                        //播放延迟时间
//                        long mediaTimeDelayMs=mediaTimeUpdatePeriodMs-surplusTimeMs;
//                        if(mediaTimeDelayMs<(mediaTimeUpdatePeriodMs/5)){
//                            mediaTimeDelayMs+=mediaTimeUpdatePeriodMs;
//                        }
//                        delayMs=playBackSpeed==1?mediaTimeDelayMs:(long) (mediaTimeDelayMs/playBackSpeed);
//                        Log.e("www","playBackSpeed<=5:"+delayMs);
//                    }else {
//                        delayMs=200;
//                    }
//                }else {
//                    //当暂停状态时
//                    delayMs=1000;
//                }
//                handler.postDelayed(this,delayMs);
//            }else {
//                show=true;
//                Toast.makeText(MainActivity.this, "播放完结", Toast.LENGTH_SHORT).show();
//            }
//
//
//        }
//    };

}
