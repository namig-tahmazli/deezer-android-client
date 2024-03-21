package com.namig.tahmazli.deezerandroidclient;

import org.junit.Test;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class ThreadingTest {

    @Test
    public void test_TransferQueue() throws InterruptedException {

        final TransferQueue<Integer> queue = new LinkedTransferQueue<>();

        final var thread1 = new Thread(() -> {
            try {
                System.out.println("Receiving 1");
                int value = queue.take();
                System.out.println("Received " + value);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        final var thread2 = new Thread(() -> {
            try {
                System.out.println("Sending 1");
                queue.transfer(1);
                queue.remove(1);
                System.out.println("Sent 1");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
