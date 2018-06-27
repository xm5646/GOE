package com.project.goe.projectgeodbserver.controller;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.project.goe.projectgeodbserver.entity.SmsCode;
import com.project.goe.projectgeodbserver.service.RedisService;
import com.project.goe.projectgeodbserver.statusType.SmsType;
import com.project.goe.projectgeodbserver.util.SmsUtil;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private RedisService redisService;


    @GetMapping("/getCodeByAccount")
    public RetMsg getSmsCodeByAccount(String account, String phoneNumber){
        RetMsg retMsg = new RetMsg();

        //创建验证码
        SmsCode code = new SmsCode();
        code.setOperation(SmsType.INIT_USER_INFO);
        code.setAccount(account);
        String codeStr = SmsUtil.getSmsCode();
        code.setCode(codeStr);

        //判断该用户是否在分钟内发送过验证码,如果没有就调用阿里短信服务,发送短信验证码
        try {
            if (redisService.ExistsKey(account)){
                retMsg.setCode(400);
                retMsg.setSuccess(false);
                retMsg.setMessage("已经发送验证码,请在2分钟之后再试");
            }else {
                SendSmsResponse sendSmsResponse = SmsUtil.sendSms(phoneNumber, codeStr);
                QuerySendDetailsResponse querySendDetailsResponse = SmsUtil.querySendDetails(sendSmsResponse.getBizId(),phoneNumber);
                if(querySendDetailsResponse.getCode() != null && querySendDetailsResponse.getCode().equals("OK")) {
                    retMsg.setSuccess(true);
                    retMsg.setCode(200);
                    retMsg.setMessage("发送成功");
                    //保存到redis
                    redisService.setObj(account, code);
                }
            }
        }catch (ClientException err){
            retMsg.setCode(400);
            retMsg.setSuccess(false);
            retMsg.setMessage("发送短信验证码失败");
        }
        return  retMsg;
    }

}
