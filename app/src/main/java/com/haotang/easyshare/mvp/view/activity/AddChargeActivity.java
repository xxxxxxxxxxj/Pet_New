package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.di.component.activity.DaggerAddChargeActivityCommponent;
import com.haotang.easyshare.di.module.activity.AddChargeActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
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
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
public class AddChargeActivity extends BaseActivity<AddChargePresenter> implements IAddChargeView {
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
    private List<CommentImg> imgList = new ArrayList<CommentImg>();
    private List<String> imgPathList = new ArrayList<String>();
    private CommentImgAdapter commentImgAdapter;
    private static final int IMG_NUM = 3;
    private AddChargeBoDa addChargeBoDa;
    private PopupWindow pWinBottomDialog;
    private int payWay = 0;
    private String[] time =
            {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00"
                    , "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
    private Map<String, RequestBody> filedMap = new HashMap<String, RequestBody>();
    private Map<String, String> paramsMap = new HashMap<String, String>();
    private double lat;
    private double lng;
    private int upOrDown;
    private int kuaiOrMan;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_add_charge;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerAddChargeActivityCommponent.builder().
                addChargeActivityModule(new AddChargeActivityModule(this, this)).build().inject(this);
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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
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
                    SystemUtil.goPhotoView(AddChargeActivity.this, position, imgPathList, true);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            if (data != null) {
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
        DevRing.activityStackManager().exitActivity(this); //退出activity
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

    @OnClick({R.id.iv_titlebar_back, R.id.tv_titlebar_other, R.id.rl_addcharge_zdz, R.id.ll_addcharge_kuai, R.id.ll_addcharge_man, R.id.rl_addcharge_zffs, R.id.ll_addcharge_up, R.id.ll_addcharge_down, R.id.rl_addcharge_kfsj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_titlebar_other:
                paramsMap.put("lng", String.valueOf(lng));
                paramsMap.put("lat", String.valueOf(lat));
                paramsMap.put("telephone", etAddchargePhone.getText().toString().trim());
                paramsMap.put("title", etAddchargeZmc.getText().toString().trim());
                paramsMap.put("address", tvAddchargeZdz.getText().toString().trim());
                paramsMap.put("electricityPrice", etAddchargeCdf.getText().toString().trim() + "_" + tvAddchargeKfsj.getText().toString().trim());
                paramsMap.put("parkingPrice", etAddchargeTcf.getText().toString().trim() + "_" + upOrDown);
                paramsMap.put("serviceFee", etAddchargeFwf.getText().toString().trim());
                paramsMap.put("payWay", tvAddchargeZffs.getText().toString().trim());
                paramsMap.put("openTime", tvAddchargeKfsj.getText().toString().trim());
                paramsMap.put("remark", etAddchargeBzsm.getText().toString().trim());
                if (kuaiOrMan == 0) {//快充
                    paramsMap.put("fastNum", etAddchargeSl.getText().toString().trim());
                } else if (kuaiOrMan == 1) {//慢充
                    paramsMap.put("slowNum", etAddchargeSl.getText().toString().trim());
                }
                for(int i=0;i<imgPathList.size();i++){
                    //构建要上传的文件
                    File file = new File(imgPathList.get(i));
                    // 创建 RequestBody，用于封装构建RequestBody
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    // MultipartBody.Part  和后端约定好Key，这里的partName是用image
                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    filedMap.put("file",requestFile);
                }
                mPresenter.save(paramsMap, filedMap);
                break;
            case R.id.rl_addcharge_zdz:
                startActivity(new Intent(AddChargeActivity.this, SelectAddressActivity.class));
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

    private void setWxOrZfb(int flag) {
        if (flag == 1) {//微信
            payWay = 1;
            addChargeBoDa.getIvAddchargeBottomWx().setImageResource(R.mipmap.icon_addcharge_select);
            addChargeBoDa.getIvAddchargeBottomZfb().setImageResource(R.mipmap.icon_addcharge_unselect);
        } else if (flag == 2) {//支付宝
            payWay = 2;
            addChargeBoDa.getIvAddchargeBottomWx().setImageResource(R.mipmap.icon_addcharge_unselect);
            addChargeBoDa.getIvAddchargeBottomZfb().setImageResource(R.mipmap.icon_addcharge_select);
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
                } else if (payWay == 2) {//支付宝
                    addChargeBoDa.getIvAddchargeBottomWx().setImageResource(R.mipmap.icon_addcharge_unselect);
                    addChargeBoDa.getIvAddchargeBottomZfb().setImageResource(R.mipmap.icon_addcharge_select);
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
                    setWxOrZfb(1);
                }
            });
            addChargeBoDa.getRlAddchargeBottomZfb().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setWxOrZfb(2);
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
        finish();
    }

    @Override
    public void saveFail(int code, String msg) {
        RingLog.e(TAG, "saveFail() status = " + code + "---desc = " + msg);
    }
}
