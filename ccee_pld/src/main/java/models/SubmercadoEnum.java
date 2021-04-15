package models;

public enum SubmercadoEnum {
    SUDESTE("SUDESTE"),
    SUL("SUL"),
    NORDESTE("NORDESTE"),
    NORTE("NORTE");

    private final String submercado;

    SubmercadoEnum(String submercado) {
        this.submercado = submercado;
    }

    public String getSubmercado() {
        return submercado;
    }
}
