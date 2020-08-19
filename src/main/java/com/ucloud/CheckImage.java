package com.ucloud;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.SortedMap;
import java.util.TreeMap;

public class CheckImage {
    /**
     * @param imageUrl
     * @return pass-放行， forbid-封禁， check-人工审核
     * @throws Exception
     */
    public String check(String imageUrl) {
        String ucloudUrl = "http://api.uai.ucloud.cn/v1/image/scan";
        String appId = "uaicensor-rjmvogpx";
        String uaicensorPublicKey = null;
        String uaicensorPrivateKey = null;

        //图片绝对路径
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        /**
         * 生成signature，首字母排序
         */
        String timestamp = System.currentTimeMillis() + "";
        SortedMap<Object, Object> packageParams = new TreeMap<>();
        packageParams.put("PublicKey", uaicensorPublicKey);
        packageParams.put("ResourceId", appId);
        packageParams.put("Timestamp", timestamp);
        packageParams.put("Url", imageUrl);
        String signature = null;
        try {
           // signature = UCloudUtil.createSign(packageParams, uaicensorPrivateKey);
        } catch (Exception e) {
            return null;
        }
        /**
         * 参数
         */
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("Scenes", "porn");
        param.add("Method", "url");
        param.add("Url", imageUrl);
        /**
         * headers 参数
         */
        headers.setContentType(MediaType.parseMediaType("multipart/form-data; charset=UTF-8"));
        headers.set("PublicKey", uaicensorPublicKey);
        headers.set("Signature", signature);
        headers.set("ResourceId", appId);
        headers.set("Timestamp", timestamp);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = rest.exchange(ucloudUrl, HttpMethod.POST, httpEntity, String.class);
        String body = responseEntity.getBody();
       // JSONObject jsonObject = JSON.parseObject(body);
       // if (jsonObject.getInteger("RetCode") == 0) {
       //     String res = jsonObject.getJSONObject("Result").getJSONObject("Porn").getString("Suggestion");
       //     return res;
       // }
        return null;
    }
}
