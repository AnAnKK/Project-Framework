package com.runda.projectframework.app.others;

import android.app.ActivityManager;
import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * author:  RD_CY
 * date:    2018/8/27
 * version: v1.0
 * description: glide优化缓存策略
 */
@GlideModule
public class RDGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            manager.getMemoryInfo(memoryInfo);
            builder.setDefaultRequestOptions(RequestOptions.formatOf(memoryInfo.lowMemory ?
                    DecodeFormat.PREFER_RGB_565 : DecodeFormat.PREFER_ARGB_8888));
        }
    }
}
