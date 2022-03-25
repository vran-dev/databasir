<html>
<body>
<div>
    <table style="-webkit-font-smoothing:antialiased;margin:25px auto;background-repeat:no-repeat;background-position:0px 100px;text-align:left;border-radius:5px; padding:50px; background:#f3f4f6;width:800px"
           border="0" cellspacing="0" cellpadding="0">
        <tbody style="background-color:#ffffff;">
        <tr>
            <td colspan="2" width="50%"
                style="font-family:'Microsoft Yahei','Helvetica Neue',sans-serif,SimHei;line-height:24px; padding: 0px; margin: 0px;">
                <div style="width: 700px;height: 80px;background: rgb(255, 255, 255);border-radius: 0px;">
                            <span style="background:#FFF;margin: 0px; padding:0px; vertical-align: center; margin-left: 55px;float: left;margin-top: 34px;">
                                Databasir
                            </span>
                    <span style="height: 14px;color: rgb(96, 108, 128);font-size: 14px;font-family: NotoSansCJKsc-Regular;font-weight: normal;text-align: center;letter-spacing: 0px;float: right;margin-top: 38px;margin-right: 10px;">专注于数据库文档管理</span>
                </div>
                <hr style=" border:none;border-top:1px solid #D8DDE4; margin-bottom: 30px;width: 640px;text-align:center"/>
            </td>
        </tr>
        <tr>
            <td style="line-height:30px; text-align:justify; font-family:'Microsoft Yahei','Helvetica Neue',sans-serif,SimHei;padding: 0px; margin: 0px; background:#FFF; position:relative;"
                colspan="4">
                <div style="color:#323A45;background-color:#FFF;padding:0px 50px;">
                    <div style="background-color: rgba(235, 247, 255, 0.41); padding:10px 20px ;">
                        <p style="color: rgb(32, 45, 64);font-size: 14px;font-family: PingFangSC-Medium;font-weight: 0;letter-spacing: 0px;line-height: 12px;margin:0;margin-top: 20px;">
                            数据库&nbsp;<b>${projectName}</b>&nbsp;有新的文档变更:
                        </p>
                        <div style="margin-left: 20px; margin-top: 10px;background-size: 3px;background-repeat: no-repeat;">
                            <span style="padding:5px 8px 5px 8px; border:none ;background-color: transparent;">
                                <table style="text-align:center;width: 100%;color: #303133;font-size: 14px;font-family: PingFangSC-Medium;font-weight: 0;letter-spacing: 0px;line-height: 12px;">
                                        <#list diffs as diff >
                                            <#if diff.diffType == "MODIFIED">
                                                <tr style="color:#E6A23C;background-color:#fdf6ec;padding:8px;border: 1px solid transparent;border-collapse: collapse;">
                                                    <td style="padding:8px;border-collapse: collapse;">${diff.tableName}</td>
                                                    <td style="padding:8px;border-collapse: collapse;">修改</td>
                                                </tr>
                                            <#elseif diff.diffType == "REMOVED">
                                                <tr style="color:#F56C6C;background-color:#fef0f0;padding:8px;border: 1px solid transparent;border-collapse: collapse;">
                                                    <td style="padding:8px;border-collapse: collapse;">${diff.tableName}</td>
                                                    <td style="padding:8px;border-collapse: collapse;">删除</td>
                                                </tr>
                                            <#elseif diff.diffType == "ADDED">
                                                <tr style="color:#67C23A;background-color:#f0f9eb;padding:8px;border: 1px solid transparent;border-collapse: collapse;">
                                                    <td style="padding:8px;border-collapse: collapse;">${diff.tableName}</td>
                                                    <td style="padding:8px;border-collapse: collapse;">新增</td>
                                                </tr>
                                            </#if>
                                        </#list>
                                </table>
                            </span>
                        </div>
                        <p style="color:#606266;margin-top: 0px;">
                            详细变更内容请登录平台通过版本差异对比查看
                        </p>
                    </div>
                </div>
            </td>
        </tr>

        <tr>
            <td style="text-align:center; font-family:'Microsoft Yahei','Helvetica Neue',sans-serif,SimHei; padding: 0px; margin: 0px; background-color:#FFF; position:relative;"
                colspan="4">
                <div style="font-size:10px;color:#323A45; margin: 0px;padding:10px 40px;background-color:#FFF">
                    <hr style=" border:none;border-top:1px solid #D8DDE4; margin-bottom: 30px;width: 640px;text-align:center"/>
                    <p style="font-size:10px; line-height: 10px; color:#606c80;text-align: center">
                        <a href="https://doc.databasir.com" style="color: rgb(0, 102, 255);text-decoration:none;"
                           target="_blank">系统文档</a>
                        <span style="color: rgb(133, 146, 165);">｜
                            <a href="https://github.com/vran-dev/databasir"
                               style="color: rgb(0, 102, 255);text-decoration:none;"
                               target="_blank">Databasir</a></span>
                    </p>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <br/>
</div>
</body>
</html>

