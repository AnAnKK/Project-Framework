package com.runda.projectframework.app.page.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseLazyFragment;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.page.activity.Activity_FuncList;
import com.runda.projectframework.app.page.activity.home.Activity_EditText;
import com.runda.projectframework.app.page.activity.home.Activity_FileUploadDownload;
import com.runda.projectframework.app.page.activity.home.Activity_Flexbox;
import com.runda.projectframework.app.page.activity.home.Activity_KProgressHud;
import com.runda.projectframework.app.page.activity.home.Activity_LoadingDialog;
import com.runda.projectframework.app.page.activity.home.Activity_Marquee;
import com.runda.projectframework.app.page.activity.home.Activity_Popup;
import com.runda.projectframework.app.page.activity.home.Activity_VersionUpdate;
import com.runda.projectframework.app.page.activity.home.Image.Activity_RadiusImageView;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.Activity_AppBarLayout;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.Activity_CoordinatorLayoutBasic;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.Activity_CoordinatorLayoutBasic2;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.Activity_CoordinatorLayoutBasic3;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.Activity_CoordinatorLayoutBasic4;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.behavior.Activity_BehaviorMoveTitle;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.behavior.Activity_BehaviorProfile;
import com.runda.projectframework.app.page.activity.home.coordinatorLayout.behavior.Activity_BehaviorSearch;
import com.runda.projectframework.app.page.activity.home.immersionbar.Activity_ImmersionBarSlideTrans;
import com.runda.projectframework.app.page.activity.home.loadsir.Activity_LoadSirActivity;
import com.runda.projectframework.app.page.activity.home.loadsir.Activity_LoadSirCustom;
import com.runda.projectframework.app.page.activity.home.loadsir.Activity_LoadSirFragment;
import com.runda.projectframework.app.page.activity.home.loadsir.FragmentActivity_LoadSir;
import com.runda.projectframework.app.page.activity.home.Image.Activity_PictureAdd;
import com.runda.projectframework.app.page.activity.home.Image.Activity_Transferee;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_QuickAdapter;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_RecyclerDrag;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_RecyclerMultiType;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_RecyclerSticky;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_ScrollSticky;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_RecyclerSwipe;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_RecyclerSwipeDefined;
import com.runda.projectframework.app.page.activity.home.recycler.Activity_RecyclerTree;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_Profile;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_RefreshBasic;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_WebView;
import com.runda.projectframework.app.page.activity.home.smartrefresh.Activity_WeiboPage;
import com.runda.projectframework.app.page.activity.home.tbs.Activity_TBSStatic;
import com.runda.projectframework.app.page.activity.home.video.Activity_VideoPlayRecyclerView;
import com.runda.projectframework.app.page.adapter.Adapter_FuncItem;
import com.runda.projectframework.app.page.viewmodel.ViewModel_MainPage_Home;
import com.runda.projectframework.app.repository.bean.PageTextClzInfo;
import com.runda.projectframework.utils.IntentUtil;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 * Created by Kongdq
 * @date 2019/11/7
 * Description: 首页
 */
public class Fragment_Home extends BaseLazyFragment<ViewModel_MainPage_Home> {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static final String SmartRefreshLayout = "SmartRefreshLayout";
    public static final String ImmersionBarString = "ImmersionBar";
    public static final String PopString = "Pop";
    public static final String LoadSir = "LoadSir";
    public static final String KProgressHud = "KProgressHud";
    public static final String RecyclerViewWidget = "RecyclerView";
    public static final String Image = "图片";
    public static final String Video = "视频";
    public static final String CoordinatorLayoutString = "CoordinatorLayout";
    public static final String VersionUpdate = "版本更新";
    public static final String Flexbox = "流布局";
    public static final String Marquee = "文字滚动";
    public static final String Drop = "下拉框看github/DropDownMenu-plus";//
    public static final String TBSStatic = "TBS静态集成";
    public static final String EditTextString = "EditText";
    public static final String FileUpDownLoad = "文件上传下载";
    public static final String LoadingDialog = "dialog取代Kprogress";

    private String TAG = getClass().getSimpleName();

    public static Fragment_Home newInstance() {
        Bundle args = new Bundle();
        Fragment_Home fragment = new Fragment_Home();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public View getRegisterLoadSir() {
        return null;
    }

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).statusBarColor(R.color.color_primary).fitsSystemWindows(true).keyboardEnable(true).init();
    }

    @Override
    public ViewModel_MainPage_Home initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(ViewModel_MainPage_Home.class);
    }

    @Override
    public void initEvents() {
//        版本更新
    }

    @Override
    public void onNetReload(View v) {

    }

    @Override
    public void start() {
        setData();
    }

    private void setData() {
        List<PageTextClzInfo> list = new ArrayList<>();
        list.add(new PageTextClzInfo(SmartRefreshLayout, Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(ImmersionBarString, Activity_ImmersionBarSlideTrans.class.getSimpleName()));
        list.add(new PageTextClzInfo(PopString, Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(LoadSir, Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(KProgressHud, Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(RecyclerViewWidget, Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(Image, Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(Video, Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(CoordinatorLayoutString,Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(VersionUpdate,Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(Flexbox, Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(Marquee, Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(Drop, Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(TBSStatic, Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(EditTextString, Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(FileUpDownLoad, Activity_FuncList.class.getSimpleName()));
        list.add(new PageTextClzInfo(LoadingDialog, Activity_FuncList.class.getSimpleName()));
        refreshLayout.setRefreshHeader(new ClassicsHeader(_mActivity));
        refreshLayout.setRefreshFooter(new ClassicsFooter(_mActivity));
        refreshLayout.setEnableLoadMore(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(_mActivity,VERTICAL));
        Adapter_FuncItem adapter = new Adapter_FuncItem(R.layout.item_text,list);
        Disposable event_itemClicked = adapter.getRxOnItemClickListener()
                .compose(RxUtil.operateDelay())
                .subscribe(event -> {
                    if (refreshLayout.getState() != RefreshState.None) {
                        return;
                    }

                    switch (event.data().getName()){
                        case SmartRefreshLayout:
                            List<PageTextClzInfo> list1 = new ArrayList<>();
                            list1.add(new PageTextClzInfo("WebView", Activity_WebView.class.getName()));
                            list1.add(new PageTextClzInfo("WeiBoPage", Activity_WeiboPage.class.getName()));
                            list1.add(new PageTextClzInfo("个人中心", Activity_Profile.class.getName()));
                            list1.add(new PageTextClzInfo("基本使用", Activity_RefreshBasic.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list1);
                                    intent.putExtra("title",SmartRefreshLayout);
                                }
                            });
                            break;
                        case ImmersionBarString:
                            List<PageTextClzInfo> list2 = new ArrayList<>();
                            list2.add(new PageTextClzInfo("滑动透明状态栏", Activity_ImmersionBarSlideTrans.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list2);
                                    intent.putExtra("title",ImmersionBarString);
                                }
                            });
                            break;
                        case PopString:
                            IntentUtil.startActivity(_mActivity,Activity_Popup.class);
                            break;
                        case LoadSir:
                            List<PageTextClzInfo> list3 = new ArrayList<>();
                            list3.add(new PageTextClzInfo("Activity", Activity_LoadSirActivity.class.getName()));
                            list3.add(new PageTextClzInfo("Fragment", Activity_LoadSirFragment.class.getName()));
                            list3.add(new PageTextClzInfo("FragmentActivity", FragmentActivity_LoadSir.class.getName()));
                            list3.add(new PageTextClzInfo("自定义CallBack", Activity_LoadSirCustom.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list3);
                                    intent.putExtra("title",LoadSir);
                                }
                            });
                            break;
                        case KProgressHud:
                            IntentUtil.startActivity(_mActivity, Activity_KProgressHud.class);
                            break;
                        case RecyclerViewWidget:
                            List<PageTextClzInfo> list4 = new ArrayList<>();
                            list4.add(new PageTextClzInfo("BaseRecyclerViewAdapterHelper基本用法", Activity_QuickAdapter.class.getName()));
                            list4.add(new PageTextClzInfo("拖拽", Activity_RecyclerDrag.class.getName()));
                            list4.add(new PageTextClzInfo("侧滑+拖拽", Activity_RecyclerSwipe.class.getName()));
                            list4.add(new PageTextClzInfo("自定义侧边栏内容", Activity_RecyclerSwipeDefined.class.getName()));
                            list4.add(new PageTextClzInfo("BaseRecyclerViewAdapterHelper不同Item", Activity_RecyclerMultiType.class.getName()));
                            list4.add(new PageTextClzInfo("粘性头部ScrollView", Activity_ScrollSticky.class.getName()));
                            list4.add(new PageTextClzInfo("粘性头部RecyclerView", Activity_RecyclerSticky.class.getName()));
                            list4.add(new PageTextClzInfo("BaseRecyclerViewAdapterHelper基本用法树形", Activity_RecyclerTree.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list4);
                                    intent.putExtra("title",RecyclerViewWidget);
                                }
                            });
                            break;
                        case Image:
                            List<PageTextClzInfo> list5 = new ArrayList<>();
                            list5.add(new PageTextClzInfo("图片添加", Activity_PictureAdd.class.getName()));
                            list5.add(new PageTextClzInfo("图片展示Transferee", Activity_Transferee.class.getName()));
                            list5.add(new PageTextClzInfo("RadiusImageView", Activity_RadiusImageView.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list5);
                                    intent.putExtra("title", Image);
                                }
                            });
                            break;
                        case Video:
                            List<PageTextClzInfo> list6 = new ArrayList<>();
                            list6.add(new PageTextClzInfo("recyclerview视频列表 DKPlayer", Activity_VideoPlayRecyclerView.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list6);
                                    intent.putExtra("title",Video);
                                }
                            });
                            break;
                        case CoordinatorLayoutString:
                            List<PageTextClzInfo> list7 = new ArrayList<>();
                            list7.add(new PageTextClzInfo("AppBarLayout用法", Activity_AppBarLayout.class.getName()));
                            list7.add(new PageTextClzInfo("CoordinatorLayout基本用法1", Activity_CoordinatorLayoutBasic.class.getName()));
                            list7.add(new PageTextClzInfo("CoordinatorLayout基本用法2", Activity_CoordinatorLayoutBasic2.class.getName()));
                            list7.add(new PageTextClzInfo("CoordinatorLayout基本用法3", Activity_CoordinatorLayoutBasic3.class.getName()));
                            list7.add(new PageTextClzInfo("CoordinatorLayout带搜索", Activity_CoordinatorLayoutBasic4.class.getName()));
                            list7.add(new PageTextClzInfo("Behavior个人中心", Activity_BehaviorProfile.class.getName()));
                            list7.add(new PageTextClzInfo("Behavior搜索", Activity_BehaviorSearch.class.getName()));
                            list7.add(new PageTextClzInfo("Behavior标题向下移动", Activity_BehaviorMoveTitle.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list7);
                                    intent.putExtra("title",CoordinatorLayoutString);
                                }
                            });
                            break;
                        case VersionUpdate:
                            List<PageTextClzInfo> list8 = new ArrayList<>();
                            list8.add(new PageTextClzInfo("版本更新", Activity_VersionUpdate.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list8);
                                    intent.putExtra("title",VersionUpdate);
                                }
                            });
                            break;
                        case Flexbox:
                            List<PageTextClzInfo> list9 = new ArrayList<>();
                            list9.add(new PageTextClzInfo("流布局", Activity_Flexbox.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list9);
                                    intent.putExtra("title",Flexbox);
                                }
                            });
                            break;
                        case Marquee:
                            List<PageTextClzInfo> list10 = new ArrayList<>();
                            list10.add(new PageTextClzInfo(Marquee, Activity_Marquee.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list10);
                                    intent.putExtra("title",Marquee);
                                }
                            });
                            break;
                        case Drop:
                            List<PageTextClzInfo> list11 = new ArrayList<>();
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list11);
                                    intent.putExtra("title",Drop);
                                }
                            });
                            break;
                        case TBSStatic:
                            List<PageTextClzInfo> list12 = new ArrayList<>();
                            list12.add(new PageTextClzInfo(TBSStatic, Activity_TBSStatic.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list12);
                                    intent.putExtra("title",TBSStatic);
                                }
                            });
                            break;
                        case EditTextString:
                            List<PageTextClzInfo> list13 = new ArrayList<>();
                            list13.add(new PageTextClzInfo(EditTextString, Activity_EditText.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list13);
                                    intent.putExtra("title",EditTextString);
                                }
                            });
                            break;
                        case FileUpDownLoad:
                            List<PageTextClzInfo> list14 = new ArrayList<>();
                            list14.add(new PageTextClzInfo(FileUpDownLoad, Activity_FileUploadDownload.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list14);
                                    intent.putExtra("title",FileUpDownLoad);
                                }
                            });
                            break;
                        case LoadingDialog:
                            List<PageTextClzInfo> list15 = new ArrayList<>();
                            list15.add(new PageTextClzInfo(LoadingDialog, Activity_LoadingDialog.class.getName()));
                            IntentUtil.startActivityWithOperation(_mActivity, Activity_FuncList.class, new IntentUtil.IntentOperation() {
                                @Override
                                public void operate(Intent intent) {
                                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list15);
                                    intent.putExtra("title",LoadingDialog);
                                }
                            });
                            break;
                    }
                });
        getViewModel().getRxEventManager().addRxEvent(event_itemClicked);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initNotSignEvent() {

    }


    @Override
    public void initNoNetworkEvent() {
        getViewModel().getNoNetworkLiveData().observe(this, message -> {
            if (message == null) {
                return;
            }
        });
    }

    @Override
    public void initTokenOverTimeEvent() {

    }

    @Override
    public void initShowOrDismissWaitingEvent() {

    }

}
