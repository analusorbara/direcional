package models;

import net.minidev.json.annotate.JsonIgnore;

import java.util.*;

@SuppressWarnings("unused")
public class PldData {
    private Date data;
    private final Set<PldHora> pldHoras = new HashSet<>();

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Set<PldHora> getPldHoras() {
        return pldHoras;
    }

    @JsonIgnore
    public void setPldHora(PldHora pldHora) {
        this.pldHoras.add(pldHora);
    }

    @JsonIgnore
    public PldHora minPld() {
        return Collections.min(pldHoras, Comparator.comparing(PldHora::getConsumo));
    }

    @JsonIgnore
    public PldHora maxPld() {
        return Collections.max(pldHoras, Comparator.comparing(PldHora::getConsumo));
    }

    @JsonIgnore
    public Double mediaDia() {
        OptionalDouble mediaOptional = pldHoras
                .stream()
                .mapToDouble(PldHora::getConsumo)
                .average();
        if (mediaOptional.isPresent()) {
            return mediaOptional.getAsDouble();
        } else {
            return 0.0;
        }
    }
}
