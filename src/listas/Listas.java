/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package listas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Laura Sabillon
 */
public class Listas extends JFrame {

    private ListaEnlazada listaEnlazada = new ListaEnlazada();
    private String ikon = null;
    private String ruta = null;
    private JFrame lista_C;

    private String currentAlbum;
    private int currentDuration;
    private String songAlbum;
    private int songDuration;
    private int playbackPosition = 0;
    private boolean isSeeking = false;
    private JPanel musicListPanel;
    private Player currentPlayer;
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private File currentSongFile;
    private Player play;

    public Listas() {
        setTitle("REPRODUCTOR DE MUSICA");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("REPRODUCTOR DE MUSICA");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 20, 0);

        JButton add = createButton("AÑADIR CANCIÓN");
        JButton elegir = createButton("ELEGIR CANCIÓN");
        JButton salir = createButton("SALIR");

        panel.add(add, gbc);
        gbc.gridy++;
        panel.add(elegir, gbc);
        gbc.gridy++;
        panel.add(salir, gbc);

        panel.setBackground(Color.BLACK);

        add(panel, BorderLayout.CENTER);

        setVisible(true);

        //add
        add.addActionListener(e -> {

            JFrame añadir = new JFrame("AÑADIR CANCIONES");
            añadir.setSize(400, 500);
            añadir.setVisible(true);
            añadir.setLocationRelativeTo(null);
            añadir.getContentPane().setBackground(Color.BLACK);

            JPanel panele = new JPanel(new GridBagLayout());
            panele.setBackground(Color.BLACK);
            GridBagConstraints gb = new GridBagConstraints();
            gb.insets = new Insets(10, 10, 10, 10);

            JLabel titleLabel = new JLabel("AÑADIR CANCIONES");
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            gb.gridx = 0;
            gb.gridy = 0;
            gb.gridwidth = 2;
            gb.anchor = GridBagConstraints.CENTER;
            panele.add(titleLabel, gb);

            gb.gridwidth = 1;
            gb.anchor = GridBagConstraints.WEST;

            Dimension textFieldSize = new Dimension(200, 25);

            JLabel label1 = new JLabel("Song Title:");
            label1.setForeground(Color.WHITE);
            gb.gridx = 0;
            gb.gridy = 1;
            panele.add(label1, gb);

            JTextField cancion = new JTextField();
            cancion.setPreferredSize(textFieldSize);
            gb.gridx = 1;
            panele.add(cancion, gb);

            JLabel label2 = new JLabel("Artista:");
            label2.setForeground(Color.WHITE);
            gb.gridx = 0;
            gb.gridy = 2;
            panele.add(label2, gb);

            JTextField artist = new JTextField();
            artist.setPreferredSize(textFieldSize);
            gb.gridx = 1;
            panele.add(artist, gb);

            JLabel label4 = new JLabel("Cover Path:");
            label4.setForeground(Color.WHITE);
            gb.gridx = 0;
            gb.gridy = 4;
            panele.add(label4, gb);

            JButton icono = new JButton("Escoger Icono");
            icono.setPreferredSize(textFieldSize);
            gb.gridx = 1;
            panele.add(icono, gb);

            JLabel label5 = new JLabel("Duracion (segundos):");
            label5.setForeground(Color.WHITE);
            gb.gridx = 0;
            gb.gridy = 5;
            panele.add(label5, gb);

            JTextField textField5 = new JTextField();
            textField5.setPreferredSize(textFieldSize);
            gb.gridx = 1;
            panele.add(textField5, gb);

            JLabel label6 = new JLabel("Escoger Archivo:");
            label6.setForeground(Color.WHITE);
            gb.gridx = 0;
            gb.gridy = 6;
            panele.add(label6, gb);

            JButton mp3 = new JButton("Escoger Mp3");
            mp3.setPreferredSize(textFieldSize);
            gb.gridx = 1;
            panele.add(mp3, gb);

            JButton addButton = new JButton("AÑADIR CANCION");
            addButton.setPreferredSize(new Dimension(300, 30));
            gb.gridx = 0;
            gb.gridy = 7;
            gb.gridwidth = 2;
            gb.anchor = GridBagConstraints.CENTER;
            panele.add(addButton, gb);

            icono.addActionListener(ex -> {
                JFileChooser fileChooser = new JFileChooser();

                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files (PNG, JPG)", "png", "jpg");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    ikon = filePath;
                    JOptionPane.showMessageDialog(null, "Selected Image: " + selectedFile.getName());
                }
            });

            mp3.addActionListener(ex -> {
                JFileChooser fileChooser = new JFileChooser();

                FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 Files (*.mp3)", "mp3");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    ruta = filePath;
                    JOptionPane.showMessageDialog(null, "Selected Mp3: " + selectedFile.getName());
                }
            });
            addButton.addActionListener(actionEvent -> {
                String titulo = cancion.getText();
                String artista = artist.getText();
                String coverPath = ikon;
                String duracion = "";
                int time = 0;

                try {
                    duracion = textField5.getText().trim();
                    time = Integer.parseInt(duracion);

                    System.out.println("Duration is: " + duracion);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Duracion tiene que ser un numero!", "Input Error", JOptionPane.ERROR_MESSAGE);
                }

                String path = ruta;
                if (ikon == null && ruta == null) {
                    JOptionPane.showMessageDialog(null, "Tiene que escoger un icono y un mp3! ");
                    return;
                }
                if (ikon == null) {
                    JOptionPane.showMessageDialog(null, "Tiene que escoger un archivo mp3! ");
                    return;
                }
                if (ruta == null) {
                    JOptionPane.showMessageDialog(null, "Tiene que escoger un icono! ");
                    return;
                }
                if (titulo.isEmpty() || artista.isEmpty() || duracion.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tiene que llenar todo los espacios!!");
                } else {

                    listaEnlazada.agregarCancion(titulo, artista, coverPath, time, path);
                    JOptionPane.showMessageDialog(añadir, "Song added: " + titulo + " by " + artista);
                    añadir.dispose();
                }
            });

            añadir.add(panele);
        });

        //elegir
        elegir.addActionListener(e -> {
            lista_C = new JFrame("LISTA DE CANCIONES");
            lista_C.setSize(400, 500);
            lista_C.setVisible(true);
            lista_C.setLocationRelativeTo(null);
            lista_C.getContentPane().setBackground(Color.BLACK);

            JPanel musicPanel = createMusicPanel();

            lista_C.getContentPane().add(musicPanel);

            lista_C.revalidate();
            lista_C.repaint();
        });

        //salir
        salir.addActionListener(e -> {
            System.exit(0);
        });
    }

    public JPanel createMusicPanel() {
        JPanel musicPanel = new JPanel(new BorderLayout());
        musicPanel.setBackground(Color.BLACK);

        JPanel searchBarPanel = new JPanel(new BorderLayout());
        searchBarPanel.setBackground(Color.BLACK);

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 18));
        searchField.setBackground(Color.BLACK);
        searchField.setForeground(Color.WHITE);
        searchField.setCaretColor(Color.WHITE);
        searchField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        searchField.setPreferredSize(new Dimension(250, 40));

        JLabel searchButton = new JLabel("Buscar");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(Color.BLACK);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        searchBarPanel.add(searchField, BorderLayout.CENTER);
        searchBarPanel.add(searchButton, BorderLayout.WEST);

        DefaultListModel<String> songListModel = new DefaultListModel<>();
        JList<String> songList = new JList<>(songListModel);
        songList.setBackground(Color.BLACK);
        songList.setForeground(Color.WHITE);
        songList.setFont(new Font("Arial", Font.PLAIN, 16));
        songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        songList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value);
            label.setBackground(Color.BLACK);
            label.setForeground(isSelected ? Color.WHITE : Color.GRAY);
            label.setFont(new Font("Arial", Font.PLAIN, 16));

            if (!isSelected) {
                label.setOpaque(false);
            }

            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            return label;
        });

        Nodo current = listaEnlazada.cabeza;
        while (current != null) {
            Musica musica = current.musica;
            String song = musica.getTitulo() + " - " + musica.getArtista();
            songListModel.addElement(song);
            current = current.siguiente;
        }

        songList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedSong = songList.getSelectedValue();
                    if (selectedSong != null) {
                        String artistName = selectedSong.split(" - ")[1];
                        String songTitle = selectedSong.split(" - ")[0];

                        Nodo temp = listaEnlazada.cabeza;
                        while (temp != null) {
                            Musica musica = temp.musica;
                            if (musica.getTitulo().equals(songTitle) && musica.getArtista().equals(artistName)) {
                                String albumArtPath = musica.getCoverPath();
                                File songFile = new File(musica.getRuta());
                                showNowPlayingPanel(songTitle, artistName, albumArtPath, songFile);
                                break;
                            }
                            temp = temp.siguiente;
                        }
                    }
                }
            }
        });

        JScrollPane songListScrollPane = new JScrollPane(songList);
        songListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        songListScrollPane.setPreferredSize(new Dimension(400, 300));

        musicPanel.add(searchBarPanel, BorderLayout.NORTH);
        musicPanel.add(songListScrollPane, BorderLayout.CENTER);
        JButton returnButton = new JButton("REGRESAR");
        returnButton.setFont(new Font("Arial", Font.BOLD, 14));
        returnButton.setBackground(Color.BLACK);
        returnButton.setForeground(Color.WHITE);
        returnButton.setFocusPainted(false);
        returnButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        returnButton.addActionListener(e -> {
            lista_C.dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(returnButton);

        musicPanel.add(searchBarPanel, BorderLayout.NORTH);
        musicPanel.add(songListScrollPane, BorderLayout.CENTER);
        musicPanel.add(bottomPanel, BorderLayout.SOUTH);

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterMusic();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterMusic();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterMusic();
            }

            private void filterMusic() {
                String searchText = searchField.getText().toLowerCase();
                DefaultListModel<String> songListModel = (DefaultListModel<String>) songList.getModel();

                songListModel.clear();

                Nodo current = listaEnlazada.cabeza;
                while (current != null) {
                    Musica musica = current.musica;
                    String song = musica.getTitulo() + " - " + musica.getArtista();

                    if (song.toLowerCase().contains(searchText)) {
                        songListModel.addElement(song);
                    }

                    current = current.siguiente;
                }
            }

        });

        songList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = songList.locationToIndex(e.getPoint());
                if (index != -1) {
                    songList.repaint();
                    songList.setSelectedIndex(index);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int index = songList.locationToIndex(e.getPoint());
                if (index != -1) {
                    songList.repaint();
                }
            }
        });

        return musicPanel;
    }

    private void showNowPlayingPanel(String songTitle, String artistName, String albumArtPath, File file) {
        currentAlbum = songTitle;
        currentDuration = (int) file.length();

        if (currentPlayer != null && isPlaying) {
            stopMusic();
        }

        JFrame nowPlayingDialog = new JFrame("Now Playing");
        nowPlayingDialog.setLayout(new BorderLayout());

        nowPlayingDialog.setSize(400, 500);
        nowPlayingDialog.setResizable(true);
        nowPlayingDialog.getContentPane().setBackground(Color.BLACK);
        nowPlayingDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        nowPlayingDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                stopMusic();
                currentSongFile = null;
                nowPlayingDialog.dispose();
            }
        });
        nowPlayingDialog.setAlwaysOnTop(true);

        // Album Art
        JLabel albumArtLabel = new JLabel();
        albumArtLabel.setHorizontalAlignment(SwingConstants.CENTER);
        albumArtLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        if (albumArtPath != null && !albumArtPath.isEmpty()) {
            albumArtLabel.setIcon(new ImageIcon(new ImageIcon(albumArtPath).getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH)));
        } else {
            albumArtLabel.setText("No Artwork Available");
            albumArtLabel.setForeground(Color.WHITE);
        }

        // SONG INFO
        JPanel songInfoPanel = new JPanel();
        songInfoPanel.setLayout(new GridLayout(2, 1));
        songInfoPanel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel(songTitle, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JLabel artistLabel = new JLabel(artistName, SwingConstants.CENTER);
        artistLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        artistLabel.setForeground(Color.GRAY);

        songInfoPanel.add(titleLabel);
        songInfoPanel.add(artistLabel);

        // BUTTONS PANEL
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlsPanel.setBackground(Color.WHITE);

        JButton playPauseButton = new JButton();
        ImageIcon icon = new ImageIcon("DefaultIMAGE/play.png");
        playPauseButton.setIcon(icon);
        playPauseButton.setBackground(Color.WHITE);
        playPauseButton.setForeground(Color.WHITE);

        playPauseButton.addActionListener(e -> {
            if (isPlaying && !isPaused) {
                pauseMusic();
                playPauseButton.setIcon(icon);
            } else if (isPaused) {
                resumeMusic();
                playPauseButton.setIcon(new ImageIcon("DefaultIMAGE/pause.png"));
            } else {
                playMusic(file);
                playPauseButton.setIcon(new ImageIcon("DefaultIMAGE/pause.png"));
            }
        });

        controlsPanel.add(playPauseButton);

        nowPlayingDialog.add(albumArtLabel, BorderLayout.NORTH);
        nowPlayingDialog.add(songInfoPanel, BorderLayout.CENTER);
        nowPlayingDialog.add(controlsPanel, BorderLayout.SOUTH);

        nowPlayingDialog.setLocation(1100, Toolkit.getDefaultToolkit().getScreenSize().height - nowPlayingDialog.getHeight() - 250);
        nowPlayingDialog.setVisible(true);
    }

    private void playMusic(File file) {
        try {
            currentSongFile = file;
            currentPlayer = new Player(new FileInputStream(file));
            isPlaying = true;
            isPaused = false;
            System.out.println("Stopping music: " + (currentSongFile != null ? currentSongFile.getName() : "None"));
            System.out.println("Starting music: " + file.getName());
            new Thread(() -> {
                try {
                    currentPlayer.play();
                    isPlaying = false;
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (JavaLayerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void resumeMusic() {
        if (currentSongFile != null && isPaused) {
            try {
                FileInputStream fileInputStream = new FileInputStream(currentSongFile);
                currentPlayer = new Player(fileInputStream);

                isSeeking = true;
                new Thread(() -> {
                    try {
                        fileInputStream.skip(playbackPosition);
                        currentPlayer.play();
                        isSeeking = false;
                        isPlaying = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

                isPlaying = true;
                isPaused = false;
                System.out.println("Resumed from position: " + playbackPosition + " ms");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No song to resume.");
        }
    }

    private void pauseMusic() {
        if (currentPlayer != null && isPlaying) {
            try {
                playbackPosition += currentPlayer.getPosition();
                stopMusic();
                isPaused = true;
                System.out.println("Paused at position: " + playbackPosition + " ms");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stopMusic() {
        if (currentPlayer != null) {
            currentPlayer.close();
            isPlaying = false;
            isPaused = false;
        }
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 50));
        return button;
    }

    public static void main(String[] args) {
        Listas list = new Listas();
    }
}
