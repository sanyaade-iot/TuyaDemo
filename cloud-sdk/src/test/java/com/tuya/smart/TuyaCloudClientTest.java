package com.tuya.smart;

import com.tuya.smart.config.ClientConfig;
import org.apache.commons.configuration.PropertiesConfiguration;
import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import java.util.HashMap;
import com.tuya.smart.model.ResponseMessage;
import com.tuya.smart.model.RequestMessage;


public class TuyaCloudClientTest {

    public static void main(String[] args) throws Exception {

        String userHome = System.getProperty("user.home");
        PropertiesConfiguration config = new PropertiesConfiguration(userHome + "/conf/zz.properties");

        ClientConfig clientConfig=new ClientConfig();
        clientConfig.setSocketTimeout(2000);
        clientConfig.setConnectionTimeout(2000);
        
        String endUri = "https://a1.tuyacn.com/api.json";
        String accessId = config.getString("accessId");
        String accessKey = config.getString("accessKey");
        TuyaCloudClient client = new TuyaCloudClient(accessId,accessKey, endUri, clientConfig);

        RequestMessage request = new RequestMessage();
        request.setApi("s.m.dev.list.group.list");
        request.setApiVersion("1.0");
        request.setOs("centOS-6");
        request.setDeviceid("ppstrong");
        request.setLang("zh");

        //request.setSession("ay146613Q5026676FEK2voO393e687fbc014703a25c30a16b3741353");

        //Map<String,String> params = new HashMap<String,String>();
        //params.put("uid","mmmmmmmmmm");
        //params.put("countryCode","86");
        //params.put("passwd","faint");
        //request.setParams(params);


        ResponseMessage response=client.sendRequest(request);

        if (response.isSuccess()) {
            String result = JSONObject.toJSONString( response.getResult(),true);
            System.out.println(result);
        } else {
            String errorCode = response.getErrorCode();
            String errorMsg = response.getErrorMsg();
            System.out.println(errorMsg);
        }




    }

}
