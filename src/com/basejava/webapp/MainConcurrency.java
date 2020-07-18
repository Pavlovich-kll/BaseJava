package com.basejava.webapp;

public class MainConcurrency {
    private static int counter;
    private static final Object LOCK = new Object();//этот объект олицетворяет общую очередь, перед которым встают все потоки после выполнения;


    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());//имя потока, где запускатеся главный thread, т.е. main
        Thread thread0 = new Thread() { //с помощью него запускаем параллельный процесс
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());//имя потока, который мы создали через new и  его состояние
            }
        };
        thread0.start();//запуск созданного thread

        /**
         * Первый случай- создаем анонимный класс и переопределяем метод run;
         * Второй случай- параметризируем метод Thread и в конструктор передает Runnable (Этот случай предпочтительнее);
         */

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }
        }).start();

        System.out.println(thread0.getState());
        deadlock();

/**
 * Задача со счётчиком (counter). Разделить к нему доступ.
 */
        final MainConcurrency mainConcurrency = new MainConcurrency();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> { //создаем поток
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();//при нестатическом методе. все  эти 10000 thread встают в очередь к объекту mainConcurrency;
                }
            }).start();
        }
        Thread.sleep(500);//(1 вар): ждем, пока потоки закончат свое исполнение и потом выводим counter;
        System.out.println(counter);
        LazySingleton.getInstance();//instance синглетона;

    }

    private static void inc() { //метод инкрементирующий счётчик. synchronized- говорит о том, что в метод имеет право заходить только единственный поток;
//        synchronized (MainConcurrency.class) { //аналогично тому, если мы напишем synchronized в методе перед void, чтобы засинхронить метод целиком;
        double a = Math.sin(13);
        synchronized (LOCK) {//все потоки, что будут доходить, становятся "в очередь" перед принятым объектом. Для локальной синхронизации;
            counter++;
        }
//    }
    }

    public static void deadlock() {
        Object x = new Object();
        Object y = new Object();

        new Thread(()-> {
            synchronized (x) {
                System.out.println(Thread.currentThread().getName() + ": got x monitor");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": trying to get y monitor");
                synchronized (y) {
                    System.out.println(Thread.currentThread().getName() + ": got y monitor");
                }
            }
        }).start();

        new Thread(()-> {
            synchronized (y) {
                System.out.println(Thread.currentThread().getName() + ": got y monitor");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": trying to get x monitor");
                synchronized (x) {
                    System.out.println(Thread.currentThread().getName() + ": got y monitor");
                }
            }
        }).start();
    }


}