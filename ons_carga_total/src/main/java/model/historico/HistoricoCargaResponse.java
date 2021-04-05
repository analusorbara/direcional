package model.historico;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricoCargaResponse {
    @JsonProperty("HistoricoCargaOutputmessage")
    private HistoricoCargaOutputmessage historicoCargaOutputmessage;

    public HistoricoCargaOutputmessage getHistoricoCargaOutputmessage() {
        return historicoCargaOutputmessage;
    }

    public void setHistoricoCargaOutputmessage(HistoricoCargaOutputmessage historicoCargaOutputmessage) {
        this.historicoCargaOutputmessage = historicoCargaOutputmessage;
    }
}
