package wk2.file_io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ThreadFileExample2 {
    public static void main(String[] args) {
        String fileName = "thread_output2.txt";

        Runnable writerTask = () -> {
            long start = System.currentTimeMillis();
            String threadName = Thread.currentThread().getName();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                for (int i = 1; i <= 1000; i++) {
                    bw.write("문자열 - " + threadName + " (" + i + ")\n");
                    Thread.sleep(10); // 쓰기 간격 약간 두기 (경쟁 상태 보기)
                    //bw.flush(); // flush 하지 않으면??
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            System.out.println("소요 시간: " + (end - start) + "ms");
        };

        Thread t1 = new Thread(writerTask, "스레드 1번");
        Thread t2 = new Thread(writerTask, "스레드 2번");

        t1.start();
        t2.start();
    }

}
