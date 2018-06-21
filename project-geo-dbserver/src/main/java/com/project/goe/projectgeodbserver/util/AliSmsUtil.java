package com.project.goe.projectgeodbserver.util;

//static final String accessKeyId = "LTAInsLbmn3XvGvg";
//static final String accessKeySecret = "a3qpFIjIu6z4AuiXUFJvPw8hfjIGiM";


import com.aliyuncs.DefaultAcsClient;  
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;  
import com.aliyuncs.exceptions.ServerException;  
import com.aliyuncs.profile.DefaultProfile;  
import com.aliyuncs.profile.IClientProfile;  

  
/**  
 * @author: py 
 * @version:2017年1月13日 下午2:40:28  
 * com.kp.sms.TestSms.java 
 * @Desc  
 */  
public class AliSmsUtil {  
    public static String regionId="cn-hangzhou";//机房信息,可以不用更改  
    /**********需要准备的参数**************/  
    public static String accessKey="LTAInsLbmn3XvGvg";//需要修改  
    public static String accessSecret="a3qpFIjIu6z4AuiXUFJvPw8hfjIGiM";//需要修改  
    public static String code="SMS_41635111";//需要修改  
    public static String signName="测试99";//需要修改  
    /**********************************/  
      
      
    public static void main(String[] args) {  
          
        String phone="13520580169";  
        String time =getChinaDateByMM(System.currentTimeMillis());  
        //根据自己定义的短信模板，修改  
        String jsonStr="{\"name\":\"小明\",\"code\":\"12312\",\"time\":\""+time +"\"}";  
        test(phone, jsonStr,code,signName);  
  
    }  
  
      
       public static void test(String phone, String jsonStr, String code, String signName) {          
           try {  
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKey, accessSecret);  
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",  "sms.aliyuncs.com");  
            IAcsClient client = new DefaultAcsClient(profile);  
            SendSmsRequest request = new SendSmsRequest();  
                //管理控制台中配置的短信签名（状态必须是验证通过）  
                request.setSignName(signName);  
                //管理控制台中配置的审核通过的短信模板的模板CODE（状态必须是验证通过）  
                 request.setTemplateCode(code);  
//              短信模板中的变量；数字需要转换为字符串；个人用户每个变量长度必须小于15个字符。  
//               例如:短信模板为：“接受短信验证码${no}”,此参数传递{“no”:”123456”}，用户将接收到[短信签名]接受短信验证码123456  
//                ((Object) request).setParamString(jsonStr);  
                //目标手机号，多个手机号可以逗号分隔  
                request.setPhoneNumbers(phone);  
//              request.setVersion(version);  
                  
                SendSmsResponse httpResponse = client.getAcsResponse(request);  
                String requestId = httpResponse.getRequestId();  
                System.err.println("requestId:"+requestId);  
            } catch (ServerException e) {  
                e.printStackTrace();  
            }  
            catch (ClientException e) {  
                e.printStackTrace();  
            }  
        }  
         
         
        /** 
         * 使用毫秒转换为中文日期 
         * @param tmpDateInt 
         * @return 
         */  
        public static String getChinaDateByMM(long time){  
            String ret_date = "";  
            java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy年MM月dd日");  
            ret_date = formatter.format(time);  
            return ret_date;  
        }  
} 