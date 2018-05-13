package com.yada.demo.OutTranServer.service;

import com.yada.demo.OutTranServer.config.SgqrProperties;
import com.yada.demo.OutTranServer.config.TransProperties;
import com.yada.demo.OutTranServer.dao.TransDao;
import com.yada.demo.OutTranServer.model.Trans;
import com.yada.demo.OutTranServer.util.DateUtils;
import com.yada.demo.OutTranServer.view.NetsTrans;
import com.yada.demo.OutTranServer.view.SgqrQrCode;
import com.yada.demo.OutTranServer.view.SgqrTrans;
import com.yada.demo.OutTranServer.view.TransResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransService {

    @Value("${qrCode.format}")
    private String format;

    private TransDao transDao;
    private TransProperties transProperties;
    private SgqrProperties sgqrProperties;

    @Autowired
    public TransService(TransDao transDao, TransProperties transProperties, SgqrProperties sgqrProperties) {
        this.transDao = transDao;
        this.transProperties = transProperties;
        this.sgqrProperties = sgqrProperties;
    }

    // 组装二维码contents
    public String qrCode(SgqrQrCode qrCode) {
        StringBuilder union = new StringBuilder()
                .append(sgqrProperties.getUnionPay().getAcqNo())
                .append(sgqrProperties.getUnionPay().getForwNo())
                .append(qrCode.getUnionMerNo());

        StringBuilder wechat = new StringBuilder()
                .append("00")
                .append(addZero(sgqrProperties.getWechatPay().getGui().length()))
                .append(sgqrProperties.getWechatPay().getGui())
                .append("01")
                .append(addZero(qrCode.getWechatMerNo().length()))
                .append(qrCode.getWechatMerNo())
                .append("02")
                .append(addZero(qrCode.getWechatTermNo().length()))
                .append(qrCode.getWechatTermNo());

        StringBuilder sgqr = new StringBuilder()
                .append("00")
                .append(addZero(sgqrProperties.getSgqrPay().getGui().length()))
                .append(sgqrProperties.getSgqrPay().getGui())
                .append("01")
                .append(addZero(qrCode.getSgqrId().length()))
                .append(qrCode.getSgqrId())
                .append("02")
                .append(addZero(sgqrProperties.getSgqrPay().getVersion().length()))
                .append(sgqrProperties.getSgqrPay().getVersion())
                .append("03")
                .append(addZero(qrCode.getSgqrPostalCode().length()))
                .append(qrCode.getSgqrPostalCode())
                .append("04")
                .append(addZero(sgqrProperties.getSgqrPay().getLevelNumber().length()))
                .append(sgqrProperties.getSgqrPay().getLevelNumber())
                .append("05")
                .append(addZero(qrCode.getSgqrUnitNumber().length()))
                .append(qrCode.getSgqrUnitNumber())
                .append("06")
                .append(addZero(qrCode.getSgqrMiscellaneous().length()))
                .append(qrCode.getSgqrMiscellaneous())
                .append("07")
                .append(addZero(sgqrProperties.getSgqrPay().getNewRevisionDate().length()))
                .append(sgqrProperties.getSgqrPay().getNewRevisionDate());

        return String.format(format, addZero(union.length()) + union.toString(),
                addZero(wechat.length()) + wechat.toString(), addZero(sgqr.length()) + sgqr.toString());
    }

    private String addZero(int length) {
        StringBuilder sb = new StringBuilder();
        if (length < 10) {
            sb.append("0").append(length);
        } else {
            sb.append(length);
        }
        return sb.toString();
    }

    // TODO 模拟SGQR交易
    public TransResult sgqrTrans(SgqrTrans trans) {
        return new TransResult();
    }

    // 模拟NETS交易
    public TransResult netsTrans(NetsTrans netsTrans) {
        Trans trans = new Trans();
        trans.setBatchNo(transProperties.getBatchNo());
        trans.setInvoiceNo(transProperties.getInvoiceNo());
        trans.setTranAmt(netsTrans.getTranAmt());
        trans.setTranType("80");
        trans.setTranDate(DateUtils.getCurrent());
        trans.setChannel("nets");
        trans.setCardNo(netsTrans.getCardNo());
        trans.setMerNo(netsTrans.getMerNo());
        trans.setTermNo(netsTrans.getTermNo());
        trans.setAuthNo(transProperties.getAuthNo());
        trans.setRrn(transProperties.getRrn());
        trans.setMcc(netsTrans.getMerNo().substring(7, 11));
        trans.setRespCode("00");
        trans = transDao.saveAndFlush(trans);

        TransResult result = new TransResult();
        result.setResCode(trans.getRespCode());
        result.setTranAmt(trans.getTranAmt());
        result.setTranDate(trans.getTranDate());
        result.setCardNo(trans.getCardNo());
        result.setMerNo(trans.getMerNo());
        result.setTermNo(trans.getTermNo());
        return result;
    }

    // 交易查询
    public List<Trans> query(String startDate, String endDate) {
        return transDao.findByTranDateBetween(startDate, endDate);
    }
}
