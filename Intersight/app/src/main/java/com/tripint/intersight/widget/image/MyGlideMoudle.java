package com.tripint.intersight.widget.image;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;
import com.tripint.intersight.common.R;


/**
 * 作者：wangdakuan
 * 主要功能:设置全局图片请求tag
 * 创建时间：2015/11/24 10:19
 */
public class MyGlideMoudle implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        ViewTarget.setTagId(R.id.request_image_view);

        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        builder.setMemoryCache(new
                LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new
                LruBitmapPool(customBitmapPoolSize));
        // set size & external vs. internal
//        int cacheSize100MegaBytes = 104857600;
//        builder.setDiskCache(
//                new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes)); //
//        builder.setDiskCache(
//                new ExternalCacheDiskCacheFactory(context, cacheSize100MegaBytes));
//        // or any other path
//        String downloadDirectoryPath = Environment.getDownloadCacheDirectory().getPath();
//        //默認
//        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, cacheSize100MegaBytes));// In case you want to specify a cache sub folder (i.e. "glidecache")://
        //指定
//        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, "glidecache", cacheSize100MegaBytes));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}
