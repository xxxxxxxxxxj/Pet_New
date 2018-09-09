package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerAddChargeActivityCommponent;
import com.haotang.easyshare.di.module.activity.AddChargeActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.ChargeDetailBean;
import com.haotang.easyshare.mvp.model.entity.res.CommentImg;
import com.haotang.easyshare.mvp.model.entity.res.PhotoViewPagerImg;
import com.haotang.easyshare.mvp.model.entity.res.SelectAddress;
import com.haotang.easyshare.mvp.presenter.AddChargePresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.CommentImgAdapter;
import com.haotang.easyshare.mvp.view.iview.IAddChargeView;
import com.haotang.easyshare.mvp.view.viewholder.AddChargeBoDa;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.FileSizeUtil;
import com.haotang.easyshare.util.SignUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.haotang.easyshare.util.UmenUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;
import com.umeng.analytics.MobclickAgent;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

/**
 * 添加充电站界面
 */
public class AddChargeActivity extends BaseActivity<AddChargePresenter> implements IAddChargeView, AMapLocationListener {
    protected final static String TAG = AddChargeActivity.class.getSimpleName();
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_titlebar_back)
    ImageView ivTitlebarBack;
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_addcharge_img)
    RecyclerView rvAddchargeImg;
    @BindView(R.id.et_addcharge_zmc)
    EditText etAddchargeZmc;
    @BindView(R.id.tv_addcharge_zdz)
    TextView tvAddchargeZdz;
    @BindView(R.id.rl_addcharge_zdz)
    RelativeLayout rlAddchargeZdz;
    @BindView(R.id.et_addcharge_sl)
    EditText etAddchargeSl;
    @BindView(R.id.iv_addcharge_kuai)
    ImageView ivAddchargeKuai;
    @BindView(R.id.ll_addcharge_kuai)
    LinearLayout llAddchargeKuai;
    @BindView(R.id.iv_addcharge_man)
    ImageView ivAddchargeMan;
    @BindView(R.id.ll_addcharge_man)
    LinearLayout llAddchargeMan;
    @BindView(R.id.ll_addcharge_kuaiorman)
    LinearLayout llAddchargeKuaiorman;
    @BindView(R.id.et_addcharge_phone)
    EditText etAddchargePhone;
    @BindView(R.id.et_addcharge_cdf)
    EditText etAddchargeCdf;
    @BindView(R.id.et_addcharge_fwf)
    EditText etAddchargeFwf;
    @BindView(R.id.et_addcharge_tcf)
    EditText etAddchargeTcf;
    @BindView(R.id.tv_addcharge_zffs)
    TextView tvAddchargeZffs;
    @BindView(R.id.rl_addcharge_zffs)
    RelativeLayout rlAddchargeZffs;
    @BindView(R.id.iv_addcharge_up)
    ImageView ivAddchargeUp;
    @BindView(R.id.ll_addcharge_up)
    LinearLayout llAddchargeUp;
    @BindView(R.id.iv_addcharge_down)
    ImageView ivAddchargeDown;
    @BindView(R.id.ll_addcharge_down)
    LinearLayout llAddchargeDown;
    @BindView(R.id.ll_addcharge_upordown)
    LinearLayout llAddchargeUpordown;
    @BindView(R.id.tv_addcharge_kfsj)
    TextView tvAddchargeKfsj;
    @BindView(R.id.rl_addcharge_kfsj)
    RelativeLayout rlAddchargeKfsj;
    @BindView(R.id.et_addcharge_bzsm)
    EditText etAddchargeBzsm;
    @BindView(R.id.iv_addcharg_gr)
    ImageView iv_addcharg_gr;
    @BindView(R.id.iv_addcharg_gg)
    ImageView iv_addcharg_gg;

    private List<CommentImg> imgList = new ArrayList<CommentImg>();
    private List<String> imgPathList = new ArrayList<String>();
    private List<String> localImgPathList = new ArrayList<String>();
    private CommentImgAdapter commentImgAdapter;
    private static final int IMG_NUM = 3;
    private AddChargeBoDa addChargeBoDa;
    private PopupWindow pWinBottomDialog;
    private int payWay = 0;
    private String[] time =
            {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00"
                    , "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
    private double lat;
    private double lng;
    private int upOrDown;
    private int kuaiOrMan;
    private String uuid;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    private double localLat;
    private double localLng;
    private File chargeImgFile;
    private int isPrivate = 1;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_add_charge;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerAddChargeActivityCommponent.builder().
                addChargeActivityModule(new AddChargeActivityModule(this, this)).build().inject(this);
        uuid = getIntent().getStringExtra("uuid");
        chargeImgFile = new File(Environment.getExternalStorageDirectory(),
                "chargeImgFile");
        if (!chargeImgFile.exists()) {
            chargeImgFile.mkdirs();
        }
    }

    @Subscribe
    public void getAddress(SelectAddress selectAddress) {
        if (selectAddress != null) {
            lat = selectAddress.getLat();
            lng = selectAddress.getLng();
            StringUtil.setText(tvAddchargeZdz, selectAddress.getAddress(), "", View.VISIBLE, View.VISIBLE);
        }
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("新建充电桩");
        tvTitlebarOther.setVisibility(View.VISIBLE);
        tvTitlebarOther.setText("保存");

        imgList.add(new CommentImg("", true));
        rvAddchargeImg.setHasFixedSize(true);
        rvAddchargeImg.setNestedScrollingEnabled(false);
        NoScollFullGridLayoutManager noScollFullGridLayoutManager = new
                NoScollFullGridLayoutManager(rvAddchargeImg, this, 3, GridLayoutManager.VERTICAL, false);
        noScollFullGridLayoutManager.setScrollEnabled(false);
        rvAddchargeImg.setLayoutManager(noScollFullGridLayoutManager);
        rvAddchargeImg.addItemDecoration(new GridSpacingItemDecoration(3,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                true));
        commentImgAdapter = new CommentImgAdapter(R.layout.item_comment_img
                , imgList);
        rvAddchargeImg.setAdapter(commentImgAdapter);

        setLocation();
        UmenUtil.UmengEventStatistics(this, UmenUtil.yxzx15);
    }

    private void setLocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        etAddchargePhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s == null || s.length() == 0) return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    etAddchargePhone.setText(sb.toString());
                    etAddchargePhone.setSelection(index);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        commentImgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommentImg commentImg = imgList.get(position);
                if (commentImg.isAdd()) {
                    Matisse.from(AddChargeActivity.this)
                            .choose(MimeType.ofImage())
                            .countable(true)
                            .capture(true)
                            .captureStrategy(
                                    new CaptureStrategy(true, "com.haotang.easyshare.MatisseFileProvider"))
                            .maxSelectable((IMG_NUM + 1) - imgList.size())
                            .gridExpectedSize(
                                    getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideEngine())
                            .forResult(AppConfig.REQUEST_CODE_CHOOSE);
                } else {
                    List<String> photoViewImgList = new ArrayList<String>();
                    photoViewImgList.clear();
                    for (int i = 0; i < imgList.size(); i++) {
                        CommentImg commentImg1 = imgList.get(i);
                        if (commentImg1 != null && !commentImg1.isAdd()) {
                            photoViewImgList.add(imgList.get(i).getImgUrl());
                        }
                    }
                    SystemUtil.goPhotoView(AddChargeActivity.this, position, photoViewImgList, true);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            if (data != null) {
                showDialog();
                compressWithRx(Matisse.obtainPathResult(data));
            }
        }
    }

    private void compressWithRx(List<String> pathList) {
        Flowable.just(pathList)
                .observeOn(Schedulers.io())
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(@NonNull List<String> list) throws Exception {
                        return Luban.with(AddChargeActivity.this).load(list).get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(@NonNull List<File> list) throws Exception {
                        disMissDialog();
                        for (int i = 0; i < imgList.size(); i++) {
                            CommentImg commentImg = imgList.get(i);
                            if (commentImg.isAdd()) {
                                imgList.remove(i);
                            }
                        }
                        for (int i = 0; i < list.size(); i++) {
                            imgPathList.add(list.get(i).getAbsolutePath());
                            imgList.add(new CommentImg(list.get(i).getAbsolutePath(), false));
                        }
                        if (imgList.size() > IMG_NUM) {
                            for (int i = 0; i < imgList.size(); i++) {
                                CommentImg commentImg = imgList.get(i);
                                if (commentImg.isAdd()) {
                                    imgList.remove(i);
                                }
                            }
                        }
                        if (imgList.size() < IMG_NUM) {
                            boolean isAdd = false;
                            for (int i = 0; i < imgList.size(); i++) {
                                CommentImg commentImg = imgList.get(i);
                                if (commentImg.isAdd()) {
                                    isAdd = true;
                                    break;
                                }
                            }
                            if (!isAdd) {
                                imgList.add(new CommentImg("", true));
                            }
                        }
                        commentImgAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Subscribe
    public void getRemovePosition(PhotoViewPagerImg photoViewPagerImg) {
        String imgUrl = photoViewPagerImg.getImgUrl();
        int pagerPosition = photoViewPagerImg.getPagerPosition();
        boolean deleteAll = photoViewPagerImg.isDeleteAll();
        RingLog.d(TAG, "getRemovePosition imgUrl = " + imgUrl);
        RingLog.d(TAG, "getRemovePosition pagerPosition = " + pagerPosition);
        if (deleteAll) {
            imgPathList.clear();
            imgList.clear();
        } else {
            for (int i = 0; i < imgList.size(); i++) {
                CommentImg commentImg = imgList.get(i);
                if (commentImg.getImgUrl().equals(imgUrl)) {
                    imgList.remove(i);
                    imgPathList.remove(i);
                    break;
                }
            }
        }
        if (imgList.size() < IMG_NUM) {
            boolean isAdd = false;
            for (int i = 0; i < imgList.size(); i++) {
                CommentImg commentImg = imgList.get(i);
                if (commentImg.isAdd()) {
                    isAdd = true;
                    break;
                }
            }
            if (!isAdd) {
                imgList.add(new CommentImg("", true));
            }
        }
        commentImgAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
        SystemUtil.goneJP(this);
    }

    private void setKuaiOrMan(int flag) {
        if (flag == 0) {//快充
            kuaiOrMan = 0;
            ivAddchargeKuai.setImageResource(R.mipmap.icon_addcharge_select);
            ivAddchargeMan.setImageResource(R.mipmap.icon_addcharge_unselect);
        } else if (flag == 1) {//慢充
            kuaiOrMan = 1;
            ivAddchargeKuai.setImageResource(R.mipmap.icon_addcharge_unselect);
            ivAddchargeMan.setImageResource(R.mipmap.icon_addcharge_select);
        }
    }

    private void setUpOrDown(int flag) {
        if (flag == 0) {//地上
            upOrDown = 0;
            ivAddchargeUp.setImageResource(R.mipmap.icon_addcharge_select);
            ivAddchargeDown.setImageResource(R.mipmap.icon_addcharge_unselect);
        } else if (flag == 1) {//地下
            upOrDown = 1;
            ivAddchargeUp.setImageResource(R.mipmap.icon_addcharge_unselect);
            ivAddchargeDown.setImageResource(R.mipmap.icon_addcharge_select);
        }
    }

    private void setisPrivate(int flag) {
        if (flag == 0) {//是否私人(0:公共、1:私人)
            isPrivate = 0;
            iv_addcharg_gr.setImageResource(R.mipmap.icon_gr_not);
            iv_addcharg_gg.setImageResource(R.mipmap.icon_gg);
        } else if (flag == 1) {//是否私人(0:公共、1:私人)
            isPrivate = 1;
            iv_addcharg_gr.setImageResource(R.mipmap.icon_gr);
            iv_addcharg_gg.setImageResource(R.mipmap.icon_gg_not);
        }
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_titlebar_other, R.id.rl_addcharge_zdz, R.id.ll_addcharge_kuai,
            R.id.ll_addcharge_man, R.id.rl_addcharge_zffs, R.id.ll_addcharge_up, R.id.ll_addcharge_down,
            R.id.rl_addcharge_kfsj, R.id.iv_addcharg_gr, R.id.iv_addcharg_gg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_addcharg_gr:
                setisPrivate(1);
                break;
            case R.id.iv_addcharg_gg:
                setisPrivate(0);
                break;
            case R.id.iv_titlebar_back:
                SystemUtil.goneJP(this);
                finish();
                break;
            case R.id.tv_titlebar_other:
                showDialog();
                //构建body
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE);
                builder.addFormDataPart("lng", String.valueOf(lng));
                builder.addFormDataPart("lat", String.valueOf(lat));
                builder.addFormDataPart("telephone", etAddchargePhone.getText().toString().trim().replace(" ", ""));
                builder.addFormDataPart("title", etAddchargeZmc.getText().toString().trim());
                builder.addFormDataPart("electricityPrice", etAddchargeCdf.getText().toString().trim() + "_" + tvAddchargeKfsj.getText().toString().trim());
                builder.addFormDataPart("parkingPrice", etAddchargeTcf.getText().toString().trim() + "_" + upOrDown);
                builder.addFormDataPart("serviceFee", etAddchargeFwf.getText().toString().trim());
                if (!tvAddchargeZdz.getText().toString().trim().equals("请选择站地址")) {
                    builder.addFormDataPart("address", tvAddchargeZdz.getText().toString().trim());
                }
                if (!tvAddchargeZffs.getText().toString().trim().equals("请选择支付方式")) {
                    builder.addFormDataPart("payWay", tvAddchargeZffs.getText().toString().trim());
                }
                if (!tvAddchargeKfsj.getText().toString().trim().equals("请选择开放时间")) {
                    builder.addFormDataPart("openTime", tvAddchargeKfsj.getText().toString().trim());
                }
                builder.addFormDataPart("isPrivate", String.valueOf(isPrivate));
                builder.addFormDataPart("remark", etAddchargeBzsm.getText().toString().trim());
                if (kuaiOrMan == 0) {//快充
                    builder.addFormDataPart("fastNum", etAddchargeSl.getText().toString().trim());
                } else if (kuaiOrMan == 1) {//慢充
                    builder.addFormDataPart("slowNum", etAddchargeSl.getText().toString().trim());
                }
                RingLog.e(TAG, "imgPathList.size() = " + imgPathList.size());
                if (imgPathList.size() > 0) {
                    for (int i = 0; i < imgPathList.size(); i++) {
                        RingLog.e(TAG, "imgPathList.get(i) = " + imgPathList.get(i));
                        //构建要上传的文件
                        File file = new File(imgPathList.get(i));
                        String formatFileSize = FileSizeUtil
                                .formatFileSize(file.length(), false);
                        RingLog.e(TAG, "formatFileSize = " + formatFileSize);
                        builder.addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream")
                                , file));
                    }
                }
                if (StringUtil.isNotEmpty(uuid)) {
                    builder.addFormDataPart("uuid", uuid);
                    RequestBody build = builder.build();
                    mPresenter.update(build);
                } else {
                    RequestBody build = builder.build();
                    mPresenter.save(build);
                }
                break;
            case R.id.rl_addcharge_zdz:
                startActivity(new Intent(AddChargeActivity.this, AddAddressActivity.class));
                break;
            case R.id.rl_addcharge_zffs:
                showBottomDialog(1);
                break;
            case R.id.rl_addcharge_kfsj:
                showBottomDialog(2);
                break;
            case R.id.ll_addcharge_kuai:
                setKuaiOrMan(0);
                break;
            case R.id.ll_addcharge_man:
                setKuaiOrMan(1);
                break;
            case R.id.ll_addcharge_up:
                setUpOrDown(0);
                break;
            case R.id.ll_addcharge_down:
                setUpOrDown(1);
                break;
        }
    }

    private void setPayWay(int flag) {
        if (flag == 1) {//微信
            payWay = 1;
            addChargeBoDa.getIvAddchargeBottomWx().setImageResource(R.mipmap.icon_addcharge_select);
            addChargeBoDa.getIvAddchargeBottomZfb().setImageResource(R.mipmap.icon_addcharge_unselect);
            addChargeBoDa.getIv_addcharge_bottom_xj().setImageResource(R.mipmap.icon_addcharge_unselect);
        } else if (flag == 2) {//支付宝
            payWay = 2;
            addChargeBoDa.getIvAddchargeBottomWx().setImageResource(R.mipmap.icon_addcharge_unselect);
            addChargeBoDa.getIvAddchargeBottomZfb().setImageResource(R.mipmap.icon_addcharge_select);
            addChargeBoDa.getIv_addcharge_bottom_xj().setImageResource(R.mipmap.icon_addcharge_unselect);
        } else if (flag == 3) {//现金
            payWay = 3;
            addChargeBoDa.getIv_addcharge_bottom_xj().setImageResource(R.mipmap.icon_addcharge_select);
            addChargeBoDa.getIvAddchargeBottomWx().setImageResource(R.mipmap.icon_addcharge_unselect);
            addChargeBoDa.getIvAddchargeBottomZfb().setImageResource(R.mipmap.icon_addcharge_unselect);
        }
    }

    private void showBottomDialog(final int flag) {
        pWinBottomDialog = null;
        if (pWinBottomDialog == null) {
            ViewGroup customView = (ViewGroup) View.inflate(this, R.layout.addcharge_bottom_dialog
                    , null);
            addChargeBoDa = new AddChargeBoDa(customView);
            pWinBottomDialog = new PopupWindow(customView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, true);
            pWinBottomDialog.setFocusable(true);// 取得焦点
            //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
            pWinBottomDialog.setBackgroundDrawable(new BitmapDrawable());
            //点击外部消失
            pWinBottomDialog.setOutsideTouchable(true);
            //设置可以点击
            pWinBottomDialog.setTouchable(true);
            //进入退出的动画
            pWinBottomDialog.setAnimationStyle(R.style.mypopwindow_anim_style);
            pWinBottomDialog.setWidth(SystemUtil.getDisplayMetrics(this)[0]);
            pWinBottomDialog.showAtLocation(customView, Gravity.BOTTOM, 0, 0);
            if (flag == 1) {//支付方式
                addChargeBoDa.getTvAddchargeBottomTitle().setText("支付方式");
                addChargeBoDa.getLlAddchargeBottomSelectpayway().setVisibility(View.VISIBLE);
                addChargeBoDa.getRlAddchargeBottomSelecttime().setVisibility(View.GONE);
                if (payWay == 1) {//微信
                    addChargeBoDa.getIvAddchargeBottomWx().setImageResource(R.mipmap.icon_addcharge_select);
                    addChargeBoDa.getIvAddchargeBottomZfb().setImageResource(R.mipmap.icon_addcharge_unselect);
                    addChargeBoDa.getIv_addcharge_bottom_xj().setImageResource(R.mipmap.icon_addcharge_unselect);
                } else if (payWay == 2) {//支付宝
                    addChargeBoDa.getIvAddchargeBottomWx().setImageResource(R.mipmap.icon_addcharge_unselect);
                    addChargeBoDa.getIvAddchargeBottomZfb().setImageResource(R.mipmap.icon_addcharge_select);
                    addChargeBoDa.getIv_addcharge_bottom_xj().setImageResource(R.mipmap.icon_addcharge_unselect);
                } else if (payWay == 3) {//现金
                    addChargeBoDa.getIvAddchargeBottomWx().setImageResource(R.mipmap.icon_addcharge_unselect);
                    addChargeBoDa.getIvAddchargeBottomZfb().setImageResource(R.mipmap.icon_addcharge_unselect);
                    addChargeBoDa.getIv_addcharge_bottom_xj().setImageResource(R.mipmap.icon_addcharge_select);
                }
            } else if (flag == 2) {//开放时间
                addChargeBoDa.getTvAddchargeBottomTitle().setText("选择时间");
                addChargeBoDa.getLlAddchargeBottomSelectpayway().setVisibility(View.GONE);
                addChargeBoDa.getRlAddchargeBottomSelecttime().setVisibility(View.VISIBLE);

                RingLog.d(TAG, "density = " + getResources().getDisplayMetrics().density);
                RingLog.d(TAG, "textsize = " + addChargeBoDa.getTvAddchargeBottomTitle().getTextSize());

                addChargeBoDa.getWv_addcharge_bottom_starttime().setTextSize(16 * getResources().getDisplayMetrics().density / 3);
                addChargeBoDa.getWv_addcharge_bottom_starttime().setTextColorCenter(getResources().getColor(R.color.a333333));
                addChargeBoDa.getWv_addcharge_bottom_starttime().setTextColorOut(getResources().getColor(R.color.a999999));
                addChargeBoDa.getWv_addcharge_bottom_starttime().setAdapter(new ArrayWheelAdapter<String>(Arrays.asList(time)));
                addChargeBoDa.getWv_addcharge_bottom_starttime().setCyclic(true);//循环滚动
                addChargeBoDa.getWv_addcharge_bottom_starttime().setCurrentItem(0);
                addChargeBoDa.getWv_addcharge_bottom_starttime().setDividerColor(getResources().getColor(R.color.a979797));

                addChargeBoDa.getWv_addcharge_bottom_endtime().setTextSize(16 * getResources().getDisplayMetrics().density / 3);
                addChargeBoDa.getWv_addcharge_bottom_endtime().setAdapter(new ArrayWheelAdapter<String>(Arrays.asList(time)));
                addChargeBoDa.getWv_addcharge_bottom_endtime().setCyclic(true);//循环滚动
                addChargeBoDa.getWv_addcharge_bottom_endtime().setTextColorCenter(getResources().getColor(R.color.a333333));
                addChargeBoDa.getWv_addcharge_bottom_endtime().setTextColorOut(getResources().getColor(R.color.a999999));
                addChargeBoDa.getWv_addcharge_bottom_endtime().setCurrentItem(0);
                addChargeBoDa.getWv_addcharge_bottom_endtime().setDividerColor(getResources().getColor(R.color.a979797));

            }
            addChargeBoDa.getRlAddchargeBottomWx().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPayWay(1);
                }
            });
            addChargeBoDa.getRlAddchargeBottomZfb().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPayWay(2);
                }
            });
            addChargeBoDa.getRl_addcharge_bottom_xj().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPayWay(3);
                }
            });
            addChargeBoDa.getTvAddchargeBottomOther().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag == 1) {//支付方式
                        if (payWay == 0) {//未选择
                            RingToast.show("请选择支付方式");
                        } else if (payWay == 1) {//微信
                            tvAddchargeZffs.setText("微信支付");
                            pWinBottomDialog.dismiss();
                        } else if (payWay == 2) {//支付宝
                            tvAddchargeZffs.setText("支付宝支付");
                            pWinBottomDialog.dismiss();
                        } else if (payWay == 3) {//支付宝
                            tvAddchargeZffs.setText("现金支付");
                            pWinBottomDialog.dismiss();
                        }
                    } else if (flag == 2) {//开放时间
                        tvAddchargeKfsj.setText(
                                time[addChargeBoDa.getWv_addcharge_bottom_starttime().getCurrentItem()]
                                        + " - " + time[addChargeBoDa.getWv_addcharge_bottom_endtime().getCurrentItem()]);
                        pWinBottomDialog.dismiss();
                    }
                }
            });
            addChargeBoDa.getIvAddchargeBottomClose().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pWinBottomDialog.dismiss();
                }
            });
            addChargeBoDa.getIvAddchargeBottomBg().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pWinBottomDialog.dismiss();
                }
            });
            addChargeBoDa.getRllAddchargeBottom().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            pWinBottomDialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });
        }
    }

    @Override
    public void saveSuccess(AddChargeBean data) {
        RingToast.show("保存成功");
        disMissDialog();
        DevRing.busManager().postEvent(new RefreshFragmentEvent(RefreshFragmentEvent.REFRESH_MYFRAGMET));
        DevRing.busManager().postEvent(new RefreshFragmentEvent(RefreshFragmentEvent.REFRESH_MAINFRAGMET));
        finish();
    }

    @Override
    public void saveFail(int code, String msg) {
        RingToast.show("保存失败");
        disMissDialog();
        RingLog.e(TAG, "saveFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void detailSuccess(ChargeDetailBean data) {
        if (data != null) {
            uuid = data.getUuid();
            lat = data.getLat();
            lng = data.getLng();
            StringUtil.setText(etAddchargeZmc, data.getTitle(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvAddchargeZdz, data.getAddress(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvAddchargeKfsj, data.getOpenTime(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(etAddchargeCdf, data.getElectricityPrice(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(etAddchargeFwf, data.getServiceFee() + "", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvAddchargeZffs, data.getPayWay(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(etAddchargePhone, data.getPhone() + "", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(etAddchargeTcf, data.getParkingPrice(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(etAddchargeBzsm, data.getRemark(), "", View.VISIBLE, View.VISIBLE);
            setUpOrDown(data.getParkingIsUnderground());
            int fastNum = data.getFastNum();
            int slowNum = data.getSlowNum();
            int isPrivate = data.getIsPrivate();
            if (isPrivate == 0) {
                setisPrivate(0);
            } else if (isPrivate == 1) {
                setisPrivate(1);
            }
            if (fastNum > 0) {
                StringUtil.setText(etAddchargeSl, fastNum + "", "", View.VISIBLE, View.VISIBLE);
                setKuaiOrMan(0);
            } else if (slowNum > 0) {
                StringUtil.setText(etAddchargeSl, slowNum + "", "", View.VISIBLE, View.VISIBLE);
                setKuaiOrMan(1);
            }
            final List<String> detailImgs = data.getDetailImgs();
            if (detailImgs != null && detailImgs.size() > 0) {
                localImgPathList.clear();
                for (int i = 0; i < detailImgs.size(); i++) {
                    String imgUrl = detailImgs.get(i);
                    if (StringUtil.isNotEmpty(imgUrl)) {
                        Glide.with(AddChargeActivity.this)
                                .load(imgUrl)
                                .asBitmap()
                                .placeholder(R.mipmap.ic_image_load_circle).dontAnimate()
                                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        try {
                                            String fileName = "charge_img_"
                                                    + String.valueOf(System.currentTimeMillis() + ".jpg");
                                            String imgPath = SystemUtil.saveFile(chargeImgFile, resource, fileName);
                                            RingLog.e(TAG, "imgPath = " + imgPath);
                                            localImgPathList.add(imgPath);
                                            if (localImgPathList.size() == detailImgs.size()) {
                                                showDialog();
                                                compressWithRx(localImgPathList);
                                            }
                                        } catch (IOException e) {
                                            RingLog.e(TAG, "e = " + e.toString());
                                            e.printStackTrace();
                                        }
                                    }
                                });
                    }
                }
            } else {
                disMissDialog();
            }
        } else {
            disMissDialog();
        }
    }

    @Override
    public void detailFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "detailFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void updateSuccess(AddChargeBean data) {
        disMissDialog();
        RingToast.show("编辑成功");
        DevRing.busManager().postEvent(new RefreshFragmentEvent(RefreshFragmentEvent.REFRESH_MYFRAGMET));
        DevRing.busManager().postEvent(new RefreshFragmentEvent(RefreshFragmentEvent.REFRESH_MAINFRAGMET));
        finish();
        RingLog.e(TAG, "updateSuccess()");
    }

    @Override
    public void updateFail(int code, String msg) {
        disMissDialog();
        RingToast.show("编辑失败");
        RingLog.e(TAG, "updateFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                localLat = amapLocation.getLatitude();//获取纬度
                localLng = amapLocation.getLongitude();//获取经度
                RingLog.d(TAG, "localLat = "
                        + localLat + ", localLng = "
                        + localLng);
                if (localLat > 0 && localLng > 0) {
                    mlocationClient.stopLocation();
                    if (StringUtil.isNotEmpty(uuid)) {
                        showDialog();
                        Map<String, String> mapHeader = UrlConstants.getMapHeader(AddChargeActivity.this);
                        mapHeader.put("lng", String.valueOf(localLng));
                        mapHeader.put("lat", String.valueOf(localLat));
                        mapHeader.put("key", AppConfig.SERVER_KEY);
                        mapHeader.put("uuid", uuid);
                        RingLog.d(TAG, "mapHeader =  " + mapHeader.toString());
                        String md5 = SignUtil.sign(mapHeader, "MD5");
                        RingLog.d(TAG, "md5 =  " + md5);
                        mPresenter.detail(localLng, localLat, uuid, md5);
                    }
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                RingLog.d(TAG, "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
