package com.haotang.easyshare.shareutil.login;


import com.haotang.easyshare.shareutil.login.result.BaseToken;
import com.haotang.easyshare.shareutil.login.result.BaseUser;

/**
 * Created by shaohui on 2016/12/3.
 */

public class LoginResult {

    private BaseToken mToken;

    private BaseUser mUserInfo;

    private int mPlatform;
    private String mCcode;

    public BaseToken getmToken() {
        return mToken;
    }

    public void setmToken(BaseToken mToken) {
        this.mToken = mToken;
    }

    public BaseUser getmUserInfo() {
        return mUserInfo;
    }

    public void setmUserInfo(BaseUser mUserInfo) {
        this.mUserInfo = mUserInfo;
    }

    public int getmPlatform() {
        return mPlatform;
    }

    public void setmPlatform(int mPlatform) {
        this.mPlatform = mPlatform;
    }

    public String getmCcode() {
        return mCcode;
    }

    public void setmCcode(String mCcode) {
        this.mCcode = mCcode;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "mToken=" + mToken +
                ", mUserInfo=" + mUserInfo +
                ", mPlatform=" + mPlatform +
                '}';
    }

    public LoginResult(int platform,String code) {
        mPlatform = platform;
        mCcode = code;
    }

    public LoginResult(int platform, BaseToken token) {
        mPlatform = platform;
        mToken = token;
    }

    public LoginResult(int platform, BaseToken token, BaseUser userInfo) {
        mPlatform = platform;
        mToken = token;
        mUserInfo = userInfo;
    }

    public int getPlatform() {
        return mPlatform;
    }

    public void setPlatform(int platform) {
        this.mPlatform = platform;
    }

    public BaseToken getToken() {
        return mToken;
    }

    public void setToken(BaseToken token) {
        mToken = token;
    }

    public BaseUser getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(BaseUser userInfo) {
        mUserInfo = userInfo;
    }
}
