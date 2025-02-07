package modelo;

import java.util.Objects;

public class VistaHorariosUsuarios implements java.io.Serializable {

    private static final long serialVersionUID = -4385937591523216572L;
    private int alumId;
    private String hora;
    private String dia;
    private String moduloNombre;

    // Constructor sin parámetros
    public VistaHorariosUsuarios() {
    }

    // Constructor con parámetros
    public VistaHorariosUsuarios(int alumId, String hora, String dia, String moduloNombre) {
        this.alumId = alumId;
        this.hora = hora;
        this.dia = dia;
        this.moduloNombre = moduloNombre;
    }

    // Getters y Setters
    public int getAlumId() {
        return alumId;
    }

    public void setAlumId(int alumId) {
        this.alumId = alumId;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getModuloNombre() {
        return moduloNombre;
    }

    public void setModuloNombre(String moduloNombre) {
        this.moduloNombre = moduloNombre;
    }

    @Override
    public String toString() {
        return "VistaHorariosUsuarios [alumId=" + alumId + ", hora=" + hora + ", dia=" + dia + ", moduloNombre=" + moduloNombre + "]";
    }

    // Implementación de equals() y hashCode()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VistaHorariosUsuarios that = (VistaHorariosUsuarios) obj;
        return alumId == that.alumId && Objects.equals(hora, that.hora) && Objects.equals(dia, that.dia) && Objects.equals(moduloNombre, that.moduloNombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alumId, hora, dia, moduloNombre);
    }
}
