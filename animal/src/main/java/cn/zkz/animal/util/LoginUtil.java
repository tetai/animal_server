package cn.zkz.animal.util;

import cn.zkz.animal.Properties;
import cn.zkz.animal.controller.OperateAnimalController;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class LoginUtil {

    @Autowired
    private Properties properties;

    private static Logger logger = LogManager.getLogger(OperateAnimalController.class);
    public String getUserId(HttpServletRequest request) {
        String code = request.getHeader("wx-code");
        if (StringUtils.isBlank(code)) {
            return null;
        }
        String appId = properties.getAppId();
        String sec = properties.getSecret();

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+sec+"&js_code="+code+"&grant_type=authorization_code";
        Long startTime = System.currentTimeMillis();
        JSONObject o = HttpClient.sendGet(url, null, JSONObject.class);
        Long endTime = System.currentTimeMillis();
        logger.info("httpResult: result={},url={},  excuteTime={}", o.toJSONString(), url,  (endTime-startTime));
        if (o.containsKey("openid")) {
            return o.getString("openid");
        }
        logger.error("获取openId失败");
        return null;
    }

    public static void main(String[] args) {

    }
}
