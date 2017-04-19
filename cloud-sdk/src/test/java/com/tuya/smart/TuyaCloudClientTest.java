package com.tuya.smart;

import com.alibaba.fastjson.JSONObject;
import com.tuya.smart.config.ClientConfig;
import com.tuya.smart.model.RequestMessage;
import com.tuya.smart.model.ResponseMessage;


public class TuyaCloudClientTest {

    public static void main(String[] args) throws Exception {
        try {
            String userHome = System.getProperty("user.home");
            //PropertiesConfiguration config = new PropertiesConfiguration(userHome + "/conf/zz.properties");

            ClientConfig clientConfig = new ClientConfig();
            clientConfig.setSocketTimeout(2000);
            clientConfig.setConnectionTimeout(2000);

            String endUri = "https://a1.tuyacn.com/api.json";
            String accessId = "98u9ft5jh2hwdkjhikut";
            String accessKey = "qouytrhkgxejhur83jfkq8buqnkd8p9r";
            TuyaCloudClient client = new TuyaCloudClient(accessId, accessKey, endUri, clientConfig);

            RequestMessage request = new RequestMessage();
            request.setApi("tuya.p.time.get");
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

            for (int i = 0; i < 100; i++) {
                ResponseMessage response = client.sendRequest(request);

                if (response.isSuccess()) {
                    String result = JSONObject.toJSONString(response.getResult(), true);
                    System.out.println("第" + i + "次:" + result);
                } else {
                    String errorCode = response.getErrorCode();
                    String errorMsg = response.getErrorMsg();
                    System.out.println("===============第" + i + "次:" + errorMsg);
                }
                Thread.sleep(50000);
            }
        }catch (Exception e){
            System.out.print(e.fillInStackTrace());
            e.printStackTrace();
        }

    }

}
