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
import com.download.library.DownloadImpl;
import com.download.library.DownloadListenerAdapter;
import com.download.library.Extra;
import com.runda.projectframework.R;
import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.utils.LogUtil;

import java.io.File;


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
            mApkFile = new File(PathUtils.getExternalAppFilesPath() + "/ttt.apk");
            setCancelable(false);
            DownloadImpl.getInstance()
                    .with(getContext())
                    .url("http://shouji.360tpcdn.com/170918/93d1695d87df5a0c0002058afc0361f1/com.ss.android.article.news_636.apk")
                    .target(mApkFile)//自定义路径需指定目录和authority(FileContentProvide),需要相对应匹配才能启动通知，和自动打开文件
                    .setUniquePath(false)//是否唯一路径
                    .setForceDownload(true)//不管网络类型
                    .setRetry(4)//下载异常，自动重试,最多重试4次
                    .setEnableIndicator(false)
                    .setBlockMaxTime(60000L) //以8KB位单位，默认60s ，如果60s内无法从网络流中读满8KB数据，则抛出异常 。
                    .setConnectTimeOut(10000L)//连接10超时
                    .setOpenBreakPointDownload(false)//打开断点续传
                    .setParallelDownload(true)//打开多线程下载
                    .enqueue(new DownloadListenerAdapter() {
                        @Override
                        public void onStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, Extra extra) {
                            super.onStart(url, userAgent, contentDisposition, mimetype, contentLength, extra);
                            // 标记为下载中
                            mDownloading = true;
                            // 标记成未下载完成
                            mDownloadComplete = false;
                            // 后台更新
                            mCloseView.setVisibility(View.GONE);
                            // 显示进度条
                            mProgressView.setVisibility(View.VISIBLE);
                            mUpdateView.setText(R.string.update_status_start);
                        }

                        @MainThread //加上该注解，自动回调到主线程
                        @Override
                        public void onProgress(String url, long downloaded, long length, long usedTime) {
                            super.onProgress(url, downloaded, length, usedTime);
                            int progress = (int) ((downloaded) / Float.valueOf(length) * 100);
                            mUpdateView.setText(String.format(getString(R.string.update_status_running), progress));
                            mProgressView.setProgress(progress);
                        }

                        @Override
                        public boolean onResult(Throwable throwable, Uri path, String url, Extra extra) {
//						String md5 = Runtime.getInstance().md5(new File(path.getPath()));
                            if(throwable == null){
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
                            return super.onResult(throwable, path, url, extra);
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