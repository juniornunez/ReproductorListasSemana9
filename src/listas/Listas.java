/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listas;

/**
 *
 * @author Hp
 */

import com.mpatric.mp3agic.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Listas extends JFrame {
    ListaEnlazada listaCanciones = new ListaEnlazada();
    DefaultListModel<String> modeloLista = new DefaultListModel<>();
    JList<String> listaVisual = new JList<>(modeloLista);
    JScrollPane scrollLista = new JScrollPane(listaVisual);
    JLabel imagenCancion = new JLabel();
    JLabel etiquetaNombre = new JLabel("Cancion: ");
    JLabel etiquetaArtista = new JLabel("Artista: ");
    JLabel etiquetaDuracion = new JLabel("Duracion: ");
    JLabel etiquetaTipo = new JLabel("Tipo de Musica: ");
    Nodo cancionActual = null;
    Player reproductor;
    Thread hiloReproduccion;
    boolean enPausa = false;
    boolean mensajeMostrado = false;
    long posicionPausa = 0;
    FileInputStream flujoArchivo;
    File archivoReproduccion;

    public Listas() {
        super("Reproductor Musical");
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        JButton botonReproducir = new JButton("Reproducir");
        JButton botonPausar = new JButton("Pausar");
        JButton botonDetener = new JButton("Detener");
        JButton botonAgregar = new JButton("Agregar");
        JButton botonSalir = new JButton("Salir");

        Color verdeBoton = new Color(0, 204, 102);
        botonReproducir.setBackground(verdeBoton);
        botonPausar.setBackground(verdeBoton);
        botonDetener.setBackground(verdeBoton);
        botonAgregar.setBackground(verdeBoton);
        botonSalir.setBackground(verdeBoton);

        panelBotones.add(botonReproducir);
        panelBotones.add(botonPausar);
        panelBotones.add(botonDetener);
        panelBotones.add(botonAgregar);
        panelBotones.add(botonSalir);

        JPanel panelCancion = new JPanel(new BorderLayout());
        panelCancion.add(scrollLista, BorderLayout.CENTER);

        JPanel panelImagenYDetalles = new JPanel(new BorderLayout());

        JPanel panelDetalles = new JPanel();
        panelDetalles.setLayout(new BoxLayout(panelDetalles, BoxLayout.Y_AXIS));
        panelDetalles.add(etiquetaNombre);
        panelDetalles.add(etiquetaArtista);
        panelDetalles.add(etiquetaDuracion);
        panelDetalles.add(etiquetaTipo);

        imagenCancion.setHorizontalAlignment(JLabel.CENTER);
        panelImagenYDetalles.add(imagenCancion, BorderLayout.CENTER);
        panelImagenYDetalles.add(panelDetalles, BorderLayout.WEST);

        panelCancion.add(panelImagenYDetalles, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);
        add(panelCancion, BorderLayout.CENTER);

        botonAgregar.addActionListener(e -> abrirFrameAgregarCancion());

        // Listener para mostrar detalles al seleccionar una canción
        listaVisual.addListSelectionListener(e -> {
            int seleccion = listaVisual.getSelectedIndex();
            if (seleccion != -1) {
                cancionActual = listaCanciones.seleccionarCancion(seleccion);
                if (cancionActual != null) {
                    etiquetaNombre.setText("Cancion: " + cancionActual.nombreVisible);
                    etiquetaArtista.setText("Artista: " + cancionActual.artista);
                    etiquetaDuracion.setText("Duracion: " + cancionActual.duracion);
                    etiquetaTipo.setText("Tipo de Musica: " + cancionActual.tipoMusica);
                    mostrarImagen(cancionActual.imagen);
                    mensajeMostrado = false;
                }
            }
        });

        botonReproducir.addActionListener(e -> {
            if (cancionActual != null) {
                reproducirCancion(cancionActual.nombreCancion);
            }
        });

        botonPausar.addActionListener(e -> {
            if (reproductor != null && !enPausa) {
                pausarReproduccion();
            }
        });

        botonDetener.addActionListener(e -> detenerReproduccion());

        botonSalir.addActionListener(e -> System.exit(0));

        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void abrirFrameAgregarCancion() {
        JFrame frameAgregar = new JFrame("Agregar Cancion");
        frameAgregar.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel etiquetaNombreCancion = new JLabel("Nombre de la cancion:");
        JTextField campoNombreCancion = new JTextField();

        JLabel etiquetaArtistaCancion = new JLabel("Artista:");
        JTextField campoArtistaCancion = new JTextField();

        JLabel etiquetaDuracionCancion = new JLabel("Duracion:");
        JTextField campoDuracionCancion = new JTextField();

        JLabel etiquetaTipoMusica = new JLabel("Tipo de Musica:");
        JTextField campoTipoMusica = new JTextField();

        JLabel etiquetaArchivo = new JLabel("Seleccionar archivo:");
        JButton botonSeleccionarArchivo = new JButton("Seleccionar archivo");

        JButton botonGuardar = new JButton("Guardar");

        Color verdeBoton = new Color(0, 204, 102);
        botonSeleccionarArchivo.setBackground(verdeBoton);
        botonGuardar.setBackground(verdeBoton);

        File[] archivoSeleccionado = new File[1];

        botonSeleccionarArchivo.addActionListener(e -> {
            JFileChooser selectorArchivo = new JFileChooser();
            selectorArchivo.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos MP3", "mp3"));
            int respuesta = selectorArchivo.showOpenDialog(frameAgregar);
            if (respuesta == JFileChooser.APPROVE_OPTION) {
                archivoSeleccionado[0] = selectorArchivo.getSelectedFile();
            }
        });

        botonGuardar.addActionListener(e -> {
            if (archivoSeleccionado[0] != null) {
                String nombreCancion = campoNombreCancion.getText();
                String artista = campoArtistaCancion.getText();
                String duracion = campoDuracionCancion.getText();
                String tipo = campoTipoMusica.getText();
                String imagen = extraerImagenDeMP3(archivoSeleccionado[0]);

                listaCanciones.agregarCancion(archivoSeleccionado[0].getAbsolutePath(), nombreCancion, artista, duracion, tipo, imagen);
                modeloLista.addElement(nombreCancion);
                frameAgregar.dispose();
            } else {
                JOptionPane.showMessageDialog(frameAgregar, "Debes de seleccionar un archivo MP3", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frameAgregar.add(etiquetaNombreCancion);
        frameAgregar.add(campoNombreCancion);
        frameAgregar.add(etiquetaArtistaCancion);
        frameAgregar.add(campoArtistaCancion);
        frameAgregar.add(etiquetaDuracionCancion);
        frameAgregar.add(campoDuracionCancion);
        frameAgregar.add(etiquetaTipoMusica);
        frameAgregar.add(campoTipoMusica);
        frameAgregar.add(etiquetaArchivo);
        frameAgregar.add(botonSeleccionarArchivo);
        frameAgregar.add(new JLabel());
        frameAgregar.add(botonGuardar);

        frameAgregar.setSize(400, 300);
        frameAgregar.setLocationRelativeTo(null);
        frameAgregar.setVisible(true);
    }

    private String extraerImagenDeMP3(File archivoMP3) {
        try {
            Mp3File mp3file = new Mp3File(archivoMP3);
            if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                byte[] imagenDatos = id3v2Tag.getAlbumImage();
                if (imagenDatos != null) {
                    File imagenTemp = new File("temp_imagen.jpg");
                    FileOutputStream fos = new FileOutputStream(imagenTemp);
                    fos.write(imagenDatos);
                    fos.close();
                    return imagenTemp.getAbsolutePath();
                }
            }
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void mostrarImagen(String rutaImagen) {
        if (rutaImagen != null) {
            ImageIcon icono = new ImageIcon(rutaImagen);
            Image imagenEscalada = icono.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            imagenCancion.setIcon(new ImageIcon(imagenEscalada));
        } else {
            imagenCancion.setIcon(null);
            imagenCancion.setText("Sin Imagen");
        }
    }

    private void reproducirCancion(String rutaCancion) {
        detenerReproduccion();
        try {
            archivoReproduccion = new File(rutaCancion);
            flujoArchivo = new FileInputStream(archivoReproduccion);
            reproductor = new Player(flujoArchivo);
            hiloReproduccion = new Thread(() -> {
                try {
                    reproductor.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            });
            hiloReproduccion.start();
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
        enPausa = false;
    }

    private void detenerReproduccion() {
        if (reproductor != null) {
            reproductor.close();
            hiloReproduccion = null;
        }
        enPausa = false;
        posicionPausa = 0;
    }

    private void pausarReproduccion() {
        if (reproductor != null) {
            try {
                posicionPausa = flujoArchivo.available();
                reproductor.close();
                enPausa = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Listas();
    }
}

class Nodo {
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

class ListaEnlazada {
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