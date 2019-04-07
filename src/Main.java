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
    private Forks _leftFork;
    private Forks _rightFork;

    private String _name;
    private int _rounds;

    Philosopher(String name, Forks _left, Forks _right, int rounds) {
        this._name = name;
        _leftFork = _left;
        _rightFork = _right;
        _rounds = rounds;
    }

    public void run() { //overrides Threads run function to change how many rounds the philosophers go through
        for (int i = 0; i <= _rounds; i++) {
            eat();
        }
    }

    private void eat() {
        if (!_leftFork.used) {
            if (!_rightFork.used) {
                _leftFork.take();
                _rightFork.take();

                System.out.println(_name + " is eating");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }

                _rightFork.release();
                _leftFork.release();
            }
        }
        think();
    }

    private void think() {
        System.out.println(_name + " is thinking");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }
}

class Forks {

    boolean used;
    String _name;

    Forks(String _name) {
        this._name = _name;
    }

    synchronized void take() {
        System.out.println(_name + " was used");
        this.used = true;
    }

    synchronized void release() {
        System.out.println(_name + " was released");
        this.used = false;
    }
}