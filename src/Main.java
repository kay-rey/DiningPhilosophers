/*
Kevin Baltazar Reyes
Dining Philosophers Problem
CSC 415
 */


public class Main {
    public static void main(String[] args) {

        int PhilosopherForkAmount = 5;
        int rounds = 1;

        System.out.println("Number of rounds: " + rounds);
        System.out.println("Number of philosophers: " + PhilosopherForkAmount);

        Forks[] forks = new Forks[PhilosopherForkAmount];

        //initialize the forks
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Forks("Fork #" + i);
        }
        Philosopher[] philosophers = new Philosopher[PhilosopherForkAmount];
        for (int i = 0; i < philosophers.length - 1; i++) {
            philosophers[i] = new Philosopher("Philosopher #" + i, forks[i], forks[i + 1], rounds);
        }
        philosophers[philosophers.length - 1] = new Philosopher("Philosopher #" + (philosophers.length - 1), forks[0], forks[philosophers.length - 1], rounds); //manually create the last philospher that shares the first and last fork

        for (int i = 0; i < philosophers.length; i++) {
            System.out.println("Thread " + i + " has started");
            Thread t = new Thread(philosophers[i]);
            t.start();  //starts thread which executes the run function
        }
    }
}

class Philosopher extends Thread {
    private Forks leftFork;
    private Forks rightFork;

    private String name;
    private int rounds;

    Philosopher(String name, Forks _left, Forks _right, int rounds) {
        this.name = name;
        this.leftFork = _left;
        this.rightFork = _right;
        this.rounds = rounds;
    }

    public void run() { //overrides Threads run function to change how many rounds the philosophers go through
        for (int i = 0; i <= rounds; i++) {
            eat();
        }
    }

    private void eat() {
        if (!leftFork.used) {   //first checks if the left fork is not being used
            if (!rightFork.used) {  //secondly checks if right fork is not being used
                leftFork.take();    //if both left and right are not being used then take both forks for the philospher to start eating
                rightFork.take();

                System.out.println(name + " is eating");

                try {
                    Thread.sleep(1000); //simulated the philosphper taking time to eat
                } catch (InterruptedException ex) {
                }

                rightFork.release();    //releases both the forks when done eating
                leftFork.release();
            }
        }
        think();
    }

    private void think() {
        System.out.println(name + " is thinking");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }
}

class Forks {

    boolean used;
    private String name;

    Forks(String name) {
        this.name = name;
    }

    synchronized void take() {
        System.out.println(name + " was used");
        this.used = true;
    }

    synchronized void release() {
        System.out.println(name + " was released");
        this.used = false;
    }
}