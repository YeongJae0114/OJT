package wk2.base64;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Example {
    public static void main(String[] args) throws Exception {
        String text = "가"; // 비교할 문자열

        // OS 기본 인코딩 사용 (플랫폼마다 다름)
        String base64Default = Base64.getEncoder()
                .encodeToString(text.getBytes());

        // UTF-8 명시
        String base64Utf8 = Base64.getEncoder()
                .encodeToString(text.getBytes(StandardCharsets.UTF_8));

        // MS949 (Windows 한글 기본 인코딩)
        Charset ms949 = Charset.forName("MS949");
        String base64Ms949 = Base64.getEncoder()
                .encodeToString(text.getBytes(ms949));

        // 출력
        System.out.println("입력 문자열: " + text);
        System.out.println("---------------------------------");
        System.out.println("기본 인코딩 (" + Charset.defaultCharset() + "): " + base64Default);
        System.out.println("UTF-8: " + base64Utf8);
        System.out.println("MS949: " + base64Ms949);
    }
}
