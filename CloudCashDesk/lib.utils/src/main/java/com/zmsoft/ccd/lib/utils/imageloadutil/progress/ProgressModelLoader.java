package com.zmsoft.ccd.lib.utils.imageloadutil.progress;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.InputStream;

/**
 * @author DangGui
 * @create 2016/12/28.
 */
public class ProgressModelLoader implements StreamModelLoader<String> {

    private final ModelCache<String, String> mModelCache;
    private ProgressUIListener mProListener;

    public ProgressModelLoader(ProgressUIListener listener) {
        this(null, listener);
    }

    public ProgressModelLoader(ModelCache<String, String> modelCache) {
        this(modelCache, null);
    }


    public ProgressModelLoader(ModelCache<String, String> modelCache, ProgressUIListener listener) {
        this.mModelCache = modelCache;
        this.mProListener = listener;
    }


    @Override
    public DataFetcher<InputStream> getResourceFetcher(String model, int width, int height) {
        String result = null;
        if (mModelCache != null) {
            result = mModelCache.get(model, width, height);
        }
        if (result == null) {
            result = model;
            if (mModelCache != null) {
                mModelCache.put(model, width, height, result);
            }
        }
        return new ProgressDataFetcher(result, mProListener);
    }

    public static class Factory implements ModelLoaderFactory<String, InputStream> {

        private final ModelCache<String, String> mModelCache = new ModelCache<>(500);

        @Override
        public ModelLoader<String, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new ProgressModelLoader(mModelCache);
        }

        @Override
        public void teardown() {

        }
    }

}
