package com.runda.projectframework.app.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.runda.projectframework.R;
import com.runda.projectframework.app.page.Adapter_Permission;
import com.runda.projectframework.app.repository.PermissionInfo;

import java.util.Arrays;

/**
 *
 * @Description:
 * @Author:         An_K
 * @CreateDate:     2020/10/15 16:19
 * @Version:        1.0
 */
public final class DialogRequestPermission {

    public static final class Builder
            extends BaseDialog.Builder<Builder> {

        private OnListener mListener;

        private final RecyclerView recyclerView;

        private final Button buttonKnown;

        private  PermissionInfo[] data = new PermissionInfo[]{
                new PermissionInfo("存储权限","存储App的图片及音频文件,节约您的流量"),
                new PermissionInfo("电话权限","为了正常识别手机设备、运营商网络和本机设备、进行手机认证保证账号安全"),
                new PermissionInfo("存储权限","存储App的图片及音频文件,节约您的流量"),
                new PermissionInfo("电话权限","为了正常识别手机设备、运营商网络和本机设备、进行手机认证保证账号安全"),
                new PermissionInfo("存储权限","存储App的图片及音频文件,节约您的流量"),
                new PermissionInfo("电话权限","为了正常识别手机设备、运营商网络和本机设备、进行手机认证保证账号安全"),
        };

        public Builder(Context context) {
            super(context);

            setContentView(R.layout.dialog_request_permission);
//            setAnimStyle(BaseDialog.ANIM_BOTTOM);
            setCancelable(false);
            setCanceledOnTouchOutside(false);

            recyclerView = findViewById(R.id.recyclerView);
            buttonKnown = findViewById(R.id.buttonKnown);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            Adapter_Permission adapter_permission = new Adapter_Permission(R.layout.item_permission, Arrays.asList(data));
            recyclerView.setAdapter(adapter_permission);
            setOnClickListener(buttonKnown);
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        @Override
        public void onClick(View v) {
            if (v == buttonKnown) {
                mListener.onConfirm(getDialog());
            }
        }

        public interface OnListener {

            /**
             * 点击知道了
             */
            void onConfirm(BaseDialog dialog);
        }
    }
}