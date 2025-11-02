package wk2.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Date;

public class JwsExample {

    public static void main(String[] args) {
        try {
            // 비밀키 설정
            String secret = "super-secret-256";
            String wrongSecret = "wrong-secret-key";


            // 2) RSA 키쌍 (비대칭)
            KeyPairGenerator rsaGen = KeyPairGenerator.getInstance("RSA");
            rsaGen.initialize(2048); // 현재 표준 키 길이
            KeyPair rsaPair = rsaGen.generateKeyPair();
            RSAPrivateKey rsaPriv = (RSAPrivateKey) rsaPair.getPrivate();
            RSAPublicKey rsaPub = (RSAPublicKey) rsaPair.getPublic();

            // 토큰 생성
            Algorithm hs256 = Algorithm.HMAC256(secret);
            Algorithm rs256 = Algorithm.RSA256(rsaPub, rsaPriv);

            String token = JWT.create()
                    .withIssuer("max")                              // iss
                    .withSubject("user-123")                        // sub
                    .withClaim("role", "admin")         // custom claim
                    .withIssuedAt(new Date())                       // iat
                    .withExpiresAt(new Date(System.currentTimeMillis() + 5000)) // 5초 유효
                    .sign(rs256);

            System.out.println("생성된 JWT:");
            System.out.println(token);

            System.out.println("\ndecode JWT ");
            decodeJwt(token);

            // 2초 대기 후 검증
            Thread.sleep(2000);

            // 토큰 검증
            JWTVerifier verifier = JWT.require(rs256)
                    .withIssuer("max")
                    .build();
            DecodedJWT jwt = verifier.verify(token);

            System.out.println("\n검증 성공:");
            System.out.println("Subject: " + jwt.getSubject());
            System.out.println("Role: " + jwt.getClaim("role").asString());
            System.out.println("ExpiresAt: " + jwt.getExpiresAt());

            // 잘못된 키(HMAC)로 검증 시도
            System.out.println("\n🚨 잘못된 서명키(HMAC256)로 검증 시도");
            Algorithm wrongHmac = Algorithm.HMAC256(wrongSecret);
            JWTVerifier wrongVerifier = JWT.require(wrongHmac)
                    .withIssuer("max")
                    .build();
            wrongVerifier.verify(token);

            // 6초 후 만료 테스트
            Thread.sleep(6000);
            verifier.verify(token); // 만료 예외 발생 예정

        } catch (TokenExpiredException e) {
            System.out.println("\n토큰 만료됨: " + e.getMessage());
        } catch (JWTVerificationException e) {
            System.out.println("\n검증 실패: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void decodeJwt(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                System.out.println("유효하지 않은 JWT 형식입니다.");
                return;
            }

            Base64.Decoder decoder = Base64.getUrlDecoder();

            String headerJson = new String(decoder.decode(parts[0]));
            String payloadJson = new String(decoder.decode(parts[1]));

            System.out.println("====== Header =====");
            System.out.println(headerJson);
            System.out.println("\n===== Payload =====");
            System.out.println(payloadJson);
        } catch (IllegalArgumentException e) {
            System.out.println("Base64 디코딩 중 오류 발생: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("예상치 못한 오류: " + e.getMessage());
        }
    }
}
