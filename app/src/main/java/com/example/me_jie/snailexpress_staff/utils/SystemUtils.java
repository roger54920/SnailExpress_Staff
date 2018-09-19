package com.example.me_jie.snailexpress_staff.utils;

import android.content.Context;

/**
 * Created by yue on 15/10/29.
 * 系统工具
 */
@SuppressWarnings("deprecation")
public class SystemUtils {
    private SystemUtils() {
    }
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
