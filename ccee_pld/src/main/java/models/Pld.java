package models;

import java.util.HashSet;
import java.util.Set;

public class Pld {
    private final Set<Submercado> submercados = new HashSet<>();

    public Set<Submercado> getSubmercados() {
        return submercados;
    }

    public void setSubmercado(Submercado submercado) {
        this.submercados.add(submercado);
    }
}
