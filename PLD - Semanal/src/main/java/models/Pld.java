package models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Pld {
    private final Set<Submercado> submercados = new HashSet<>();

    public Set<Submercado> getSubmercados() {
        return submercados;
    }

    public void setSubmercado(Submercado submercado) {
        this.submercados.add(submercado);
    }

    public Submercado getSubmercado(SubmercadoEnum submercadoEnum) {
        Optional<Submercado> submercado = submercados.stream()
                .filter(item -> item.getNome().equals(submercadoEnum.getSubmercado()))
                .findFirst();

        return submercado.orElse(null);
    }

    public boolean temPldNovo(LocalDateTime diaDeReferenciaPld) {
        Optional<PldData> pldData = getSubmercado(SubmercadoEnum.SUDESTE).procuraDia(diaDeReferenciaPld);
        return pldData.isPresent();
    }
}
