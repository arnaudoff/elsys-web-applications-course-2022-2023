package org.example;

public class PoliticianClient {
    private Politician politician;

    public PoliticianClient(Politician politician) {
        this.politician = politician;
    }

    public Politician getPolitician() {
        return politician;
    }

    public void setPolitician(Politician politician) {
        this.politician = politician;
    }

    void speak() {
        politician.talk();
    }
}
