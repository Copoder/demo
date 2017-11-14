package com.iunin.demo.demo.fuction.fillout;

/**
 * @author leon@iunin.com
 */
public interface FpLxListener {
    void onFpLxChanged(String fplxdm);

    FpLxListener NULL_IMPL = new FpLxListener() {
        @Override
        public void onFpLxChanged(String fplxdm) {

        }
    };
}
