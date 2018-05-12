package com.yada.demo.OutTranServer.web;

import com.yada.demo.OutTranServer.config.NetsProperties;
import com.yada.demo.OutTranServer.config.SgqrProperties;
import com.yada.demo.OutTranServer.service.TransService;
import com.yada.demo.OutTranServer.util.DateUtils;
import com.yada.demo.OutTranServer.view.NetsTrans;
import com.yada.demo.OutTranServer.view.SgqrQrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/web")
public class WebController {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SgqrProperties sgqrProperties;
    private NetsProperties netsProperties;
    private TransService transService;

    @Autowired
    public WebController(SgqrProperties sgqrProperties, NetsProperties netsProperties, TransService transService) {
        this.sgqrProperties = sgqrProperties;
        this.netsProperties = netsProperties;
        this.transService = transService;
    }

    // 获取SGQR组装二维码页面
    @RequestMapping("/sgqr")
    public String sgqr(Model model) {

        SgqrQrCode qrCode = new SgqrQrCode();
        qrCode.setUnionMerNo(sgqrProperties.getUnionPay().getMerNo());
        qrCode.setWechatMerNo(sgqrProperties.getWechatPay().getMerNo());
        qrCode.setWechatTermNo(sgqrProperties.getWechatPay().getTermNo());
        qrCode.setSgqrId(sgqrProperties.getSgqrPay().getId());
        qrCode.setSgqrPostalCode(sgqrProperties.getSgqrPay().getPostalCode());
        qrCode.setSgqrMiscellaneous(sgqrProperties.getSgqrPay().getMiscellaneous());
        qrCode.setSgqrUnitNumber(sgqrProperties.getSgqrPay().getUnitNumber());

        model.addAttribute("qrCode", qrCode);
        return "sgqr";
    }

    // 请求NETS仿POS界面
    @RequestMapping("/nets")
    public String nets(Model model) {

        NetsTrans trans = new NetsTrans();
        trans.setMerNo(netsProperties.getMerNo());
        trans.setTermNo(netsProperties.getTermNo());
        trans.setCardNo(netsProperties.getCardNo());

        model.addAttribute("trans", trans);
        return "nets";
    }

    // 交易查询
    @RequestMapping("/query")
    public String query(Model model, String startDate, String endDate) throws ParseException {
        String start = DateUtils.getTodayStart();
        String end = DateUtils.getTodayEnd();

        if (startDate != null && !"".equals(startDate.trim())) {
            start = DateUtils.dateToStr(sdf.parse(startDate));
        } else {
            startDate = sdf.format(DateUtils.strToDate(start));
        }
        if (endDate != null && !"".equals(endDate.trim())) {
            end = DateUtils.dateToStr(sdf.parse(endDate));
        } else {
            endDate = sdf.format(DateUtils.strToDate(end));
        }

        model.addAttribute("result", transService.query(start, end));
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "query";
    }
}
