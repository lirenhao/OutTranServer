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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

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
    public String createQrCode(SgqrQrCode qrCode) throws IOException, WriterException {
        // 组装二维码contents
        String contents = transService.qrCode(qrCode);
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
    @CrossOrigin(origins = "*")
    public TransResult sgqrTrans(@RequestBody SgqrTrans trans) {
        return transService.sgqrTrans(trans);
    }

    // 模拟NETS的POS客户端交易
    @RequestMapping("/nets")
    @CrossOrigin(origins = "*")
    public TransResult netsTrans(NetsTrans trans) {
        return transService.netsTrans(trans);
    }
}
