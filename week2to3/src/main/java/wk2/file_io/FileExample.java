package wk2.file_io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = "output.txt"; // 저장할 파일 이름

        System.out.println("문자열을 입력하세요. (종료하려면 'exit' 입력)");

        try (FileWriter writer = new FileWriter(fileName, true)) { // append 모드
            while (true) {
                System.out.print("입력 > ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("프로그램을 종료합니다.");
                    break;
                }

                writer.write(input + System.lineSeparator());
                writer.flush(); // 즉시 파일에 반영
                System.out.println("→ 파일에 저장되었습니다.");
            }
        } catch (IOException e) {
            System.out.println("파일 처리 중 오류 발생: " + e.getMessage());
        }
    }
}
