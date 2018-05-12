package com.yada.demo.OutTranServer.web;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.yada.demo.OutTranServer.service.TransService;
import com.yada.demo.OutTranServer.util.QrCodeImage;
import com.yada.demo.OutTranServer.view.NetsTrans;
import com.yada.demo.OutTranServer.view.SgqrQrCode;
import com.yada.demo.OutTranServer.view.SgqrTrans;
import com.yada.demo.OutTranServer.view.TransResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Value("${qrCode.width}")
    private int width;
    @Value("${qrCode.height}")
    private int height;

    private TransService transService;

    @Autowired
    public ApiController(TransService transService) {
        this.transService = transService;
    }

    // 生成SGQR二维码
    @RequestMapping(value = "/qrCode")
    public String createQrCode(SgqrQrCode qrCode, HttpServletResponse response) throws IOException, WriterException {
        // TODO 组装二维码contents
        String contents = "00020101021102164761360000000*1704155123456789123451110123456789012153123456789012341531250003440001034450003445311000126330015SG.COM.DASH.WWW0110000005550127670014A00000076200010120COM.LQDPALLIANCE.WWW021512345678901234503020028660011SG.COM.OCBC0147OCBCP2P629A358D-ECE7-4554-AD56-EBD12D84CA7E4F7329500006SG.EZI013603600006-76bb-4a5a-aa1a-fbcb64d6ecf530850013SG.COM.EZLINK01201234567890123456-1230204SGQR0324A123456,B123456,C12345670404A23X31260008COM.GRAB0110A93FO3230Q32390007COM.DBS011012345678900210123456789033900011SG.COM.NETS01230201401832831128823590002150001118703240000308885872010901199084E5DC3D834430017COM.QQ.WEIXIN.PAY011012345678900204123435660010SG.COM.UOB014845D233507F5E8C306E3871A4E9FACA601A80C114B5645E5D36940009SG.PAYNOW010100216+6212345678901230301004351234567890123456789012345678901234505082020123137270009SG.AIRPAY0110A11BC0000X51860007SG.SGQR0112201803070317020701.0003030608100604020205031380609Counter010708201804075204581453037025802SG5916FOOD XYZ PTE LTD6009SINGAPORE610608100663043320";

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height);
        QrCodeImage.writeToStream(bitMatrix, "png", bos);

        byte[] imageBytes = bos.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String imageString = encoder.encode(imageBytes);

        bos.close();
        return imageString;
    }

    // 模拟SGQR的APP端交易
    @RequestMapping("/sgqr")
    public TransResult sgqrTrans(SgqrTrans trans) {
        return transService.sgqrTrans(trans);
    }

    // 模拟NETS的POS客户端交易
    @RequestMapping("/nets")
    public TransResult netsTrans(NetsTrans trans) {
        return transService.netsTrans(trans);
    }
}
