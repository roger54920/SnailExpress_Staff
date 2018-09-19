package com.example.me_jie.snailexpress_staff.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.me_jie.snailexpress_staff.Constants;
import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.ExpressRobSingleBean;
import com.example.me_jie.snailexpress_staff.bean.RecipientsBean;
import com.example.me_jie.snailexpress_staff.bean.RecipientsDisplayBean;
import com.example.me_jie.snailexpress_staff.bean.SendDetailsBean;
import com.example.me_jie.snailexpress_staff.bean.SendDisplayBean;
import com.example.me_jie.snailexpress_staff.okgo.OkHttpResolve;
import com.example.me_jie.snailexpress_staff.presenter.HistoicFlowPresenter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import static com.example.me_jie.snailexpress_staff.R.id.accept_phone;


/**
 * Created by me-jie on 2017/5/18.
 * 请求操作
 */

public class RequestOperationUtil {
    /**
     * 时间轴寄件
     *
     * @param senderId
     */
    public static void setSendHistoicFlowResult(String senderId, Activity activity, HistoicFlowPresenter histoicFlowPresenter) {
        new OkHttpResolve(activity);
        histoicFlowPresenter.postJsonHistoicFlowResult("business/track/send", "{\"id\":\"" + senderId + "\"}", activity);
    }

    /**
     * 时间轴收件
     *
     * @param acceptId
     */
    public static void setAddresseeHistoicFlowResult(String acceptId, Activity activity, HistoicFlowPresenter histoicFlowPresenter) {
        new OkHttpResolve(activity);
        histoicFlowPresenter.postJsonHistoicFlowResult("business/track/accept", "{\"id\":\"" + acceptId + "\"}", activity);
    }

    /**
     * 设置图片
     */
    public static void setGlide(Activity activity, String img_url, ImageView fullPhotoView) {
        Glide.with(activity)
                .load(img_url)
                .asBitmap()// .asGif() 加载gif图
                .diskCacheStrategy(DiskCacheStrategy.SOURCE) //添加缓存
                .placeholder(R.mipmap.image_loader)//加载成功之前
                .error(R.mipmap.fail_load)//加载失败
                .into(fullPhotoView);
    }

    /**
     * 寄件称重必须参数
     */
    public static void intentSendWeigh(SendDetailsBean sendDetailsBean, Intent intent) {
        SendDetailsBean.MapsenderBean mapsender = sendDetailsBean.getMapsender();
        intent.putExtra("shipperCode", mapsender.getShipperCode());
        intent.putExtra("sendername", mapsender.getSendername());
        intent.putExtra("createDate", mapsender.getCreateDate());
        intent.putExtra("sendermobile", mapsender.getSendermobile());

        intent.putExtra("receivername", mapsender.getReceivername());
        intent.putExtra("receivermobile", mapsender.getReceivermobile());
        if (!mapsender.getSenderpro().equals(mapsender.getSendercity())) {
            intent.putExtra("sender", mapsender.getSenderpro() + mapsender.getSendercity() + mapsender.getSenderarea() + mapsender.getSenderaddress());
        } else {
            intent.putExtra("sender", mapsender.getSendercity() + mapsender.getSenderarea() + mapsender.getSenderaddress());
        }
        if (!mapsender.getRecepro().equals(mapsender.getRececity())) {
            intent.putExtra("recipients", mapsender.getRecepro() + mapsender.getRececity() + mapsender.getRecearea() + mapsender.getReceaddress());
        } else {
            intent.putExtra("recipients", mapsender.getRececity() + mapsender.getRecearea() + mapsender.getReceaddress());
        }
    }

    /**
     * 收件必须参数
     */
    public static void intentAcceptNecessaryParameters(RecipientsBean recipientsBean, Intent intent, String source) {
        RecipientsBean.ExpressAcceptBean expressAccept = recipientsBean.getExpressAccept();
        intent.putExtra("source", source);
        intent.putExtra("shipperCode", expressAccept.getExpressParcel().getShipperCode());
        intent.putExtra("lgisticCode", expressAccept.getExpressParcel().getLgisticCode());
        intent.putExtra("img_url", Constants.UP_LOAD_IMAGE_TOP + expressAccept.getExpressParcel().getPhoto());
        intent.putExtra("createDate", expressAccept.getCreateDate());
        intent.putExtra("expressAccept_id", expressAccept.getId());
        intent.putExtra("expressParcel_id", expressAccept.getExpressParcel().getId());
        intent.putExtra("workstation_id", expressAccept.getWorkstation().getId());
        intent.putExtra("act_procInsId", recipientsBean.getAct().getProcInsId());
        intent.putExtra("act_taskId", recipientsBean.getAct().getTaskId());
    }

    /**
     * 收件录入参数
     */
    public static void intentAcceptMessageEnteringParameters(RecipientsBean.ExpressAcceptBean expressAccept, Intent intent) {
        intent.putExtra("cellCode", expressAccept.getWorkstation().getCellCode());
        intent.putExtra("remarks",expressAccept.getWorkstation().getRemarks());
    }

    /**
     * 寄件必须参数
     */
    public static void intentSendNecessaryParameters(SendDetailsBean sendDetailsBean, Intent intent, String source) {
        SendDetailsBean.MapsenderBean mapsender = sendDetailsBean.getMapsender();
        intent.putExtra("source", source);
        intent.putExtra("senderId", mapsender.getSenderId());
        intent.putExtra("taskId", mapsender.getTaskId());
        intent.putExtra("procInsId", mapsender.getProcInsId());

        intent.putExtra("lgisticCode", mapsender.getLgisticCode());
        intent.putExtra("goodsname", mapsender.getGoodsname());
        intent.putExtra("containerNO", mapsender.getContainerNO());
        intent.putExtra("cost", mapsender.getCost());
        intent.putExtra("goodsWeight", mapsender.getGoodsWeight());
        intent.putExtra("shipperCode", mapsender.getShipperCode());
        if (!mapsender.getSenderpro().equals(mapsender.getSendercity())) {
            intent.putExtra("sender", mapsender.getSendername() + " " + mapsender.getSenderpro() + mapsender.getSendercity() + mapsender.getSenderarea() + mapsender.getSenderaddress());
        } else {
            intent.putExtra("sender", mapsender.getSendername() + " " + mapsender.getSendercity() + mapsender.getSenderarea() + mapsender.getSenderaddress());
        }
        if (!mapsender.getRecepro().equals(mapsender.getRececity())) {
            intent.putExtra("recipients", mapsender.getReceivername() + " " + mapsender.getRecepro() + mapsender.getRececity() + mapsender.getRecearea() + mapsender.getReceaddress());
        } else {
            intent.putExtra("recipients", mapsender.getReceivername() + " " + mapsender.getRececity() + mapsender.getRecearea() + mapsender.getReceaddress());
        }
    }

    /**
     * 设置抢单适配器参数
     *
     * @param recipientsDisplayBean
     * @param holder
     */
    public static void setAdpaterOrder(ExpressRobSingleBean.PageBean.ListBean recipientsDisplayBean, ViewHolder holder) {
        holder.setText(R.id.ers_type, recipientsDisplayBean.getBusinessType());
        if (recipientsDisplayBean.getBusinessType().equals("送件")) {
            holder.setBackgroundRes(R.id.ers_type, R.drawable.button_return_shape).setTextColorRes(R.id.ers_type, R.color.cl_6fd1c8);
        } else {
            holder.setBackgroundRes(R.id.ers_type, R.drawable.button_affirm_shape).setTextColorRes(R.id.ers_type, R.color.white);
        }
        holder.setText(R.id.ers_name, recipientsDisplayBean.getUsername());
        holder.setText(R.id.ers_phone, recipientsDisplayBean.getMobile());
        holder.setText(R.id.ers_address, recipientsDisplayBean.getAddress());

        holder.setBackgroundRes(R.id.express_rob_single_ll, R.drawable.customer_selector);
    }

    /**
     * 设置收件适配器参数
     *
     * @param recipientsDisplayBean
     * @param holder
     */
    public static void setAdpaterAccept(RecipientsDisplayBean.PageBean.ListBean recipientsDisplayBean, ViewHolder holder) {
        holder.setText(R.id.conllecting_state, recipientsDisplayBean.getBusinessStatus());
        holder.setText(R.id.express_type, recipientsDisplayBean.getExpressParcel().getShipperCode());
        holder.setText(R.id.express_number, recipientsDisplayBean.getExpressParcel().getLgisticCode());
        holder.setText(R.id.express_datetime, recipientsDisplayBean.getCreateDate());
        holder.setText(R.id.courier_send_state, recipientsDisplayBean.getRemarks());

        RecipientsDisplayBean.PageBean.ListBean.ExpressParcelBean.ReceiverBean receiver = recipientsDisplayBean.getExpressParcel().getReceiver();
        if (receiver != null) {
            holder.setText(R.id.sir_name, receiver.getName());
            holder.setText(accept_phone, receiver.getMobile());
            String provinceName = receiver.getProvinceName();
            String cityName = receiver.getCityName();
            String expAreaName = receiver.getExpAreaName();
            String address = receiver.getAddress();
            if (provinceName.equals(cityName)) {
                holder.setText(R.id.sir_address, cityName + expAreaName + address);
            } else {
                holder.setText(R.id.sir_address, provinceName + cityName + expAreaName + address);
            }
        } else {
            holder.setVisible(R.id.accept_rl, false);
            holder.setVisible(R.id.accept_phone, false);

        }
        holder.setBackgroundRes(R.id.ll_content, R.drawable.customer_selector);
    }

    /**
     * 设置寄件适配器参数
     *
     * @param sendDisplayBean
     * @param holder
     */
    public static void setAdpaterSender(SendDisplayBean.PageBean.ListBean sendDisplayBean, ViewHolder holder) {
        SendDisplayBean.PageBean.ListBean.ExpressParcelBean expressParcel = sendDisplayBean.getExpressParcel();
        holder.setText(R.id.shipperCode, "所选承运公司为：" + expressParcel.getShipperCode());
        SendDisplayBean.PageBean.ListBean.ExpressParcelBean.CommodityBean commodity = expressParcel.getCommodity();
        holder.setText(R.id.goodsname, commodity.getGoodsName());
        SendDisplayBean.PageBean.ListBean.ExpressParcelBean.SenderBean sender = expressParcel.getSender();
        SendDisplayBean.PageBean.ListBean.ExpressParcelBean.ReceiverBean receiver = expressParcel.getReceiver();
        holder.setText(R.id.sender, sender.getName() + " " + sender.getProvinceName() + sender.getCityName() + sender.getExpAreaName() + sender.getAddress());
        holder.setText(R.id.addressee, receiver.getName() + " " + receiver.getProvinceName() + receiver.getCityName() + receiver.getExpAreaName() + receiver.getAddress());

        String lgisticcode = sendDisplayBean.getExpressParcel().getLgisticCode();
        if (!lgisticcode.equals("0")) {
            holder.setText(R.id.courier_number, "快递单号：" + lgisticcode).setVisible(R.id.courier_number, true);
        } else {
            holder.setVisible(R.id.courier_number, false);
        }
        String goodsWeight = commodity.getGoodsWeight();
        String cost = expressParcel.getCost();
        if (goodsWeight != null && cost != null) {
            holder.setVisible(R.id.delivery_costs, true);
            holder.setText(R.id.weight, "包裹重量为：" + commodity.getGoodsWeight() + "kg");
            holder.setText(R.id.delivery_costs, "包裹快递费用为：" + expressParcel.getCost() + "元");
        } else {
            holder.setVisible(R.id.delivery_costs, false);
            holder.setText(R.id.weight, "包裹未称重");
        }
        holder.setText(R.id.send_expressage_state, sendDisplayBean.getBusinessStatus());
        holder.setText(R.id.courier_sender_state, sendDisplayBean.getRemarks());
        holder.setText(R.id.sender_phone, sendDisplayBean.getExpressParcel().getReceiver().getMobile());

        holder.setBackgroundRes(R.id.rl_content, R.drawable.customer_selector);
    }
}
