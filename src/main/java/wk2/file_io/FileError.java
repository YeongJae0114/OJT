package wk2.file_io;

import java.io.FileWriter;
import java.io.IOException;

public class FileError {
    public static void main(String[] args) {
        // 1. 권한 오류
        System.out.println("권한 오류 실험");
        permissionDeniedTest();

        System.out.println("==============================");

    }

    // 만약 경로에 쓰기 권한이 없다면 커널이 Permission Denied를 반환한다.
    public static void permissionDeniedTest() {
        System.out.println("\n[1️⃣ 권한 오류 실험 시작]");
        try {
            // /sys/ 는 일반 사용자에게 쓰기 금지된 시스템 디렉터리
            FileWriter writer = new FileWriter("/sys/kernel/test.txt");
            writer.write("이건 쓰기 불가 경로입니다.");
            writer.close();
        } catch (IOException e) {
            System.out.println("💥 발생한 오류: " + e.getMessage());
        }
    }


}
