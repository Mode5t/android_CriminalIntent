package com.days.chenhy.android_criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID mId;
    private String mTitile;
    private Date mdate;
    private boolean mSolved;

    public Crime() {
        mId = UUID.randomUUID();
        mdate = new Date();
    }

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public String getmTitile() {
        return mTitile;
    }

    public void setmTitile(String mTitile) {
        this.mTitile = mTitile;
    }

    public Date getMdate() {
        return mdate;
    }

    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }
}
