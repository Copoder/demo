package com.iunin.demo.demo.fuction.fillout;

/**
 * @author leon@iunin.com
 */

public interface IInvoiceTypeSelector {
    int FP_JUAN_PIAO = 0;
    int FP_PU_PIAO = 1;
    int FP_DIANZI_PIAO = 2;
    int FP_ZHUAN_PIAO = 3;

    String LXDM_JUAN_PIAO = "025";
    String LXDM_PU_PIAO = "007";
    String LXDM_DIANZI_PIAO = "026";
    String LXDM_ZHUAN_PIAO = "004";

    //void setType(int type);
    //void getType();
    void setFpLxDm(String fplxdm);
    String getFpLxDm();
    void addListener(FpLxListener listener);
    void removeListener(FpLxListener listener);

}
