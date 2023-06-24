package model;

public class NavioWrapper {
    private String key;
    private Navio navio;

    public NavioWrapper(String key, Navio navio) {
        this.key = key;
        this.navio = navio;
    }

    public Navio getNavio() {
        return navio;
    }

    public String getKey() {
        return key;
    }
}

