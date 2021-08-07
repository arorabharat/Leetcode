package structural.adaapter;

class Client {

    Target target;

    Client(Target target) {
        this.target = target;
    }

    void printRequest() {
        System.out.println(target.request());
    }
}
