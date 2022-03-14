package lv.nixx.poc.txs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Collection;
import java.util.Map;

@Data
public class Statistic {

    private Collection<TableInfo> beforeOperation;
    private Collection<TableInfo> afterOperation;

    private Map<String, Object> data;
    private String error;
    private String status = "OK";

    @JsonProperty("isDataChanged")
    public String isDataChanged() {
        final long bc = beforeOperation.stream().map(TableInfo::getRowCount).reduce(0L, Long::sum);
        final long ac = afterOperation.stream().map(TableInfo::getRowCount).reduce(0L, Long::sum);
        return bc != ac ? "CHANGED" : "NOT_CHANGED";
    }

    @AllArgsConstructor
    @Getter
    public static class TableInfo {
        String name;
        long rowCount;
    }

}
