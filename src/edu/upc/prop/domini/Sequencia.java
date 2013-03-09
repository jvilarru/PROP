package edu.upc.prop.domini;

public class Sequencia {

    private long id;
    private String sequencia;
    private Pacient pacient;
    private Malaltia malaltia;

    /**
     * Es crea una sequencia amb la cadena especificada i un identificador
     *
     * @param sequencia La cadena que tindra la sequencia
     * @param id El seu identificador
     */
    public Sequencia(String sequencia, long id) {
        this.sequencia = sequencia;
        pacient = null;
        malaltia = null;
        this.id = id;
    }

    /**
     * Retorna la malaltia associada a la sequencia
     *
     * @return La malaltia associada a la sequencia o null si no te cap
     */
    public Malaltia getMalaltia() {
        return malaltia;
    }

    /**
     * Modificar la malaltia associada de la sequencia
     *
     * @param malaltia La nova malaltia que s'associara amb aquesta sequencia
     */
    public void setMalaltia(Malaltia malaltia) {
        this.malaltia = malaltia;
    }

    /**
     * Retorna el pacient associat a la sequencia
     *
     * @return El pacient associat a la sequencia no null si no te cap
     */
    public Pacient getPacient() {
        return pacient;
    }

    /**
     * Modificar el pacient associat a la sequencia
     *
     * @param pacient El nou pacient de la sequencia
     */
    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    /**
     * Retorna la cadena de la sequencia
     *
     * @return La cadena de la sequencia
     */
    public String getSequencia() {
        return sequencia;
    }

    /**
     * Retorna l'identificar de la sequencia
     *
     * @return L'identificador de la sequencia
     */
    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        String identificador = "" + this.id;
        String pac = "";
        if (this.pacient != null) {
            pac = this.pacient.getDni();
        }
        String mal = "";
        if (this.malaltia != null) {
            mal = this.malaltia.getNom();
        }
        String seq = this.sequencia;
        if (seq == null) {
            seq = "";
        }
        return identificador + ":" + seq + ":" + pac + ":" + mal;
    }

    @Override
    public boolean equals(Object o) {
        return id == ((Sequencia) o).id;
    }
}
