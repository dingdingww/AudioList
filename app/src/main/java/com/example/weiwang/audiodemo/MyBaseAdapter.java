package com.example.weiwang.audiodemo;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

public class MyBaseAdapter extends BaseQuickAdapter<MediaEntity,BaseViewHolder> implements AutioControl.AutioControlListener, BaseQuickAdapter.OnItemChildClickListener {


    private  AutioControl autioControl;



    public MyBaseAdapter(@Nullable List<MediaEntity> data,AutioControl control){
        super(R.layout.item_audio, data);
        autioControl=control;
        autioControl.setOnAutioControlListener(this);
        setOnItemChildClickListener(this);
    }
//
    @Override
    protected void convert(BaseViewHolder helper, MediaEntity item) {

        TextView startTime=  helper.getView(R.id.tv_start_time);
        TextView endTime=  helper.getView(R.id.tv_end_time);
        TextView title=  helper.getView(R.id.text);
        ImageView play=  helper.getView(R.id.play);
        ImageView pause=  helper.getView(R.id.pause);
        if(item.getPlayStatus()){
            play.setVisibility(View.INVISIBLE);
            pause.setVisibility(View.VISIBLE);
        }else {
            play.setVisibility(View.VISIBLE);
            pause.setVisibility(View.INVISIBLE);
        }
        helper.addOnClickListener(R.id.play);
        helper.addOnClickListener(R.id.pause);

        startTime.setText(item.getStartTime());
        endTime.setText(item.getEndTime());
        DefaultTimeBar timeBar= (DefaultTimeBar) helper.getView(R.id.exo_progress);



        timeBar.addListener(new TimeBar.OnScrubListener() {
            @Override
            public void onScrubStart(TimeBar timeBar, long position) {
            }

            @Override
            public void onScrubMove(TimeBar timeBar, long position) {
                if(startTime!=null){
                    if(autioControl.getPosition()==mData.indexOf(item)){
                        startTime.setText(Util.getStringForTime(autioControl.getFormatBuilder(), autioControl.getFormatter(), position));
                    }else {
                        timeBar.setPosition(0);
                    }
                }
            }

            @Override
            public void onScrubStop(TimeBar timeBar, long position, boolean canceled) {
                if(autioControl!=null){
                    if(autioControl.getPosition()==mData.indexOf(item)){
                        autioControl.seekToTimeBarPosition(position);
                    }else {
                        timeBar.setPosition(0);
                    }
                }
            }
        });

    }

    @Override
    public void setCurPositionTime(int position,long curPositionTime) {

        DefaultTimeBar timeBar= (DefaultTimeBar) getViewByPosition(getRecyclerView(),position,R.id.exo_progress);
        timeBar.setPosition(curPositionTime);
    }

    @Override
    public void setDurationTime(int position,long durationTime) {
        DefaultTimeBar timeBar= (DefaultTimeBar) getViewByPosition(getRecyclerView(),position,R.id.exo_progress);
        timeBar.setDuration(durationTime);
    }

    @Override
    public void setBufferedPositionTime(int position,long bufferedPosition) {
        DefaultTimeBar timeBar= (DefaultTimeBar) getViewByPosition(getRecyclerView(),position,R.id.exo_progress);
        timeBar.setBufferedPosition(bufferedPosition);
    }

    @Override
    public void setCurTimeString(int position,String curTimeString) {
        TextView startTime= (TextView) getViewByPosition(getRecyclerView(),position,R.id.tv_start_time);
        MediaEntity mediaEntity = mData.get(position);
        mediaEntity.setStartTime(curTimeString);
        startTime.setText(curTimeString);
    }

    @Override
    public void isPlay(int position,boolean isPlay) {
        MediaEntity mediaEntity = mData.get(position);
        mediaEntity.setPlayStatus(isPlay);
        ImageView play= (ImageView) getViewByPosition(getRecyclerView(),position,R.id.play);
        ImageView pause= (ImageView) getViewByPosition(getRecyclerView(),position,R.id.pause);
        if(isPlay){
            if(play!=null){
                play.setVisibility(View.INVISIBLE);
            }
            if(pause!=null){
                pause.setVisibility(View.VISIBLE);
            }


        }else {
            if(play!=null){
                play.setVisibility(View.VISIBLE);
            }
            if(pause!=null){
                pause.setVisibility(View.INVISIBLE);
            }


        }
    }

    @Override
    public void setDurationTimeString(int position,String durationTimeString) {
        TextView endTime= (TextView) getViewByPosition(getRecyclerView(),position,R.id.tv_end_time);
        MediaEntity mediaEntity = mData.get(position);
        mediaEntity.setEndTime(durationTimeString);
        endTime.setText(durationTimeString);
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        boolean playNClickIsSame = playNClickIsSame(autioControl.getPosition(), position);
        switch (view.getId()){
            case R.id.play:
                initStatus(autioControl.getPosition(),position);
                autioControl.onPrepare(mData.get(position).getUri());
                autioControl.onStart(position);
                break;
            case R.id.pause:
                if(playNClickIsSame){
                    autioControl.onPause();
                }

                break;
        }
    }

    private boolean playNClickIsSame(int playIndex,int clickIndex){
        return playIndex==clickIndex?true:false;
    }

    private void initStatus(int playIndex,int clickIndex){
        MediaEntity oldEntity = mData.get(playIndex);
//        MediaEntity newEntity = mData.get(clickIndex);
        oldEntity.setPlayStatus(false);
        oldEntity.setStartTime("00:00");

        DefaultTimeBar timeBar= (DefaultTimeBar) getViewByPosition(getRecyclerView(),playIndex,R.id.exo_progress);
        timeBar.setPosition(0);
        timeBar.setBufferedPosition(0);

        TextView startTime= (TextView) getViewByPosition(getRecyclerView(),playIndex,R.id.tv_start_time);
        startTime.setText(oldEntity.getStartTime());

        ImageView oldplay= (ImageView) getViewByPosition(getRecyclerView(),playIndex,R.id.play);

        oldplay.setVisibility(View.VISIBLE);
        ImageView oldpause= (ImageView) getViewByPosition(getRecyclerView(),playIndex,R.id.pause);
        oldpause.setVisibility(View.INVISIBLE);

        ImageView newplay= (ImageView) getViewByPosition(getRecyclerView(),clickIndex,R.id.play);
        newplay.setVisibility(View.INVISIBLE);
        ImageView onewpause= (ImageView) getViewByPosition(getRecyclerView(),clickIndex,R.id.pause);
        onewpause.setVisibility(View.VISIBLE);

    }
}
