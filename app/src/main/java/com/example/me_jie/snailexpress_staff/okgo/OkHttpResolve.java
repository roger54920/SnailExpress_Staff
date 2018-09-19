package com.example.me_jie.snailexpress_staff.okgo;

import android.content.Context;
import android.util.Log;

import com.example.me_jie.snailexpress_staff.utils.GsonUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.interceptor.LoggerInterceptor.TAG;

/**
 * Created by me-jie on 2017/3/31.
 */

public class OkHttpResolve {
    private static Context context;

    public OkHttpResolve(Context context) {
        this.context = context;
    }

    /**
     * get 提交
     *
     * @param url
     * @param cacheKey
     * @param clazz
     * @param callBack
     * @param <T>
     * @return
     */
    public static <T> T getResult(String url, String cacheKey, final Class<T> clazz, final HttpCallBack callBack) {
        OkGo.get(url)     // 请求方式和请求url
                .tag(context)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey(cacheKey)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(final String s, Call call, Response response) {
                        //结果
                        T t = GsonUtils.getGson(s, clazz);
                        if (callBack != null) {
                            callBack.finish(t);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e(TAG, "onError: " + e.getMessage());
                    }
                });
        return null;
    }

    /**
     * post 字符串提交
     *
     * @param url
     * @param clazz
     * @param callBack
     * @param <T>
     * @return
     */
    public static <T> T postStringResult(String url, String str, final Class<T> clazz, final HttpCallBack callBack) {
        OkGo.post(url)     // 请求方式和请求url
                .tag(context)                       // 请求的 tag, 主要用于取消对应的请求
                .upString(str)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(final String s, Call call, Response response) {
                        //结果
                        T t = GsonUtils.getGson(s, clazz);
                        if (callBack != null) {
                            callBack.finish(t);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e(TAG, "onError: " + e.getMessage());
                    }
                });
        return null;
    }

    /**
     * post Json提交
     *
     * @param url
     * @param json
     * @param clazz
     * @param callBack
     * @param <T>
     * @return
     */
    public static <T> T postJsonResult(String url, String json, final Class<T> clazz, final HttpCallBack callBack) {
        OkGo.post(url)     // 请求方式和请求url
                .tag(context)                       // 请求的 tag, 主要用于取消对应的请求
                .upJson(json)//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(final String s, Call call, Response response) {
                        //结果
                        T t = GsonUtils.getGson(s, clazz);
                        if (callBack != null) {
                            callBack.finish(t);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e(TAG, "onError: " + e.getMessage());
                    }
                });
        return null;
    }

    /**
     * 上传文件
     * @param url
     * @param category
     * @param srcPath
     * @param clazz
     * @param callBack
     * @param <T>
     * @return
     */
    public static <T> T postJsonUpLoadResult(String url, String category, String srcPath, final Class<T> clazz, final HttpCallBack callBack) {
        OkGo.post(url)     // 请求方式和请求url
                .tag(context)                       // 请求的 tag, 主要用于取消对应的请求
                .params("category", category)        // 这里可以上传参数
                .params("file", new File(srcPath))   // 可以添加文件上传
                .cacheKey("file_img" + category)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(final String s, Call call, Response response) {
                        //结果
                        T t = GsonUtils.getGson(s, clazz);
                        if (callBack != null) {
                            callBack.finish(t);
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e(TAG, "onError: " + e.getMessage());
                    }
                });
        return null;
    }


    public interface HttpCallBack<T> {
        <T> void finish(T result);
    }
}
