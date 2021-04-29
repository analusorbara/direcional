package models;

import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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

    @JsonIgnore
    public PldHora minDia(LocalDateTime dataReferencia) {
        return procuraDia(dataReferencia).map(PldData::minPld).orElse(null);
    }

    @JsonIgnore
    public PldHora maxDia(LocalDateTime dataReferencia) {
        return procuraDia(dataReferencia).map(PldData::maxPld).orElse(null);
    }

    @JsonIgnore
    public Double mediaDia(LocalDateTime dataReferencia) {
        return procuraDia(dataReferencia).map(PldData::mediaDia).orElse(null);
    }

    @JsonIgnore
    public double mediaSemana(LocalDateTime dataInicioSemana, LocalDateTime diaDeReferenciaPld) {
        return mediaEntreDatas(dataInicioSemana, diaDeReferenciaPld);
    }

    @JsonIgnore
    public PldHora minSemana(LocalDateTime dataInicioSemana, LocalDateTime diaDeReferenciaPld) {
        return minEntreDatas(dataInicioSemana, diaDeReferenciaPld);

    }

    @JsonIgnore
    public PldHora maxSemana(LocalDateTime dataInicioSemana, LocalDateTime diaDeReferenciaPld) {
        return maxEntreDatas(dataInicioSemana, diaDeReferenciaPld);

    }

    @JsonIgnore
    public double mediaMes(LocalDateTime inicioMes, LocalDateTime diaDeReferenciaPld) {
        return mediaEntreDatas(inicioMes, diaDeReferenciaPld);
    }

    @JsonIgnore
    public Optional<PldData> procuraDia(LocalDateTime dataReferencia) {
        return pldDatas.stream().filter(item -> {
            LocalDate localDate = item.getData()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return dataReferencia.getDayOfMonth() == localDate.getDayOfMonth()
                    && dataReferencia.getMonthValue() == localDate.getMonthValue()
                    && dataReferencia.getYear() == localDate.getYear();
        }).findFirst();
    }

    @JsonIgnore
    public PldData procuraData(LocalDateTime dataReferencia) {
        Optional<PldData> pldData = procuraDia(dataReferencia);
        return pldData.orElse(null);
    }

    @JsonIgnore
    private double mediaEntreDatas(LocalDateTime inicio, LocalDateTime fim) {
        Date dateInicio = Date.from(inicio.minusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        Date dateFim = Date.from(fim.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());

        OptionalDouble mediaOptional = pldDatas.stream()
                .filter(item -> item.getData().after(dateInicio) && item.getData().before(dateFim))
                .mapToDouble(PldData::mediaDia)
                .average();
        if (mediaOptional.isPresent()) {
            return mediaOptional.getAsDouble();
        } else {
            return 0.0;
        }
    }

    @JsonIgnore
    private PldHora minEntreDatas(LocalDateTime inicio, LocalDateTime fim) {
        Date dateInicio = Date.from(inicio.minusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        Date dateFim = Date.from(fim.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());

        Optional<PldHora> mediaOptional = pldDatas.stream()
                .filter(item -> item.getData().after(dateInicio) && item.getData().before(dateFim))
                .map(PldData::minPld)
                .min(Comparator.comparing(PldHora::getValor));
        return mediaOptional.orElseGet(null);
    }

    @JsonIgnore
    private PldHora maxEntreDatas(LocalDateTime inicio, LocalDateTime fim) {
        Date dateInicio = Date.from(inicio.minusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        Date dateFim = Date.from(fim.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());

        Optional<PldHora> mediaOptional = pldDatas.stream()
                .filter(item -> item.getData().after(dateInicio) && item.getData().before(dateFim))
                .map(PldData::maxPld)
                .max(Comparator.comparing(PldHora::getValor));
        return mediaOptional.orElseGet(null);
    }
}

