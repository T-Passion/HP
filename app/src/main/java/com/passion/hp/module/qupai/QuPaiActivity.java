package com.passion.hp.module.qupai;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aliyun.demo.recorder.AliyunVideoRecorder;
import com.passion.hp.R;
import com.passion.libbase.AbstractBaseActivity;
import com.passion.libbase.constants.RouterPath;
import com.passion.libbase.imp.LayoutId;

/**
 * Created by chaos
 * on 2018/1/9. 10:56
 * 文件描述：
 */
@Route(path = RouterPath.QU_PAI_ACTIVITY)
@LayoutId(R.layout.activity_qu_pai_layout)
public class QuPaiActivity extends AbstractBaseActivity {
    private int max = 60 * 1000;
    private int min = 10 * 1000;

    @Override
    protected void initVars(View view) {
//        AliyunSnapVideoParam recordParam = new AliyunSnapVideoParam.Builder()
//                //设置录制分辨率，目前支持360p，480p，540p，720p
//                .setResulutionMode(AliyunVideoRecorder.re)
//                //设置视频比例，目前支持1:1,3:4,9:16
//                .setRatioMode(AliyunVideoRecorder.RATIO_MODE_9_16)
//                .setRecordMode(RecorderDemo.RECORD_MODE_AUTO) //设置录制模式，目前支持按录，点录和混合模式
//                .setFilterList(eff_dirs) //设置滤镜地址列表,具体滤镜接口接收的是一个滤镜数组
//                .setBeautyLevel(80) //设置美颜度
//                .setBeautyStatus(true) //设置美颜开关
//                .setCameraType(CameraType.FRONT) //设置前后置摄像头
//                .setFlashType(FlashType.ON) // 设置闪光灯模式
//                .setNeedClip(true) //设置是否需要支持片段录制
//                .setMaxDuration(max) //设置最大录制时长 单位毫秒
//                .setMinDuration(min) //设置最小录制时长 单位毫秒
//                .setVideQuality(VideoQuality.EPD) //设置视频质量
//                .setGop(30) //设置关键帧间隔
//                .setVideoBitrate(2000) //设置视频码率，如果不设置则使用视频质量videoQulity参数计算出码率
//                .setSortMode(AliyunSnapVideoParam.SORT_MODE_VIDEO)//设置导入相册过滤选择视频
//                .build();
    }

    @Override
    protected void loadInitDta() {

    }

    public void goRecordClick(View view) {
    }
}
