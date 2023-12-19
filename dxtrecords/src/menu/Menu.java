package menu;

import dao.*;
import domain.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private final ArtistDAO artistDAO;
    private final MusicDAO musicDAO;
    private final DistributorDAO distributorDAO;
    private final ArtistMusicDAO artistMusicDAO;
    private final MusicDistributorDAO musicDistributorDAO;
    private final Scanner scanner;
    ArrayList<Artist> artList = new ArrayList<>();
    ArrayList<Music> musList = new ArrayList<>();
    ArrayList<Distributor> disList = new ArrayList<>();
    ArrayList<ArtistMusic> artMusList = new ArrayList<>();

    ArrayList<MusicDistributor> musDisList = new ArrayList<>();


    public Menu(ArtistDAO artistDAO, MusicDAO musicDAO, DistributorDAO distributorDAO, ArtistMusicDAO artistMusicDAO, MusicDistributorDAO musicDistributorDAO, Scanner scanner) {
        this.artistDAO = artistDAO;
        this.musicDAO = musicDAO;
        this.distributorDAO = distributorDAO;
        this.artistMusicDAO = artistMusicDAO;
        this.musicDistributorDAO = musicDistributorDAO;
        this.scanner = scanner;
    }

    public void mainMenu() {
        System.out.println("-- DXT ENTERTAINMENT RECORDS --\n");
        System.out.println("0 - Exit");
        System.out.println("1 - Artist");
        System.out.println("2 - Music");
        System.out.println("3 - Distributor");
        System.out.println("4 - Services");

        System.out.println("Pick an option from above:");
        int option = Integer.parseInt(scanner.nextLine());

        switch (option) {
            case 0 -> {
                System.out.println("Closing...");
                scanner.close();
                System.exit(0);
            }
            case 1 -> artistMenu();
            case 2 -> musicMenu();
            case 3 -> distributorMenu();
            case 4 -> servicesMenu();
            default -> System.out.println("Invalid option!\n");
        }
    }

    private void artistMenu() {
        boolean closeMenu = false;

        while (!closeMenu) {
            System.out.println("-- ARTIST MENU --\n");
            System.out.println("0 - Return to main menu");
            System.out.println("1 - Register an artist");
            System.out.println("2 - List all artists");
            System.out.println("3 - Search an artist by his ID");
            System.out.println("4 - Search artists by their music");
            System.out.println("5 - Update an artist");
            System.out.println("6 - Delete an artist");

            System.out.println("Pick an option from above:");
            int option = Integer.parseInt(scanner.nextLine());

            artList = artistDAO.list();
            musList = musicDAO.list();

            switch (option) {
                case 0 -> mainMenu();

                case 1 -> {
                    System.out.println("-- ARTIST REGISTRATION --\n");

                    System.out.println("Inform the artist's name:");
                    String artName = scanner.nextLine();

                    System.out.println("Inform the artist's country:");
                    String artCountry = scanner.nextLine();

                    System.out.println("Select a role for the artist:");
                    for (Role role : Role.values()) {
                        System.out.println(role.getID() + " - " + role.getRole());
                    }
                    int selectedRole = Integer.parseInt(scanner.nextLine());

                    System.out.println("Inform a social network for the artist:");
                    String artSocial = scanner.nextLine();

                    Artist artist = new Artist(artName, artCountry, Role.values()[selectedRole], artSocial);

                    if (artistDAO.insert(artist) > 0) {
                        System.out.println("Success! " + artName + " is registered.\n");
                    } else {
                        System.out.println("Failed!\n");
                    }
                }

                case 2 -> {
                    System.out.println("-- LIST ALL ARTISTS --\n");

                    if (artList.isEmpty()) {
                        System.out.println("There is no artist registered!\n");
                        artistMenu();
                    } else {
                        listAllArtists();
                    }
                }

                case 3 -> {
                    System.out.println("-- SEARCH AN ARTIST BY HIS ID --\n");

                    if (artList.isEmpty()) {
                        System.out.println("There is no artist registered!\n");
                        artistMenu();
                    } else {
                        System.out.println("Inform the artists' ID you wish to find:");
                        int id = Integer.parseInt(scanner.nextLine());

                        Artist artist = artistDAO.read(id);

                        if (artist != null) {
                            System.out.println("Artist found, below you will see all his info:");
                            System.out.println("Name: " + artist.getName());
                            System.out.println("Country: " + artist.getCountry());
                            System.out.println("Role: " + artist.getRole());
                            System.out.println("Social network: " + artist.getSocialNetwork());
                            System.out.println("---\n");
                        } else {
                            System.out.println("Couldn't find any artist with the provided ID " + id);
                        }
                    }
                }

                case 4 -> {
                    System.out.println("-- SEARCH ARTISTS BY THEIR MUSIC --\n");

                    if (artList.isEmpty()) {
                        System.out.println("There is no artist registered!\n");
                        artistMenu();
                    } else {
                        listAllMusics();

                        System.out.println("Enter the ISRC of the music to search for artists:");
                        String musISRC = scanner.nextLine();

                        ArrayList<Artist> artists = artistMusicDAO.searchArtistByMusic(musISRC);

                        if (artists.isEmpty()) {
                            System.out.println("No artists found with the provided ISRC " + musISRC);
                        } else {
                            System.out.println("Artists associated with the ISRC " + musISRC + ":");
                            for (Artist artist : artists) {
                                System.out.println("Name: " + artist.getName());
                                System.out.println("Role: " + artist.getRole());
                                System.out.println("Country: " + artist.getCountry());
                                System.out.println("Social network: " + artist.getSocialNetwork());
                                System.out.println("---");
                            }
                        }
                    }
                }

                case 5 -> {
                    System.out.println("-- UPDATE AN ARTIST --\n");

                    if (artList.isEmpty()) {
                        System.out.println("There is no artist registered to update!\n");
                        artistMenu();
                    } else {
                        listAllArtists();

                        System.out.println("Pick an artist by his ID from the list above:");
                        int idArt = Integer.parseInt(scanner.nextLine());

                        Artist artist = artistDAO.read(idArt);

                        if (artist == null) {
                            System.out.println("Couldn't find any artist with the provided ID: " + idArt);
                        } else {
                            System.out.println("Which information would you like to change?");
                            System.out.println("0 - Return to artist menu");
                            System.out.println("1 - Name");
                            System.out.println("2 - Country");
                            System.out.println("3 - Role");
                            System.out.println("4 - Social network");

                            int infOpt = Integer.parseInt(scanner.nextLine());

                            switch (infOpt) {
                                case 0 -> artistMenu();

                                case 1 -> {
                                    System.out.println("Inform a new name for the artist:");
                                    String artName = scanner.nextLine();
                                    artist.setName(artName);
                                }

                                case 2 -> {
                                    System.out.println("Inform a new country for the artist:");
                                    String artCountry = scanner.nextLine();
                                    artist.setCountry(artCountry);
                                }

                                case 3 -> {
                                    System.out.println("Select a new role for the artist:");
                                    for (Role role : Role.values()) {
                                        System.out.println(role.getID() + " - " + role.getRole());
                                    }

                                    int newRoleId = Integer.parseInt(scanner.nextLine());
                                    Role newRole = findRoleById(newRoleId);

                                    if (newRole != null) {
                                        artist.setRole(newRole);
                                        System.out.println("Role successfully updated!\n");
                                    } else {
                                        System.out.println("Failed. Invalid role ID!\n");
                                    }
                                }

                                case 4 -> {
                                    System.out.println("Inform a new social network for the artist:");
                                    String artSocial = scanner.nextLine();
                                    artist.setSocialNetwork(artSocial);
                                }
                                default -> System.out.println("Invalid option!\n");
                            }
                        }
                        if (artistDAO.update(artist) > 0) {
                            System.out.println("Success!\n");
                        } else {
                            System.out.println("Failed!\n");
                        }
                    }
                }

                case 6 -> {
                    System.out.println("-- DELETE AN ARTIST --\n");

                    if (artList.isEmpty()) {
                        System.out.println("There is no artist registered!\n");
                        artistMenu();
                    } else {
                        listAllArtists();

                        System.out.println("Inform the artists' ID you wish to delete from the list above");
                        int id = Integer.parseInt(scanner.nextLine());

                        if (artistDAO.hasMusicAssociation(id)) {
                            System.out.println("An artist cannot have any music associated to be deleted!\n");
                        } else {
                            if (artistDAO.delete(id) > 0) {
                                System.out.println("Successfully deleted!\n");
                            } else {
                                System.out.println("Failed!\n");
                            }
                        }
                    }
                }

                default -> System.out.println("Invalid option!\n");
            }
        }
    }

    private void musicMenu() {
        boolean closeMenu = false;

        while (!closeMenu) {
            System.out.println("-- MUSIC MENU --\n");
            System.out.println("0 - Return to main menu");
            System.out.println("1 - Register a music");
            System.out.println("2 - List all musics");
            System.out.println("3 - Search a music by its ISRC");
            System.out.println("4 - Search musics by their artist");
            System.out.println("5 - Update a music");
            System.out.println("6 - Delete a music");

            System.out.println("Pick an option from above:");
            int option = Integer.parseInt(scanner.nextLine());

            musList = musicDAO.list();
            artList = artistDAO.list();

            switch (option) {
                case 0 -> mainMenu();

                case 1 -> {
                    System.out.println("-- MUSIC REGISTRATION --\n");

                    System.out.println("Inform the music's ISRC (format XX-XXX-XX-XXXXX):");
                    String musIRSC = scanner.nextLine();

                    System.out.println("Inform the music's name:");
                    String musName = scanner.nextLine();

                    System.out.println("Select a genre for the music:");
                    for (Genre genre : Genre.values()) {
                        System.out.println(genre.getID() + " - " + genre.getGenre());
                    }
                    int selectedGenre = Integer.parseInt(scanner.nextLine());

                    System.out.println("Inform the music's duration (ex.: 3m33s):");
                    String musDuration = scanner.nextLine();

                    System.out.println("Inform the music's release date (format YYYY-MM-DD):");
                    String musRelease = scanner.nextLine();

                    Music music = new Music(musIRSC, musName, Genre.values()[selectedGenre], musDuration, musRelease);

                    if (musicDAO.insert(music) > 0) {
                        System.out.println("Success! " + musName + " is registered.\n");
                    } else {
                        System.out.println("Failed!\n");
                    }
                }

                case 2 -> {
                    System.out.println("-- LIST ALL MUSICS --\n");

                    if (musList.isEmpty()) {
                        System.out.println("There is no music registered!\n");
                        musicMenu();
                    } else {
                        listAllMusics();
                    }
                }

                case 3 -> {
                    System.out.println("-- SEARCH A MUSIC BY ITS ISRC --\n");

                    if (musList.isEmpty()) {
                        System.out.println("There is no music registered!\n");
                        musicMenu();
                    } else {
                        System.out.println("Inform the musics' ISRC you wish to find:");
                        String isrc = scanner.nextLine();

                        Music music = musicDAO.read(isrc);

                        if (music != null) {
                            System.out.println("Music found, below you will see all its info:");
                            System.out.println("Name: " + music.getName());
                            System.out.println("Genre: " + music.getGenre());
                            System.out.println("Duration: " + music.getDuration());
                            System.out.println("Release date: " + music.getReleaseDate());
                            System.out.println("---\n");

                        } else {
                            System.out.println("Couldn't find any music with the provided ISRC " + isrc);
                        }
                    }
                }

                case 4 -> {
                    System.out.println("-- SEARCH MUSICS BY THEIR ARTIST --\n");

                    if (musList.isEmpty()) {
                        System.out.println("There is no music registered!\n");
                        musicMenu();
                    } else {
                        listAllArtists();

                        System.out.println("Enter the ID of the artist to search for musics:");
                        int id = Integer.parseInt(scanner.nextLine());

                        ArrayList<Music> musics = artistMusicDAO.searchMusicByArtist(id);

                        if (musics.isEmpty()) {
                            System.out.println("No musics found with the provided ID " + id);
                        } else {
                            System.out.println("Musics associated with the ID " + id + ":");
                            for (Music music : musics) {
                                System.out.println("Name: " + music.getName());
                                System.out.println("Role: " + music.getGenre());
                                System.out.println("Duration: " + music.getDuration());
                                System.out.println("Release date: " + music.getReleaseDate());
                                System.out.println("---");
                            }
                        }
                    }
                }

                case 5 -> {
                    System.out.println("-- UPDATE A MUSIC --\n");

                    if (artList.isEmpty()) {
                        System.out.println("There is no music registered!\n");
                        musicMenu();
                    } else {
                        listAllMusics();

                        System.out.println("Pick a music by its ISRC from the list above:");
                        String isrcMus = scanner.nextLine();

                        Music music = musicDAO.read(isrcMus);

                        if (isrcMus == null) {
                            System.out.println("Couldn't find any music with the provided ISRC: " + isrcMus);
                        } else {
                            System.out.println("Which information would you like to change?");
                            System.out.println("0 - Return to music menu");
                            System.out.println("1 - Name");
                            System.out.println("2 - Genre");
                            System.out.println("3 - Duration");
                            System.out.println("4 - Release date");

                            int infOpt = Integer.parseInt(scanner.nextLine());

                            switch (infOpt) {
                                case 0 -> musicMenu();

                                case 1 -> {
                                    System.out.println("Inform a new name for the music:");
                                    String musName = scanner.nextLine();
                                    music.setName(musName);
                                }

                                case 2 -> {
                                    System.out.println("Select a new genre for the music:");
                                    for (Genre genre : Genre.values()) {
                                        System.out.println(genre.getID() + " - " + genre.getGenre());
                                    }

                                    int newGenreId = Integer.parseInt(scanner.nextLine());
                                    Genre newGenre = findGenreById(newGenreId);

                                    if (newGenre != null) {
                                        music.setGenre(newGenre);
                                        System.out.println("Genre successfully updated!\n");
                                    } else {
                                        System.out.println("Failed. Invalid role ID!\n");
                                    }
                                }

                                case 3 -> {
                                    System.out.println("Inform the new duration for the music (ex.: 3m33s):");
                                    String musDuration = scanner.nextLine();
                                    music.setDuration(musDuration);
                                }

                                case 4 -> {
                                    System.out.println("Inform the new release date for the music (format YYYY-MM-DD)");
                                    String musRelease = scanner.nextLine();
                                    music.setReleaseDate(musRelease);
                                }
                                default -> System.out.println("Invalid option!\n");
                            }

                            if (musicDAO.update(music) > 0) {
                                System.out.println("Success!\n");
                            } else {
                                System.out.println("Failed!\n");
                            }
                        }
                    }
                }

                case 6 -> {
                    System.out.println("-- DELETE A MUSIC --\n");

                    if (musList.isEmpty()) {
                        System.out.println("There is no music registered!\n");
                        musicMenu();
                    } else {
                        listAllMusics();

                        System.out.println("Inform the music's ISRC you wish to delete from the list above");
                        String isrc = scanner.nextLine();

                        if (musicDAO.hasArtistAssociation(isrc)) {
                            System.out.println("A music cannot have any artist associated to be deleted!\n");
                        } else {
                            if (musicDAO.delete(isrc) > 0) {
                                System.out.println("Successfully deleted!\n");
                            } else {
                                System.out.println("Failed to delete!\n");
                            }
                        }
                    }
                }

                default -> System.out.println("Invalid option!\n");
            }
        }
    }

    private void distributorMenu() {
        boolean closeMenu = false;

        while (!closeMenu) {
            System.out.println("-- ARTIST MENU --\n");
            System.out.println("0 - Return to main menu");
            System.out.println("1 - Register a distributor");
            System.out.println("2 - List all distributors");
            System.out.println("3 - Search a distributor by its CID");
            System.out.println("4 - Search distributors by their music");
            System.out.println("5 - Update a distributor");
            System.out.println("6 - Delete a distributor");

            System.out.println("Pick an option from above:");
            int option = Integer.parseInt(scanner.nextLine());

            disList = distributorDAO.list();

            switch (option) {
                case 0 -> mainMenu();

                case 1 -> {
                    System.out.println("-- DISTRIBUTOR REGISTRATION --\n");

                    System.out.println("Inform the distributor CID (format C0123456):");
                    String disCID = scanner.nextLine();

                    System.out.println("Inform the distributor's trade name:");
                    String disName = scanner.nextLine();

                    System.out.println("Inform the distributor price:");
                    Double disPrice = Double.parseDouble(scanner.nextLine());

                    System.out.println("Inform the streaming service(s) the distributor works with:");
                    String disService = scanner.nextLine();

                    Distributor distributor = new Distributor(disCID, disName, disPrice, disService);

                    if (distributorDAO.insert(distributor) > 0) {
                        System.out.println("Success! " + disName + " is registered.\n");
                    } else {
                        System.out.println("Failed!\n");
                    }
                }

                case 2 -> {
                    System.out.println("-- LIST ALL DISTRIBUTORS --\n");

                    if (disList.isEmpty()) {
                        System.out.println("There is no distributor registered!\n");
                        distributorMenu();
                    } else {
                        listAllDistributors();
                    }
                }

                case 3 -> {
                    System.out.println("-- SEARCH A DISTRIBUTOR BY ITS CID --\n");

                    if (disList.isEmpty()) {
                        System.out.println("There is no distributor registered!\n");
                        distributorMenu();
                    } else {
                        System.out.println("Inform the distributors' CID you wish to find:");
                        String cid = scanner.nextLine();

                        Distributor distributor = distributorDAO.read(cid);

                        if (distributor != null) {
                            System.out.println("Distributor found, below you will see all its info:");
                            System.out.println("Name: " + distributor.getTradeName());
                            System.out.println("Price: " + distributor.getPrice());
                            System.out.println("Streaming services: " + distributor.getStreamingService());
                            System.out.println("---\n");

                        } else {
                            System.out.println("Couldn't find any distributor with the provided cid " + cid);
                        }
                    }
                }

                case 5 -> {
                    System.out.println("-- UPDATE A DISTRIBUTOR --\n");

                    if (disList.isEmpty()) {
                        System.out.println("There is no distributor registered!\n");
                        distributorMenu();
                    } else {
                        listAllDistributors();

                        System.out.println("Pick a distributor by his CID from the list above:");
                        String cidDis = scanner.nextLine();

                        Distributor distributor = distributorDAO.read(cidDis);

                        if (cidDis == null) {
                            System.out.println("Couldn't find any distributor with the provided CID: " + cidDis);
                        } else {
                            System.out.println("Which information would you like to change?");
                            System.out.println("0 - Return to distributor menu");
                            System.out.println("1 - Trade name");
                            System.out.println("2 - Price");
                            System.out.println("3 - Streaming service");

                            int infOpt = Integer.parseInt(scanner.nextLine());

                            switch (infOpt) {
                                case 0 -> distributorMenu();

                                case 1 -> {
                                    System.out.println("Inform a new trade name for the distributor:");
                                    String disName = scanner.nextLine();
                                    distributor.setTradeName(disName);
                                }

                                case 2 -> {
                                    System.out.println("Select a new price for the distributor:");
                                    Double disPrice = Double.parseDouble(scanner.nextLine());
                                    distributor.setPrice(disPrice);
                                }

                                case 3 -> {
                                    System.out.println("Inform the new streaming service(s) for the distributor:");
                                    String disService = scanner.nextLine();
                                    distributor.setStreamingService(disService);
                                }
                                default -> System.out.println("Invalid option!\n");
                            }

                            if (distributorDAO.update(distributor) > 0) {
                                System.out.println("Success!\n");
                            } else {
                                System.out.println("Failed!\n");
                            }
                        }
                    }
                }

                case 6 -> {
                    System.out.println("-- DELETE A DISTRIBUTOR --\n");

                    if (disList.isEmpty()) {
                        System.out.println("There is no distributor registered!\n");
                        distributorMenu();
                    } else {
                        listAllDistributors();

                        System.out.println("Inform the distributor's CID you wish to delete from the list above");
                        String cid = scanner.nextLine();

                        if (distributorDAO.hasMusicDistributed(cid)) {
                            System.out.println("A distributor cannot have any music being distributed to be deleted!\n");
                        } else {
                            if (distributorDAO.delete(cid) > 0) {
                                System.out.println("Successfully deleted!\n");
                            } else {
                                System.out.println("Failed to delete!\n");
                            }
                        }
                    }
                }

                default -> System.out.println("Invalid option!\n");
            }
        }
    }

    private void servicesMenu() {
        boolean closeMenu = false;

        while (!closeMenu) {
            System.out.println("-- SERVICES MENU --\n");
            System.out.println("0 - Return to main menu");
            System.out.println("1 - Link an artist to a music");
            System.out.println("2 - Unlink an artist to a music");
            System.out.println("3 - List all artist-music associations");
            System.out.println("4 - Start a distribution");
            System.out.println("5 - Stop a distribution");
            System.out.println("6 - List all distributions");

            System.out.println("Pick an option from above:");
            int option = Integer.parseInt(scanner.nextLine());

            artList = artistDAO.list();
            musList = musicDAO.list();
            disList = distributorDAO.list();
            artMusList = artistMusicDAO.list();
            musDisList = musicDistributorDAO.list();

            switch (option) {
                case 0 -> mainMenu();

                case 1 -> {
                    System.out.println("-- LINK AN ARTIST TO A MUSIC --\n");

                    if (artList.isEmpty() || musList.isEmpty()) {
                        System.out.println("You must have at least an artist or music registered!\n");
                    } else {
                        listAllArtists();
                        System.out.println("Pick an artist by his ID from the list above:");
                        int artID = Integer.parseInt(scanner.nextLine());

                        listAllMusics();
                        System.out.println("Pick a music by its ISRC from the list above:");
                        String musISRC = scanner.nextLine();

                        int rowCount = artistMusicDAO.linkArtistToMusic(artID, musISRC);

                        if (rowCount > 0) {
                            System.out.println("Success! Artist " + artistDAO.getArtistByID(artID).getName() +
                                    " is associated with the music " + musicDAO.getMusicByISRC(musISRC).getName());
                        } else {
                            System.out.println("Failed to link the artist to a music!\n");
                        }
                    }
                }

                case 2 -> {
                    System.out.println("-- UNLINK AN ARTIST TO A MUSIC --\n");

                    listAllArtists();
                    System.out.println("Pick an artist by his ID from the list above:");
                    int id = Integer.parseInt(scanner.nextLine());

                    listAllMusics();
                    System.out.println("Pick a music by its ISRC from the list above");
                    String isrc = scanner.nextLine();

                    if (artistMusicDAO.isArtistLinkedToMusic(id, isrc)) {
                        artistMusicDAO.unlinkArtistToMusic(id, isrc);
                        System.out.println("Success! Artist" + artistDAO.getArtistByID(id).getName() +
                                " is no longer associated with the music " + musicDAO.getMusicByISRC(isrc));
                    } else {
                        System.out.println("The association between the artist and the music doesn't exist!\n");
                    }
                }

                case 3 -> {
                    System.out.println("-- LIST ARTIST-MUSIC ASSOCIATIONS --\n");

                    if (artMusList.isEmpty()) {
                        System.out.println("There is no artist-music associations!\n");
                    } else {
                        listArtistMusicAssociations();
                    }
                }

                case 4 -> {
                    System.out.println("-- START A DISTRIBUTION --\n");

                    if (musList.isEmpty() || disList.isEmpty()) {
                        System.out.println("You must have at least one music and distributor registered!\n");
                    } else {
                        listAllMusics();
                        System.out.println("Pick a music by its ISRC from the list above:");
                        String musISRC = scanner.nextLine();

                        listAllDistributors();
                        System.out.println("Pick a distributor by its CID from the list above:");
                        String disCID = scanner.nextLine();

                        int rowCount = musicDistributorDAO.startDistribution(musISRC, disCID);

                        if (rowCount > 0) {
                            System.out.println("Success! Music " + musicDAO.getMusicByISRC(musISRC).getName() +
                                    " is being distributed by " + distributorDAO.getDistributorByCID(disCID).getTradeName());
                        } else {
                            System.out.println("Failed to start distribution!\n");
                        }
                    }
                }

                case 5 -> {
                    System.out.println("-- STOP A DISTRIBUTION --\n");

                    listAllMusics();
                    System.out.println("Pick a music by its ISRC from the list above:");
                    String isrc = scanner.nextLine();

                    listAllDistributors();
                    System.out.println("Pick a distributor by its CID from the list above");
                    String cid = scanner.nextLine();

                    Distributor distributor = distributorDAO.getDistributorByCID(cid);

                    if (musicDistributorDAO.isMusicDistributed(isrc, cid)) {
                        musicDistributorDAO.stopDistribution(isrc, cid);
                        System.out.println("Success! Music " + musicDAO.getMusicByISRC(isrc).getName() +
                                " is no longer being distributed by " + distributor.getTradeName());
                    } else {
                        System.out.println("The distribution doesn't exist!\n");
                    }
                }

                case 6 -> {
                    System.out.println("-- LIST ALL DISTRIBUTIONS --\n");

                    if (musDisList.isEmpty()) {
                        System.out.println("There is no distribution!\n");
                    } else {
                        listAllDistributions();
                    }
                }

                default -> System.out.println("Invalid option!\n");
            }
        }
    }

    private Role findRoleById(int roleId) {
        for (Role role : Role.values()) {
            if (role.getID() == roleId) {
                return role;
            }
        }
        return null;
    }

    private Genre findGenreById(int genreId) {
        for (Genre genre : Genre.values()) {
            if (genre.getID() == genreId) {
                return genre;
            }
        }
        return null;
    }

    private void listAllArtists() {
        for (Artist artist : artList) {
            System.out.println("ID: " + artist.getID());
            System.out.println("Name: " + artist.getName());
            System.out.println("Country: " + artist.getCountry());
            System.out.println("Role: " + artist.getRole());
            System.out.println("Social network: " + artist.getSocialNetwork());
            System.out.println("---\n");
        }
    }

    private void listAllMusics() {
        for (Music music : musList) {
            System.out.println("ISRC: " + music.getISRC());
            System.out.println("Name: " + music.getName());
            System.out.println("Genre: " + music.getGenre());
            System.out.println("Duration: " + music.getDuration());
            System.out.println("Release date: " + music.getReleaseDate());
            System.out.println("---\n");
        }
    }

    private void listAllDistributors() {
        for (Distributor distributor : disList) {
            System.out.println("CID: " + distributor.getCID());
            System.out.println("Trade name: " + distributor.getTradeName());
            System.out.println("Price: " + distributor.getPrice());
            System.out.println("Streaming service: " + distributor.getStreamingService());
            System.out.println("---\n");
        }
    }

    private void listArtistMusicAssociations() {
        for (ArtistMusic artistMusic : artMusList) {
            int artID = artistMusic.getArtistID();
            String musISRC = artistMusic.getMusicISRC();

            Artist artist = artistDAO.getArtistByID(artID);
            Music music = musicDAO.getMusicByISRC(musISRC);

            if (artist != null && music != null) {
                System.out.println("Artist: " + artist.getName());
                System.out.println("Role: " + artist.getRole());
                System.out.println("Song: " + music.getName());
                System.out.println("Genre: " + music.getGenre());
                System.out.println("---\n");
            }
        }
    }

    private void listAllDistributions() {
        for (MusicDistributor musicDistributor : musDisList) {
            String musISRC = musicDistributor.getMusicISRC();
            String disCID = musicDistributor.getDistributorCID();

            Music music = musicDAO.getMusicByISRC(musISRC);
            Distributor distributor = distributorDAO.getDistributorByCID(disCID);

            if (music != null && distributor != null) {
                System.out.println("Music: " + music.getName());
                System.out.println("Genre: " + music.getGenre());
                System.out.println("Duration: " + music.getDuration());
                System.out.println("Release date: " + music.getReleaseDate());
                System.out.println("Distributor: " + distributor.getTradeName());
                System.out.println("Price per distribution: " + distributor.getPrice());
                System.out.println("Streaming service: " + distributor.getStreamingService());
                System.out.println("---\n");
            }
        }
    }
}
