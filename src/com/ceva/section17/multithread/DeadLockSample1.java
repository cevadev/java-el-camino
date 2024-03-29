package com.ceva.section17.multithread;

public class DeadLockSample1 {
    private Object lockA = new Object();
    private Object lockB = new Object();

    private void test(){
        Thread t1 = new Thread(() ->{
            // tomamos el control de lockA
            synchronized (lockA){
                System.out.println("Thread 1, lock A");
                try{
                    Thread.sleep(500);
                }catch (InterruptedException e){}
                // tomamos el control de lockB
                synchronized (lockB){
                    System.out.println("Thread 1, Lock A, B");
                }
            }
            System.out.println("Thread 1, Finalizado");
        });
        t1.start();
        Thread t2 = new Thread(()->{
            synchronized (lockB){
                System.out.println("Thread 2, lock B");
                try{
                    Thread.sleep(500);
                }
                catch(InterruptedException e){}
                synchronized (lockA){
                    System.out.println("Thread 2, Lock B, A");
                }
            }
            System.out.println("Thread 2, finalizado");
        });
        t2.start();
    }

    public static void main(String[] args) {
        new DeadLockSample1().test();
    }
}
