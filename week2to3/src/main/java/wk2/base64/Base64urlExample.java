package wk2.base64;

import java.util.Base64;

public class Base64urlExample {
    public static void main(String[] args) {
        try {
            // 테스트용 원본 데이터
            String original = "안녕하세요! +/=? 한글 테스트 🚀";

            // 일반 Base64 인코딩
            String base64 = Base64.getEncoder().encodeToString(original.getBytes("UTF-8"));

            // Base64URL 인코딩
            String base64Url = Base64.getUrlEncoder().withoutPadding().encodeToString(original.getBytes("UTF-8"));

            // 디코딩 (검증)
            String decodedBase64 = new String(Base64.getDecoder().decode(base64), "UTF-8");
            String decodedBase64Url = new String(Base64.getUrlDecoder().decode(base64Url), "UTF-8");

            // 출력
            System.out.println("원본 문자열:");
            System.out.println(original);

            System.out.println("\n🔹 일반 Base64 인코딩 결과:");
            System.out.println(base64);

            System.out.println("\n🔸 Base64URL 인코딩 결과:");
            System.out.println(base64Url);

            System.out.println("\n디코딩 결과 검증:");
            System.out.println("Base64 → " + decodedBase64);
            System.out.println("Base64URL → " + decodedBase64Url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
