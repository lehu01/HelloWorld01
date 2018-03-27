package com.zmsoft.ccd.lib.utils.imageloadutil.progress;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 通过okhttp下载文件，更新下载进度
 *
 * @author DangGui
 * @create 2016/12/28.
 */
public class ProgressDataFetcher implements DataFetcher<InputStream> {

    private final String mUrl;
    private Call mProgressCall;
    private InputStream mStream;
    private volatile boolean mIsCancelled;
    private ProgressUIListener mProListener;

    public ProgressDataFetcher(String url, ProgressUIListener listener) {
        this.mUrl = url;
        this.mProListener = listener;
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        Request request = new Request.Builder()
                .url(mUrl)
                .build();
        final ProgressListener progressListener = new ProgressListener() {

            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                if (mProListener != null) {
                    mProListener.update((int) bytesRead, (int) contentLength);
                }
            }
        };
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                }).build();
        try {
            mProgressCall = client.newCall(request);
            Response response = mProgressCall.execute();
            if (mIsCancelled) {
                return null;
            }
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            mStream = response.body().byteStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mStream;
    }

    @Override
    public void cleanup() {
        if (mStream != null) {
            try {
                mStream.close();
            } catch (IOException e) {
            }
        }
        if (mProgressCall != null) {
            mProgressCall.cancel();
        }
    }

    @Override
    public String getId() {
        return mUrl;
    }

    @Override
    public void cancel() {
        mIsCancelled = true;
    }
}
