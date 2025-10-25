package wk2.file_io.feedback;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadFileExample3 {

    // 공유 큐: 여러 스레드가 로그를 비동기로 넣음
    private static final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();
    private static volatile boolean running = true; // 종료 제어용 플래그

    public static void main(String[] args) {
        String fileName = "thread_output3.txt";

        // Writer 전용 스레드 (try-with-resources 사용)
        Thread writerThread = new Thread(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                while (running || !logQueue.isEmpty()) {
                    String log = logQueue.poll(100, TimeUnit.MILLISECONDS); // 0.1초 대기
                    if (log != null) {
                        writer.write(log + "\n");
                    }
                    if (logQueue.isEmpty()) {
                        writer.flush();
                    }
                }
                System.out.println("WriterThread 정상 종료 및 자원 해제 완료");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }, "Writer-Thread");
        writerThread.start();

        // 로그를 생성하는 다중 스레드 (Producer 역할)
        Runnable writerTask = () -> {
            long start = System.currentTimeMillis();
            String threadName = Thread.currentThread().getName();

            for (int i = 1; i <= 1000000; i++) {
                String log = "문자열 - " + threadName + " (" + i + ")";
                logQueue.offer(log); // 비동기 큐에 저장
            }

            long end = System.currentTimeMillis();
            System.out.println(threadName + " 완료 - 소요 시간: " + (end - start) + "ms");
        };
        long totalStart = System.currentTimeMillis();
        Thread t1 = new Thread(writerTask, "스레드 1번");
        Thread t2 = new Thread(writerTask, "스레드 2번");
        Thread t3 = new Thread(writerTask, "스레드 3번");

        t1.start();
        t2.start();
        t3.start();

        try {
            // Producer 스레드 종료 대기
            t1.join();
            t2.join();
            t3.join();

            // 모든 로그 입력이 끝나면 종료 신호
            running = false;
            writerThread.join();
            System.out.println("=== 모든 로그 기록 완료 ===");
            long totalEnd = System.currentTimeMillis(); // ✅ 프로그램 전체 종료
            System.out.println("=== 전체 처리 소요 시간: " + (totalEnd - totalStart) + "ms ===");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
