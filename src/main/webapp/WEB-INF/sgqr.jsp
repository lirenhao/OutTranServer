<%@ page contentType='text/html; charset=UTF-8' pageEncoding='UTF-8' %>
<!DOCTYPE html>
<html lang='zh-CN'>
<head>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <title>NETS模拟</title>
    <link rel='stylesheet' href='/css/bootstrap.min.css'/>
    <script src='/js/jquery-3.3.1.min.js'></script>
    <script src='/js/bootstrap.min.js'></script>
    <script src='/js/jquery.validate.min.js'></script>
    <script src='/js/jquery.validate.messages_zh.min.js'></script>
</head>
<body>
<div class='container'>
    <ul class='nav nav-tabs nav-justified'>
        <li role='presentation'><a href='/web/query'>交易查询</a></li>
        <li role='presentation' class='active'><a href='/web/sgqr'>SGQR模拟</a></li>
        <li role='presentation'><a href='/web/nets'>NETS模拟</a></li>
    </ul>
    <form id='sgqrForm'>
        <div class='page-header'>
            <h1>
                <small>模拟生成SGQR二维码</small>
                <button type='submit' class='btn btn-info'>生成二维码</button>
            </h1>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">银联</div>
            <div class="panel-body">
                <div class='form-group'>
                    <label for='unionMerNo'>银联商户号</label>
                    <input type='text' class='form-control' id='unionMerNo' name='unionMerNo'
                           value='${qrCode.unionMerNo}'>
                </div>
            </div>
            <div class="panel-heading">微信</div>
            <div class="panel-body">
                <div class='form-group'>
                    <label for='wechatMerNo'>微信商户号</label>
                    <input type='text' class='form-control' id='wechatMerNo' name='wechatMerNo'
                           value='${qrCode.wechatMerNo}'>
                </div>
                <div class='form-group'>
                    <label for='wechatTermNo'>微信终端号</label>
                    <input type='text' class='form-control' id='wechatTermNo' name='wechatTermNo'
                           value='${qrCode.wechatTermNo}'>
                </div>
            </div>
            <div class="panel-heading">SGQR</div>
            <div class="panel-body">
                <div class='form-group'>
                    <label for='sgqrId'>SGQR ID</label>
                    <input type='text' class='form-control' id='sgqrId' name='sgqrId' value='${qrCode.sgqrId}'>
                </div>
                <div class='form-group'>
                    <label for='sgqrPostalCode'>Postal Code</label>
                    <input type='text' class='form-control' id='sgqrPostalCode' name='sgqrPostalCode'
                           value='${qrCode.sgqrPostalCode}'>
                </div>
                <div class='form-group'>
                    <label for='sgqrMiscellaneous'>Miscellaneous</label>
                    <input type='text' class='form-control' id='sgqrMiscellaneous' name='sgqrMiscellaneous'
                           value='${qrCode.sgqrMiscellaneous}'>
                </div>
                <div class='form-group'>
                    <label for='sgqrUnitNumber'>Unit Number</label>
                    <input type='text' class='form-control' id='sgqrUnitNumber' name='sgqrUnitNumber'
                           value='${qrCode.sgqrUnitNumber}'>
                </div>
            </div>
        </div>

    </form>
</div>
<div class='modal fade ' id='myModal' aria-labelledby='myModalLabel' aria-hidden='true' role='dialog'>
    <div class='modal-dialog'>
        <div class='modal-content'>
            <img class='img-rounded center-block' id='modalImage'>
        </div>
    </div>
</div>
<script type='text/javascript'>
    //表单验证
    $('#sgqrForm').validate({
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            error.appendTo(element.closest('.form-group'));
        },
        highlight: function (element) {
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        success: function (label) {
            label.closest('.form-group').removeClass('has-error').addClass('has-success');
        },
        submitHandler: function (form) {
            $.post('/api/qrCode', $(form).serializeArray(), function (image, status, xhr) {
                if (xhr.status === 200) {
                    $('#modalImage').attr('src', 'data:image/png;base64,' + image);
                    $('#myModal').modal({show: true});
                }
            });
        }
    });
    $('#myModal').on('hidden.bs.modal', function () {
        $('#modalImage').attr('src', '');
    })
</script>
</body>