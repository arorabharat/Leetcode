package structural.adaapter;

class Adapter implements Adaptee, Target {

    @Override
    public int specificRequest() {
        return 0;
    }

    @Override
    public int request() {
        return 0;
    }
}
