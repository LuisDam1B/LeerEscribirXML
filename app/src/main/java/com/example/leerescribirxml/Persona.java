package com.example.leerescribirxml;

import java.io.Serializable;

public class Persona implements Serializable {
    private String nombre;
    private int puntuacion;
    private String sexo;

    public Persona(){

    }

    public Persona(String nombre, int puntuacion, String sexo) {
        this.nombre = nombre;
        this.puntuacion = puntuacion;
        this.sexo = sexo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getpuntuacion() {
        return puntuacion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setpuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", puntuacion=" + puntuacion +
                ", sexo='" + sexo + '\'' +
                '}';
    }
}
