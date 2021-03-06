package models;

import net.minidev.json.annotate.JsonIgnore;

import java.util.*;
import java.util.stream.Collectors;

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
        return Collections.min(
            pldHoras.stream()
                    .sorted(Comparator.comparingInt(PldHora::getHora))
                    .collect(Collectors.toCollection(LinkedHashSet::new)),
            Comparator.comparing(PldHora::getValor)
        );
    }

    @JsonIgnore
    public PldHora maxPld() {
        return Collections.max(
                pldHoras.stream()
                        .sorted(Comparator.comparingInt(PldHora::getHora))
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                Comparator.comparing(PldHora::getValor)
        );
    }

    @JsonIgnore
    public double mediaDia() {
        OptionalDouble mediaOptional = pldHoras
                .stream()
                .mapToDouble(PldHora::getValor)
                .average();
        if (mediaOptional.isPresent()) {
            return mediaOptional.getAsDouble();
        } else {
            return 0.0;
        }
    }

    @JsonIgnore
    public PldHora procuraHora(int hora) {
        Optional<PldHora> pldHora = pldHoras.stream().filter(item -> item.getHora() == hora).findFirst();
        return pldHora.orElse(null);
    }
}
