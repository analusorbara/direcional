package model.historico;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class HistoricoCarga{
    @JsonProperty("Instante")
    private Date instante;
    @JsonProperty("CargaEnergiaGWh")
    private Double cargaEnergiaGWh;
    @JsonProperty("CargaEnergiaMWMed")
    private Double cargaEnergiaMWMed;
    @JsonProperty("IdSubSistema")
    private String idSubSistema;

    public Date getInstante() {
        return instante;
    }

    public void setInstante(Date instante) {
        this.instante = instante;
    }

    public Double getCargaEnergiaGWh() {
        return cargaEnergiaGWh;
    }

    public void setCargaEnergiaGWh(Double cargaEnergiaGWh) {
        this.cargaEnergiaGWh = cargaEnergiaGWh;
    }

    public Double getCargaEnergiaMWMed() {
        return cargaEnergiaMWMed;
    }

    public void setCargaEnergiaMWMed(Double cargaEnergiaMWMed) {
        this.cargaEnergiaMWMed = cargaEnergiaMWMed;
    }

    public String getIdSubSistema() {
        return idSubSistema;
    }

    public void setIdSubSistema(String idSubSistema) {
        this.idSubSistema = idSubSistema;
    }
}