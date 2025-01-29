package modelo;

import java.util.Date;

public class MatriculacionesId implements java.io.Serializable {


    private static final long serialVersionUID = 4784579289132702178L;
    private int alumId;
    private int cicloId;
    private int curso;
    private Date fecha;

    public MatriculacionesId() {
    }

    public MatriculacionesId(int alumId, int cicloId, int curso, Date fecha) {
        this.alumId = alumId;
        this.cicloId = cicloId;
        this.curso = curso;
        this.fecha = fecha;
    }

    public int getAlumId() {
        return this.alumId;
    }

    public void setAlumId(int alumId) {
        this.alumId = alumId;
    }

    public int getCicloId() {
        return this.cicloId;
    }

    public void setCicloId(int cicloId) {
        this.cicloId = cicloId;
    }

    public int getCurso() {
        return this.curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
