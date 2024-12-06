/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listas;

/**
 *
 * @author Junior Nuñes
 */
public class ListaEnlazada {
    Nodo cabeza;

    public void agregarCancion(String nombreCancion, String nombreVisible, String artista, String duracion, String tipoMusica, String imagen) {
        Nodo nuevo = new Nodo(nombreCancion, nombreVisible, artista, duracion, tipoMusica, imagen);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo temp = cabeza;
            while (temp.siguiente != null) {
                temp = temp.siguiente;
            }
            temp.siguiente = nuevo;
        }
    }

    public Nodo seleccionarCancion(int indice) {
        Nodo temp = cabeza;
        int contador = 0;
        while (temp != null && contador < indice) {
            temp = temp.siguiente;
            contador++;
        }
        return temp;
    }
}
    