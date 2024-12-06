/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listas;

/**
 *
 * @author Laura Sabillon
 */public class Nodo {
    Musica musica; 
    Nodo siguiente;

    public Nodo(Musica musica) {
        this.musica = musica;
        this.siguiente = null;
    }
}