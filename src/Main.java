/*
Kevin Baltazar Reyes
Dining Philosophers Problem
CSC 415
 */


public class Main {
    public static void main(String[] args) {

        int PhilosopherForkAmount = 5;
        int rounds = 10;

        System.out.println("Number of rounds: " + rounds);

        Forks[] forks = new Forks[PhilosopherForkAmount];

        //initialize the forks
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Forks("Fork #" + i);
        }
        Philosopher[] philosophers = new Philosopher[PhilosopherForkAmount];
        for(int i = 0; i < philosophers.length - 1; i++){
            philosophers[i] = new Philosopher("Philosopher #" + i , forks[i], forks[i + 1], rounds);
            System.out.println(i);
        }
        philosophers[philosophers.length - 1] = new Philosopher("Philosopher #" + (philosophers.length - 1), forks[0], forks[philosophers.length - 1], rounds);
        System.out.println(philosophers.length - 1);

//        philosophers[0] = new Philosopher("Philosopher #0", forks[0], forks[1], rounds);
//        philosophers[1] = new Philosopher("Philosopher #1", forks[1], forks[2], rounds);
//        philosophers[2] = new Philosopher("Philosopher #2", forks[2], forks[3], rounds);
//        philosophers[3] = new Philosopher("Philosopher #3", forks[3], forks[4], rounds);
//        philosophers[4] = new Philosopher("Philosopher #4", forks[0], forks[4], rounds);

        for (int i = 0; i < philosophers.length; i++) {
            System.out.println("Thread " + i + " has started");
            Thread t = new Thread(philosophers[i]);
            t.start();
        }
    }
}

// State : 2 = Eat, 1 = think
class Philosopher extends Thread {
    private Forks _leftFork;
    private Forks _rightFork;

    private String _name;
    private int _state;
    private int _rounds;

    public Philosopher(String name, Forks _left, Forks _right, int rounds) {
        this._state = 1;
        this._name = name;
        _leftFork = _left;
        _rightFork = _right;
        _rounds = rounds;
    }

    public void eat() {
        if (!_leftFork.used) {
            if (!_rightFork.used) {
                _leftFork.take();
                _rightFork.take();

                System.out.println(_name + " is eating");

                Log.Delay(1000);

                _rightFork.release();
                _leftFork.release();
            }
        }
        think();
    }

    public void think() {
        this._state = 1;
        System.out.println(_name + " is thinking");
        Log.Delay(1000);

    }

    public void run() {
        for (int i = 0; i <= _rounds; i++) {
            eat();
        }
    }
}

class Log {

    public static void Delay(int ms) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }
}

class Forks {

    public boolean used;
    public String _name;

    public Forks(String _name) {
        this._name = _name;
    }

    public synchronized void take() {
        System.out.println(_name + " was used");
        this.used = true;
    }

    public synchronized void release() {
        System.out.println(_name + " was released");
        this.used = false;
    }
}