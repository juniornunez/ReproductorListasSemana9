/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listas;

/**
 *
 * @author Hp
 */
public class Nodo {
    
    String nombreCancion;
    String nombreVisible;
    String artista;
    String duracion;
    String tipoMusica;
    String imagen;
    Nodo siguiente;

    public Nodo(String nombreCancion, String nombreVisible, String artista, String duracion, String tipoMusica, String imagen) {
        this.nombreCancion = nombreCancion;
        this.nombreVisible = nombreVisible;
        this.artista = artista;
        this.duracion = duracion;
        this.tipoMusica = tipoMusica;
        this.imagen = imagen;
        this.siguiente = null;
    }
}


