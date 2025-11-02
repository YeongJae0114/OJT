package wk2.file_io;

import java.io.FileWriter;
import java.io.IOException;

public class ThreadFileExample5 {
    private static final Object lock = new Object();
    private static boolean isThread1Turn = true; // 현재 차례 표시

    public static void main(String[] args) {
        String fileName = "thread_output_ordered.txt";

        Thread thread1 = new Thread(() -> writeToFile(fileName, "스레드 1번", true));
        Thread thread2 = new Thread(() -> writeToFile(fileName, "스레드 2번", false));

        thread1.start();
        thread2.start();
    }

    private static void writeToFile(String fileName, String threadName, boolean myTurnIsThread1) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            for (int i = 1; i <= 10; i++) {
                synchronized (lock) {
                    // 내 차례가 아닐 때는 대기
                    while (isThread1Turn != myTurnIsThread1) {
                        lock.wait();
                    }

                    // 실제 파일 쓰기
                    writer.write("문자열 - " + threadName + " (" + i + ")\n");
                    writer.flush();

                    // 다음 스레드 차례로 전환
                    isThread1Turn = !isThread1Turn;
                    lock.notifyAll();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
