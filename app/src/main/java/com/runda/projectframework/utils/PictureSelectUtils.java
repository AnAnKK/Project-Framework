package com.runda.projectframework.utils;

import android.content.pm.ActivityInfo;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.others.GlideCacheEngine;
import com.runda.projectframework.app.others.GlideEngine;

import java.util.List;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/9/17 11:30
 * @Version:        1.0
 */
public class PictureSelectUtils {


    /**
     *  图片选择(相册)
     * @param activity
     * @param listChoosedImages 已选择的图片
     * @param maxNum    最大选择数量
     * @param minNum    最小选择数量
     * @param singleDirectReturn  单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
     * @param pictureConfig 单选/多选
     * @param isShowCameraButton    是否显示拍照
     * @param isCrop    是否剪裁
     * @param isCompress    是否压缩
     */
    public static void selectImages(BaseActivity activity, List<LocalMedia> listChoosedImages,int maxNum,int minNum,int pictureConfig,boolean singleDirectReturn,boolean isShowCameraButton,
                                         boolean isCrop,boolean isCompress,OnResultCallbackListener<LocalMedia> listener){
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture_Sina_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white .style v2.3.3后 建议使用setPictureStyle()动态方式
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .maxSelectNum(maxNum)// 最大图片选择数量
                .minSelectNum(minNum)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高,默认为false
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                .selectionMode(pictureConfig)// 多选 or 单选 PictureConfig.MULTIPLE : PictureConfig.SINGLE
                .isSingleDirectReturn(singleDirectReturn)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(true)// 是否可预览视频
                .isEnablePreviewAudio(true) // 是否可播放音频
                .isCamera(isShowCameraButton)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(isCrop)// 是否裁剪
                .isCompress(isCompress)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .selectionData(listChoosedImages)// 是否传入已选图片
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .forResult(listener);
    }


    /**
     * 拍照
     * @param activity
     * @param isCrop
     * @param isCompress
     * @param singleDirectReturn
     * @param listener
     */
    public static void takePhoto(BaseActivity activity,boolean isCrop, boolean isCompress,boolean singleDirectReturn,OnResultCallbackListener<LocalMedia> listener){
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .theme(R.style.picture_Sina_style)// 主题样式设置 具体参考 values/styles
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .maxSelectNum(1)// 最大图片选择数量
                .isUseCustomCamera(false)// 是否使用自定义相机
                .minSelectNum(1)// 最小选择数量
                .isSingleDirectReturn(singleDirectReturn)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高，默认为true
                .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高，默认false
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选  PictureConfig.MULTIPLE : PictureConfig.SINGLE
                .loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())// 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(false)// 是否可预览视频
                .isEnablePreviewAudio(false) // 是否可播放音频
                .isEnableCrop(isCrop)// 是否裁剪
                .isCompress(isCompress)// 是否压缩
                .compressQuality(100)// 图片压缩后输出质量
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片;
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(listener);
    }

}
