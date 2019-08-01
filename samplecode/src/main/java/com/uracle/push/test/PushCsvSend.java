package com.uracle.push.test; /**
 * Created by Y.B.H(mium2) on 18. 7. 9..
 */
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class PushCsvSend {
    public static void main(String[] args){
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost method = new HttpPost("http://211.241.199.139:8080/upmc/rcv_register_csvmessage.ctl");

        try {
            // 푸시 발송 파라미터 셋팅
            MultipartEntityBuilder reqEntityBuilder = MultipartEntityBuilder.create();
            Charset chars = Charset.forName("UTF-8");
            reqEntityBuilder.setCharset(chars);
            // CSV로 보낼 푸시발송 유저 첨부파일 full 경로.
            FileBody csvFile = new FileBody(new File("/Users/mium2/project/push/util/samplecode/src/main/resources/csv/csvTemplet.csv"));
            reqEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntityBuilder.addPart("upload_file", csvFile);
            reqEntityBuilder.addTextBody("TYPE", "C");
            reqEntityBuilder.addTextBody("MESSAGE", "{\"title\":\222안녕하세요.유라클 공지사항입니다. 일시 : %VAR1%\",\"body\":\"222오늘 새벽에 정기점검 있습니다.\n감사합니다.\"}", ContentType.create("text/plain", MIME.UTF8_CHARSET));
            //reqEntityBuilder.addTextBody("MESSAGE", "%VAR1%일 입니다.%VAR2% 입니다. 금액: %VAR3%", ContentType.create("text/plain", MIME.UTF8_CHARSET));
			//reqEntityBuilder.addTextBody("MESSAGE", "{\"title\":\"%CNAME%님 공지입니다.\","\body\":\"%VAR1%일 입니다.%VAR2% 입니다. 금액: %VAR3%\"}", ContentType.create("text/plain", MIME.UTF8_CHARSET));
            reqEntityBuilder.addTextBody("PRIORITY", "3");
            reqEntityBuilder.addTextBody("BADGENO", "0");
            reqEntityBuilder.addTextBody("RESERVEDATE", "20181220 151200");  //예약발송일 경우 ex)20180708 153000
            reqEntityBuilder.addTextBody("SERVICECODE", "ALL");
            reqEntityBuilder.addTextBody("EXT", "{\"TYPE\":\"ALT\"}");
            reqEntityBuilder.addTextBody("SENDERCODE", "admin");
            reqEntityBuilder.addTextBody("APP_ID", "com.uracle.push.test");
            reqEntityBuilder.addTextBody("DB_IN", "Y");
            reqEntityBuilder.addTextBody("SPLIT_MSG_CNT", "0");
            reqEntityBuilder.addTextBody("DELAY_SECOND", "0");
            reqEntityBuilder.addTextBody("PUSH_FAIL_SMS_SEND", "N");

            HttpEntity multiPartEntity = reqEntityBuilder.build();
            method.setEntity(multiPartEntity);

            HttpResponse upmcResponse = client.execute(method);
            if (upmcResponse.getStatusLine().getStatusCode() == 200) {
                // 성공 비즈니스 로직 처리
                BufferedReader rd = new BufferedReader(new InputStreamReader(upmcResponse.getEntity().getContent()));
                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                System.out.println(" 응답 스트링 : "+result.toString());
            }else{
                // 실패 비즈니스 로직 처리
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

