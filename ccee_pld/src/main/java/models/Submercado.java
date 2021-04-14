package models;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Submercado {
    private String nome;
    private final Set<PldData> pldDatas = new HashSet<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<PldData> getPldDatas() {
        return pldDatas;
    }

    public void setPldData(PldData pldData) {
        this.pldDatas.add(pldData);
    }

    public void mediaMes(int mes) {
        Set<PldData> pldMes = pldDatas.stream().filter(item -> {
            LocalDate localDate = item.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate.getMonthValue() == mes;
        }).collect(Collectors.toSet());


    }
}
