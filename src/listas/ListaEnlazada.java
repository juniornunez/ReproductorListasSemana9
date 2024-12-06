/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listas;

/**
 *
 * @author Laura Sabillon
 */
public class ListaEnlazada {

    Nodo cabeza;

    public void agregarCancion(String titulo, String artista, String coverPath, int duracion, String ruta) {
        Musica nuevaCancion = new Musica(titulo, artista, coverPath, duracion, ruta);
        Nodo nuevo = new Nodo(nuevaCancion);
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
