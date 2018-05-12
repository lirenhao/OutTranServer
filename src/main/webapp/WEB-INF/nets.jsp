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
        <li role='presentation'><a href='/web/sgqr'>SGQR模拟</a></li>
        <li role='presentation' class='active'><a href='/web/nets'>NETS模拟</a></li>
    </ul>
    <form id='netsForm'>
        <div class='page-header'>
            <h1>
                <small>NETS模拟交易</small>
                <button type='submit' class='btn btn-info'>刷卡消费</button>
            </h1>
        </div>
        <div class='row'>
            <div class='col-lg-6'>
                <div class='panel panel-default'>
                    <div class='panel-heading'>交易参数</div>
                    <div class='panel-body'>
                        <div class='form-group'>
                            <label for='merNo'>商户号</label>
                            <input type='text' class='form-control' id='merNo' name='merNo'
                                   value='${trans.merNo}'>
                        </div>
                        <div class='form-group'>
                            <label for='termNo'>终端号</label>
                            <input type='text' class='form-control' id='termNo' name='termNo'
                                   value='${trans.termNo}'>
                        </div>
                        <div class='form-group'>
                            <label for='cardNo'>卡号</label>
                            <input type='text' class='form-control' id='cardNo' name='cardNo'
                                   value='${trans.cardNo}'>
                        </div>
                        <div class='form-group'>
                            <label for='tranAmt'>金额</label>
                            <input type='number' class='form-control' id='tranAmt' name='tranAmt'
                                   value='${trans.tranAmt}'>
                        </div>
                    </div>
                </div>
            </div>
            <div class='col-lg-6'>
                <div class='panel panel-default'>
                    <div class='panel-heading'>交易日志</div>
                    <div class='panel-body'>
                        <pre id='transLog' class='pre-scrollable' style='height: 286px'></pre>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script type='text/javascript'>
    $.validator.addMethod('amount', function (value, element) {
            var isValidAmount = /^\d{0,4}(\.\d{0,2})?$/.test(value);
            return this.optional(element) || isValidAmount;
        }
    );
    //表单验证
    $('#netsForm').validate({
        rules: {
            tranAmt: {
                required: true,
                amount: true
            }
        },
        messages: {
            tranAmt: {
                required: '请输入金额',
                amount: '金额格式错误'
            }
        },
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
            $.ajax({
                type: 'POST',
                url: '/api/nets',
                data: $(form).serializeArray(),
                beforeSend: function () {
                    $('#transLog').append('  开始发送<br>');
                },
                success: function (data) {
                    $('#transLog').append('  接收成功<br>');
                    if (data.resCode === '00') {
                        $('#transLog').append('  交易成功');
                        $('#transLog').append('<li class="list-group-item">卡号:' + data.cardNo + '</li>');
                        $('#transLog').append('<li class="list-group-item">金额:' + data.tranAmt + '</li>');
                        $('#transLog').append('<li class="list-group-item">时间:' + data.tranDate + '</li>');
                        $('#transLog').append('<li class="list-group-item">商户号:' + data.merNo + '</li>');
                        $('#transLog').append('<li class="list-group-item">终端号:' + data.termNo + '</li><br>');
                    } else {
                        $('#transLog').append('  交易失败:' + data.resMsg);
                    }
                },
                error: function (xhr) {
                    $('#transLog').append('  发送失败:' + xhr.status + '<br>');
                },
                complete: function () {
                    $("#transLog").scrollTop($("#transLog").prop("scrollHeight"));
                }
            });
        }
    });
</script>
</body>