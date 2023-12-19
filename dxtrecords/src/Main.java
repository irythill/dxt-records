import dao.*;
import menu.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArtistDAO artistDAO = new ArtistDAO();
        MusicDAO musicDAO = new MusicDAO();
        DistributorDAO distributorDAO = new DistributorDAO();
        ArtistMusicDAO artistMusicDAO = new ArtistMusicDAO();
        MusicDistributorDAO musicDistributorDAO = new MusicDistributorDAO();
        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu(artistDAO, musicDAO, distributorDAO, artistMusicDAO, musicDistributorDAO, scanner);
        menu.mainMenu();
    }
}