package com.example.me_jie.snailexpress_staff.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import com.example.me_jie.snailexpress_staff.R;
import com.example.me_jie.snailexpress_staff.bean.RecipientsPhoneBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义弹窗 收件录入选择地址
 */
public class SelectReceiptAddressDialog extends Dialog {

	public SelectReceiptAddressDialog(Context context) {
		super(context);
	}

	public SelectReceiptAddressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;
		private List<RecipientsPhoneBean.AddressBean> mDatas;
		private CommonAdapter<RecipientsPhoneBean.AddressBean> commonAdapter;
		private List<Integer> selectState = new ArrayList<>();
		private RecipientsPhoneBean.AddressBean address;

		public Builder(Context context,List<RecipientsPhoneBean.AddressBean> address) {
			this.context = context;
			this.mDatas = address;
			for (int i = 0; i < mDatas.size() ; i++) {
				selectState.add(i+1);
			}
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 *
		 * @param
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 *
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 *
		 * @param title
		 * @return
		 */

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 *
		 * @param positiveButtonText
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}
		public SelectReceiptAddressDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final SelectReceiptAddressDialog dialog = new SelectReceiptAddressDialog(context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.coverpopup_select_receipt_address_item, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the confirm button

			Button cancel_btn = (Button) layout.findViewById(R.id.cancel_btn);
			Button confirm_btn = (Button) layout.findViewById(R.id.confirm_btn);
			RecyclerView select_receipt_address_rv = (RecyclerView) layout.findViewById(R.id.select_receipt_address_rv);

			select_receipt_address_rv.setLayoutManager(new LinearLayoutManager(context));
			commonAdapter = new CommonAdapter<RecipientsPhoneBean.AddressBean>(context, R.layout.item_select_receipt_address_dialog, mDatas) {
				@Override
				protected void convert(ViewHolder holder, final RecipientsPhoneBean.AddressBean addressBean, final int position) {
					holder.setText(R.id.radio,addressBean.getRemarks().replace(",",""));
					if(selectState.get(position)==0){
						holder.setChecked(R.id.radio, true);
					}else{
						holder.setChecked(R.id.radio, false);
					}
					holder.setOnClickListener(R.id.radio, new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							for (int i = 0; i <mDatas.size() ; i++) {
								selectState.set(i,i+1);
							}
							selectState.set(position,0);
							address = addressBean;
							notifyDataSetChanged();
						}
					});
				}
			};
			select_receipt_address_rv.setAdapter(commonAdapter);

            // set the confirm button
			if (positiveButtonText != null) {
				confirm_btn
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					confirm_btn
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				confirm_btn.setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				cancel_btn
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					cancel_btn
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				cancel_btn.setVisibility(
						View.GONE);
			}

			dialog.setContentView(layout);
			dialog.setCanceledOnTouchOutside(false);
			return dialog;
		}

		/**
		 * 获取当前选择的参数
		 * @return
         */
		public RecipientsPhoneBean.AddressBean selectAddress(){
			return address;
		}
	}

}
