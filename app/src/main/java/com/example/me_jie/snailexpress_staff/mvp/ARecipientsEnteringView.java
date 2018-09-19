package com.example.me_jie.snailexpress_staff.mvp;

/**
 * Created by me-jie on 2017/5/5.
 * 站长收件录入
 */
public interface ARecipientsEnteringView<T> {
    void onASaveRecipientsFinish(T t);//保存收件信息
    void onAAddressByPhoneOrParcelNoFinish(T t);//通过手机号或电子包裹箱号获取地址信息
}
