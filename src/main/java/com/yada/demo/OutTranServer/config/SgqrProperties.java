package com.yada.demo.OutTranServer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sgqr")
public class SgqrProperties {

    private UnionPay unionPay;

    private WechatPay wechatPay;

    private SgqrPay sgqrPay;

    public UnionPay getUnionPay() {
        return unionPay;
    }

    public void setUnionPay(UnionPay unionPay) {
        this.unionPay = unionPay;
    }

    public WechatPay getWechatPay() {
        return wechatPay;
    }

    public void setWechatPay(WechatPay wechatPay) {
        this.wechatPay = wechatPay;
    }

    public SgqrPay getSgqrPay() {
        return sgqrPay;
    }

    public void setSgqrPay(SgqrPay sgqrPay) {
        this.sgqrPay = sgqrPay;
    }

    public static class UnionPay {

        private String acqNo;
        private String forwNo;
        private String merNo;

        public String getAcqNo() {
            return acqNo;
        }

        public void setAcqNo(String acqNo) {
            this.acqNo = acqNo;
        }

        public String getForwNo() {
            return forwNo;
        }

        public void setForwNo(String forwNo) {
            this.forwNo = forwNo;
        }

        public String getMerNo() {
            return merNo;
        }

        public void setMerNo(String merNo) {
            this.merNo = merNo;
        }
    }

    public static class WechatPay {

        private String gui;
        private String merNo;
        private String termNo;

        public String getGui() {
            return gui;
        }

        public void setGui(String gui) {
            this.gui = gui;
        }

        public String getMerNo() {
            return merNo;
        }

        public void setMerNo(String merNo) {
            this.merNo = merNo;
        }

        public String getTermNo() {
            return termNo;
        }

        public void setTermNo(String termNo) {
            this.termNo = termNo;
        }
    }

    public static class SgqrPay {

        private String gui;
        private String id;
        private String version;
        private String postalCode;
        private String levelNumber;
        private String unitNumber;
        private String miscellaneous;
        private String newRevisionDate;

        public String getGui() {
            return gui;
        }

        public void setGui(String gui) {
            this.gui = gui;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getLevelNumber() {
            return levelNumber;
        }

        public void setLevelNumber(String levelNumber) {
            this.levelNumber = levelNumber;
        }

        public String getUnitNumber() {
            return unitNumber;
        }

        public void setUnitNumber(String unitNumber) {
            this.unitNumber = unitNumber;
        }

        public String getMiscellaneous() {
            return miscellaneous;
        }

        public void setMiscellaneous(String miscellaneous) {
            this.miscellaneous = miscellaneous;
        }

        public String getNewRevisionDate() {
            return newRevisionDate;
        }

        public void setNewRevisionDate(String newRevisionDate) {
            this.newRevisionDate = newRevisionDate;
        }
    }
}
