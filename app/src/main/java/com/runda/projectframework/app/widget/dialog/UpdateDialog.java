package com.runda.projectframework.app.widget.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.runda.projectframework.R;
import com.runda.projectframework.app.others.Constants;

import java.io.File;

import runda.download.library.DownloadListener;
import runda.download.library.DownloadUtil;
import runda.download.library.InputParameter;

/**
 *
 * @Description:    版本更新对话框
 * @Author:         An_K
 * @CreateDate:     2020/10/15 16:19
 * @Version:        1.0
 */
public final class UpdateDialog {


    public static final class Builder
            extends BaseDialog.Builder<Builder> {

        private final TextView mNameView;
        private final TextView mContentView;
        private final ProgressBar mProgressView;

        private final TextView mUpdateView;
        private final TextView mCloseView;

        /** Apk 文件 */
        private File mApkFile;
        /** 下载地址 */
        private String mDownloadUrl;
        /** 文件 MD5 */
        private String mFileMd5;
        /** 是否强制更新 */
        private boolean mForceUpdate;

        /** 当前是否下载中 */
        private boolean mDownloading;
        /** 当前是否下载完毕 */
        private boolean mDownloadComplete;

        public Builder(Context context) {
            super(context);

            setContentView(R.layout.dialog_update);
            setAnimStyle(BaseDialog.ANIM_BOTTOM);
            setCancelable(false);
            setCanceledOnTouchOutside(false);

            mNameView = findViewById(R.id.tv_update_name);
            mContentView = findViewById(R.id.tv_update_content);
            mProgressView = findViewById(R.id.pb_update_progress);
            mUpdateView = findViewById(R.id.tv_update_update);
            mCloseView = findViewById(R.id.tv_update_close);
            setOnClickListener(mUpdateView, mCloseView);
        }

        /**
         * 设置版本名
         */
        public Builder setVersionName(CharSequence name) {
            mNameView.setText(name);
            return this;
        }

        /**
         * 设置更新日志
         */
        public Builder setUpdateLog(CharSequence text) {
            mContentView.setText(text);
            mContentView.setVisibility(text == null ? View.GONE : View.VISIBLE);
            return this;
        }

        /**
         * 设置强制更新
         */
        public Builder setForceUpdate(boolean force) {
            mForceUpdate = force;
            mCloseView.setVisibility(force ? View.GONE : View.VISIBLE);
            setCancelable(!force);
            return this;
        }

        /**
         * 设置下载 url
         */
        public Builder setDownloadUrl(String url) {
            mDownloadUrl = url;
            return this;
        }

        /**
         * 设置文件 md5
         */
        public Builder setFileMd5(String md5) {
            mFileMd5 = md5;
            return this;
        }

        @Override
        public void onClick(View v) {
            if (v == mCloseView) {
                dismiss();
            } else if (v == mUpdateView) {
                // 判断下载状态
                if (mDownloadComplete) {
                    if (mApkFile.isFile()) {
                        // 下载完毕，安装 Apk
                        installApk();
                    } else {
                        // 下载失败，重新下载
                        downloadApk();
                    }
                } else if (!mDownloading) {
                    // 没有下载，开启下载
                    downloadApk();
                }
            }
        }

        /**
         * 下载 Apk
         */
        private void downloadApk() {
            mApkFile = new File(PathUtils.getExternalAppFilesPath() + "/sstx.apk");
            setCancelable(false);

            String BASE_URL = "http://www.apk.anzhi.com/";
            String FILE_URL = "data4/apk/201809/06/f2a4dbd1b6cc2dca6567f42ae7a91f11_45629100.apk";

            // 标记为下载中
            mDownloading = true;
            // 标记成未下载完成
            mDownloadComplete = false;
            // 后台更新
            mCloseView.setVisibility(View.GONE);
            // 显示进度条
            mProgressView.setVisibility(View.VISIBLE);
            mUpdateView.setText(R.string.update_status_start);

            DownloadUtil.getInstance()
                    .downloadFile(new InputParameter.Builder(BASE_URL, FILE_URL, PathUtils.getExternalAppFilesPath() + "/"+ FileUtils.getFileName(FILE_URL))
                            .setCallbackOnUiThread(true)
                            .build(), new DownloadListener() {
                        @Override
                        public void onFinish(final File file) {
                            mProgressView.setProgress(0);
                            mProgressView.setVisibility(View.GONE);
                            // 标记当前不是下载中
                            mDownloading = false;
                            // 如果当前不是强制更新，对话框就恢复成可取消状态
                            if (!mForceUpdate) {
                                setCancelable(true);
                            }
                            mUpdateView.setText(R.string.update_status_successful);
                            mDownloadComplete = true;
                            installApk();
                        }

                        @Override
                        public void onProgress(int progress, long downloadedLengthKb, long totalLengthKb) {
                            mUpdateView.setText(String.format(getString(R.string.update_status_running), progress));
                            mProgressView.setProgress(progress);
                        }

                        @Override
                        public void onFailed(String errMsg) {
                            mUpdateView.setText(R.string.update_status_failed);
                        }
                    });
        }

        /**
         * 安装 Apk
         */
        private void installApk() {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(getContext(), AppUtils.getAppPackageName() + ".provider", mApkFile);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(mApkFile);
            }

            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }

    }
}