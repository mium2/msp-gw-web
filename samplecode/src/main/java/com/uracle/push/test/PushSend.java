package com.uracle.push.test;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Y.B.H(mium2) on 18. 7. 9..
 */

public class PushSend {
    public static void main(String[] args){
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost postMethod = new HttpPost("http://211.241.199.139:8080/upmc/rcv_register_message.ctl");

        try {
            // 헤더 셋팅
            postMethod.setHeader("Content-Type", "application/x-www-form-urlencoded");

            // 푸시 발송 파라미터 셋팅
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("TYPE", "E"));
            urlParameters.add(new BasicNameValuePair("CUID", "MIUM001"));   // 한명 보낼 경우
            //urlParameters.add(new BasicNameValuePair("CUID", "[\"testuser1\",\"testuser2\",\"testuser3\"]"));  //여러명 보낼 경우
            urlParameters.add(new BasicNameValuePair("MESSAGE", "{\"title\":\"안녕하세요.유라클 공지사항입니다.\",\"body\":\"오늘 새벽에 정기점검 있습니다.\n 감사합니다.\"}"));
        //urlParameters.add(new BasicNameValuePair("MESSAGE", "안녕하세요. 테스트 발송입니다."));
            urlParameters.add(new BasicNameValuePair("PRIORITY", "3"));
            urlParameters.add(new BasicNameValuePair("BADGENO", "0"));
            urlParameters.add(new BasicNameValuePair("RESERVEDATE", ""));  //예약발송일 경우 ex)20180708 153000
            urlParameters.add(new BasicNameValuePair("SERVICECODE", "ALL"));   // 발송 서비스코드 ALL, ALL2, PUBLIC, PRIVATE 중 택일
            urlParameters.add(new BasicNameValuePair("EXT", ""));
            urlParameters.add(new BasicNameValuePair("SENDERCODE", "admin"));
            urlParameters.add(new BasicNameValuePair("APP_ID", "com.mium2.push.democlient"));
            urlParameters.add(new BasicNameValuePair("DB_IN", "Y"));
            urlParameters.add(new BasicNameValuePair("SPLIT_MSG_CNT", "Y"));
            urlParameters.add(new BasicNameValuePair("DELAY_SECOND", "Y"));
            urlParameters.add(new BasicNameValuePair("PUSH_FAIL_SMS_SEND", "N"));

            postMethod.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));

            HttpResponse upmcResponse = client.execute(postMethod);
            if (upmcResponse.getStatusLine().getStatusCode() == 200) {
                // 성공 비즈니스 로직 처리
                BufferedReader rd = new BufferedReader(new InputStreamReader(upmcResponse.getEntity().getContent()));
                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                System.out.println(" 응답 스트링 : " + result.toString());
            }else{
                // 실패 비즈니스 로직 처리
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
