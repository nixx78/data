package lv.nixx.poc.txs.data;

import lv.nixx.poc.txs.data.model.Statistic;

public class AppException extends RuntimeException {

    private Statistic stat;

    public AppException(Statistic stat, String message) {
        super(message);
        this.stat = stat;
    }

    public Statistic getStat() {
        return stat;
    }
}
