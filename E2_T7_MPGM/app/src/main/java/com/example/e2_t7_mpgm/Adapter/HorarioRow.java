package com.example.e2_t7_mpgm.Adapter;

public class HorarioRow {

        private String lunes;
        private String martes;
        private String miercoles;
        private String jueves;
        private String viernes;

        // Constructor
        public HorarioRow(String lunes, String martes, String miercoles, String jueves, String viernes) {
            this.lunes = lunes;
            this.martes = martes;
            this.miercoles = miercoles;
            this.jueves = jueves;
            this.viernes = viernes;
        }

        // Getters y Setters
        public String getLunes() {
            return lunes;
        }

        public String getMartes() {
            return martes;
        }

        public String getMiercoles() {
            return miercoles;
        }

        public String getJueves() {
            return jueves;
        }

        public String getViernes() {
            return viernes;
        }

    @Override
    public String toString() {
        return "HorarioRow{" +
                "lunes='" + lunes + '\'' +
                ", martes='" + martes + '\'' +
                ", miercoles='" + miercoles + '\'' +
                ", jueves='" + jueves + '\'' +
                ", viernes='" + viernes + '\'' +
                '}';
    }

    }



