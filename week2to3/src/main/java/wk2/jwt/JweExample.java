package wk2.jwt;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;
import com.nimbusds.jose.jwk.gen.*;
import com.nimbusds.jose.jwk.*;

import java.util.Base64;

public class JweExample {
    public static void main(String[] args) {
        try {
            // RSA 키쌍 생성
            RSAKey rsaKey = new RSAKeyGenerator(2048) // 2048, 4096
                    .keyID("demo-key")
                    .generate();

            // 평문 JWT Claims 생성
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject("user-123")
                    .issuer("max")
                    .claim("role", "admin")
                    .build();

            // JWE Header 정의 (암호화 알고리즘 선택)
            JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
                    .contentType("JWT") // nested JWT 구조 명시
                    .build();

            // 암호화 전 JWT 생성
            EncryptedJWT jwe = new EncryptedJWT(header, claims);

            // 공개키로 암호화
            jwe.encrypt(new RSAEncrypter(rsaKey.toRSAPublicKey()));
            String jweToken = jwe.serialize();

            System.out.println("암호화된 JWE 토큰:");
            System.out.println(jweToken);
            // Base64 파트 분리
            String[] parts = jweToken.split("\\.");

            System.out.println("\n================ Base64 Decode 결과 ================");
            Base64.Decoder decoder = Base64.getUrlDecoder();

            System.out.println("Header (JSON):");
            System.out.println(new String(decoder.decode(parts[0])));

            System.out.println("\nEncryptedKey (암호화된 AES 키):");
            System.out.println(new String(decoder.decode(parts[1])));

            System.out.println("\nIV (초기화 벡터):");
            System.out.println(new String(decoder.decode(parts[2])));

            System.out.println("\nCipherText (암호화된 Payload):");
            System.out.println(new String(decoder.decode(parts[3])));

            System.out.println("\nAuthTag (무결성 태그):");
            System.out.println(new String(decoder.decode(parts[4])));


            // 개인키로 복호화
            System.out.println("\n================ 복호화 결과 ================");

            EncryptedJWT decryptedJWT = EncryptedJWT.parse(jweToken);
            decryptedJWT.decrypt(new RSADecrypter(rsaKey.toPrivateKey()));

            System.out.println("\n복호화된 Claims:");
            System.out.println(decryptedJWT.getJWTClaimsSet().toJSONObject());


            System.out.println("\n[잘못된 키로 복호화 시도]");
            RSAKey wrongKey = new RSAKeyGenerator(2048)
                    .keyID("wrong-key")
                    .generate();
            EncryptedJWT wrongDecrypted = EncryptedJWT.parse(jweToken);
            wrongDecrypted.decrypt(new RSADecrypter(wrongKey.toPrivateKey())); // 다른 개인키

        }catch (JOSEException e){
            System.err.println("복호화 실패 (JOSEException): " + e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
