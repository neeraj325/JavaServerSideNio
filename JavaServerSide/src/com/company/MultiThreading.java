package com.company;



public class MultiThreading implements Runnable {

    private Thread thread1, thread2;
    public static void main(String[] args)
    {
            MultiThreading threadDeployment = new MultiThreading();
    }
    public MultiThreading(){
        thread1 = new Thread(this);
        thread2 = new Thread(this);

        thread1.start();
        thread2.start();

    }
    public void run(){
        int pause;
        for(int i=0;i<10;i++){
            try{System.out.println(Thread.currentThread().getName() + " being executed");
                pause = (int)(Math.random()*3000);

                Thread.sleep(pause);

            }catch (InterruptedException interruptedException){
                System.out.println(interruptedException);
            }

        }

    }

}
