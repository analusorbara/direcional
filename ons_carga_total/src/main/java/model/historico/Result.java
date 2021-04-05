package model.historico;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Result {
    @JsonProperty("HistoricoCarga")
    private List<HistoricoCarga> historicoCarga;

    public List<HistoricoCarga> getHistoricoCarga() {
        return historicoCarga;
    }

    public void setHistoricoCarga(List<HistoricoCarga> historicoCarga) {
        this.historicoCarga = historicoCarga;
    }
}