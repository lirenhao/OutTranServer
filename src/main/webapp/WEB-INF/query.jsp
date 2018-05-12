<%@ page contentType='text/html; charset=UTF-8' pageEncoding='UTF-8' %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>交易查询</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/bootstrap-datetimepicker.min.css">
    <script src='/js/jquery-3.3.1.min.js'></script>
    <script src='/js/jquery.validate.min.js'></script>
    <script src='/js/jquery.validate.messages_zh.min.js'></script>
    <script src='/js/bootstrap.min.js'></script>
    <script src='/js/bootstrap-datetimepicker.min.js'></script>
    <script src='/js/bootstrap-datetimepicker.zh-CN.js' charset='UTF-8'></script>
</head>
<body>
<div class="container">
    <ul class="nav nav-tabs nav-justified">
        <li role="presentation" class="active"><a href="/web/query">交易查询</a></li>
        <li role="presentation"><a href="/web/sgqr">SGQR模拟</a></li>
        <li role="presentation"><a href="/web/nets">NETS模拟</a></li>
    </ul>
    <div class='page-header'>
        <h1>
            <small>交易查询</small>
            <form class="form-inline" action="/web/query" method="post">
                <div class="form-group">
                    <label class="sr-only" for="startDate">开始时间</label>
                    <input type="text" class="form-control" id="startDate" name="startDate" placeholder="开始时间"
                           value="${startDate}">
                </div>
                <small>-</small>
                <div class="form-group">
                    <label class="sr-only" for="endDate">结束时间</label>
                    <input type="text" class="form-control" id="endDate" name="endDate" placeholder="结束时间"
                           value="${endDate}">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-info">查询</button>
                </div>
            </form>
        </h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">查询结果</div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>流水号</th>
                <th>批次号</th>
                <th>票据号</th>
                <th>交易金额</th>
                <th>交易类型</th>
                <th>交易时间</th>
                <th>交易渠道</th>
                <th>卡号</th>
                <th>商户号</th>
                <th>终端号</th>
                <th>授权号</th>
                <th>参考号</th>
                <th>MCC码</th>
                <th>返回码</th>
            </tr>
            </thead>
            <tbody class="pre-scrollable">
            <c:forEach items="${result}" var="item">
                <tr>
                    <th>${item.traceNo}</th>
                    <th>${item.batchNo}</th>
                    <th>${item.invoiceNo}</th>
                    <th>${item.tranAmt}</th>
                    <th>${item.tranType}</th>
                    <th>${item.tranDate}</th>
                    <th>${item.channel}</th>
                    <th>${item.cardNo}</th>
                    <th>${item.merNo}</th>
                    <th>${item.termNo}</th>
                    <th>${item.authNo}</th>
                    <th>${item.rrn}</th>
                    <th>${item.mcc}</th>
                    <th>${item.respCode}</th>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript">
    $('#startDate').datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true,
        todayBtn: true,
        language: 'zh-CN'
    });
    $('#endDate').datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true,
        todayBtn: true,
        language: 'zh-CN'
    });
</script>
</body>