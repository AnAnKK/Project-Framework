package com.runda.projectframework.app.others;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.luck.picture.lib.engine.CacheResourcesEngine;

import java.io.File;

/**
 * @author：luck
 * @date：2020-03-24 09:48
 * @describe：GlideCacheEngine
 */
public class GlideCacheEngine implements CacheResourcesEngine {
    /**
     * glide版本号,请根据用户集成为准 这里只是模拟
     */
    private final static int GLIDE_VERSION = 4;

    @Override
    public String onCachePath(Context context, String url) {
        File cacheFile;
        if (GLIDE_VERSION >= 4) {
            // Glide 4.x
            cacheFile = getCacheFileTo4x(context, url);
        } else {
            // Glide 3.x
            cacheFile = getCacheFileTo3x(context, url);
        }
        return cacheFile != null ? cacheFile.getAbsolutePath() : "";
    }


    private GlideCacheEngine() {
    }

    private static GlideCacheEngine instance;

    public static GlideCacheEngine createCacheEngine() {
        if (null == instance) {
            synchronized (GlideCacheEngine.class) {
                if (null == instance) {
                    instance = new GlideCacheEngine();
                }
            }
        }
        return instance;
    }

    public  File getCacheFileTo4x(Context context, String url) {
        try {
            return Glide.with(context).downloadOnly().load(url).submit().get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据url获取图片缓存
     * Glide 3.x请调用此方法
     * 注意：此方法必须在子线程中进行
     *
     * @param context
     * @param url
     * @return
     */
    public  File getCacheFileTo3x(Context context, String url) {
        try {
            return Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
