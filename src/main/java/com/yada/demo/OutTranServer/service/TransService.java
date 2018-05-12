package com.yada.demo.OutTranServer.service;

import com.yada.demo.OutTranServer.config.TransProperties;
import com.yada.demo.OutTranServer.dao.TransDao;
import com.yada.demo.OutTranServer.model.Trans;
import com.yada.demo.OutTranServer.util.DateUtils;
import com.yada.demo.OutTranServer.view.NetsTrans;
import com.yada.demo.OutTranServer.view.SgqrTrans;
import com.yada.demo.OutTranServer.view.TransResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransService {

    private TransDao transDao;
    private TransProperties transProperties;

    @Autowired
    public TransService(TransDao transDao, TransProperties transProperties) {
        this.transDao = transDao;
        this.transProperties = transProperties;
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
