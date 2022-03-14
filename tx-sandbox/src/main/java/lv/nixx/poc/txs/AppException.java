package lv.nixx.poc.txs;

import lv.nixx.poc.txs.model.Statistic;

public class AppException extends RuntimeException {

    private final Statistic stat;

    public AppException(Statistic stat, String message) {
        super(message);
        this.stat = stat;
    }

    public Statistic getStat() {
        return stat;
    }
}
