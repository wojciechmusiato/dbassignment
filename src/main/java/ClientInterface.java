
import java.util.*;


public class ClientInterface {

    private boolean isLoggedIn = false;
    private User currentlyLoggedUser;
    private DBOperations dbo = new DBOperations();

    public ClientInterface() {
    }

    public void startClient() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type 'help' for help anytime");
        while (scanner.hasNext()) {
            String input = scanner.next();
            if (input.equalsIgnoreCase(Commands.EXIT)) {
                break;
            }
            if (input.equals(Commands.HELP)) {
                System.out.println("available commands:");
                System.out.println("register, exit, login");
            }

            if (!isLoggedIn) {
                scanner.nextLine();
                switch (input) {
                    case Commands.REGISTER:
                        register(scanner);
                        break;
                    case Commands.LOGIN:
                        login(scanner);
                        break;
                }
            }
            //zalogowany
            else {
                scanner.nextLine();
                if (currentlyLoggedUser.getType().equalsIgnoreCase(String.valueOf(Role.USER))) {
                    userInterface(scanner, input);
                } else if (currentlyLoggedUser.getType().equalsIgnoreCase(String.valueOf(Role.ADMIN))) {
                    adminInterface(scanner, input);
                } else {
                    editorInterface(scanner, input);
                }
            }
        }
    }

    private void adminInterface(Scanner scanner, String input) throws Exception {
        if (input.equals(Commands.HELP)) {
            System.out.println(Commands.getAllCommands());
        }

        switch (input) {
            case Commands.LOGOUT:
                logout();
                break;
            case Commands.INSERT_ARTIST_CONCERT:
                insertArtistToConcert(scanner);
                break;
            case Commands.GET_SHARED_SONGS:
                dbo.getSharedSongs(currentlyLoggedUser.getId());
                break;
            case Commands.BROWSE_LINEUP:
                getLineup(scanner);
                break;
            case Commands.SHOW_PLAYLIST_SONGS:
                getPlaylistsSongs(scanner);
                break;
            case Commands.BROWSE_ALL_PUBLISHERS:
                dbo.getAllPublishers();
                break;
            case Commands.BROWSE_ALL_CONCERTS:
                dbo.getAllConcerts();
                break;
            case Commands.INSERT_PUBLISHER:
                insertPublisher(scanner);
                break;
            case Commands.INSERT_CONCERT:
                insertConcert(scanner);
                break;
            case Commands.INSERT_IND_ARTIST:
                insertIndividualArtist(scanner);
                break;
            case Commands.INSERT_GROUP_ARTIST:
                insertGroupArtist(scanner);
                break;
            case Commands.DELETE_CONCERT:
                deleteConcert(scanner);
                break;
            case Commands.DELETE_REVIEW:
                deleteReview(scanner);
                break;
            case Commands.INSERT_REVIEW:
                insertReview(scanner);
                break;
            case Commands.SHOW_MY_REVIEWS:
                dbo.showMyReviews(currentlyLoggedUser.getId());
                break;
            case Commands.SHOW_REVIEWS:
                showReviews(scanner);
                break;
            case Commands.BROWSE_ALL_PLAYLISTS:
                dbo.getAllPlaylists();
                break;
            case Commands.ADD_TO_PLAYLIST:
                addToPlaylist(scanner);
                break;
            case Commands.DELETE_PLAYLIST:
                deletePlaylist(scanner);
                break;
            case Commands.INSERT_PLAYLIST:
                createPlaylist(scanner);
                break;
            case Commands.SEND_SONG:
                sendSong(scanner);
                break;
            case Commands.DELETE_USER_FROM_GROUP:
                deleteUserGroup(scanner);
                break;
            case Commands.CREATE_GROUP:
                createGroup(scanner);
                break;
            case Commands.ADD_USER_TO_GROUP:
                addUserToGroup(scanner);
                break;
            case Commands.BROWSE_ALL_SONGS:
                dbo.getAllSongs();
                break;
            case Commands.BROWSE_ALL_ALBUMS:
                dbo.getAllAlbums();
                break;
            case Commands.BROWSE_ALL_ARTISTS:
                dbo.getAllArtists();
                break;
            case Commands.SHARE_SONG:
                shareSong(scanner);
                break;
            case Commands.INSERT_SONGS:
                insertSong(scanner);
                break;
            case Commands.CHANGE_SONG:
                changeSong(scanner);
                break;
            case Commands.SET_BREAKUP_DATE:
                setBreakUpDateOfBand(scanner);
                break;
            case Commands.CHANGE_ALBUM:
                changeAlbum(scanner);
                break;
            case Commands.CHANGE_ARTIST:
                changeArtist(scanner);
                break;
            case Commands.DELETE_SONG:
                deleteSong(scanner);
                break;
            case Commands.DELETE_ALBUM:
                deleteAlbum(scanner);
                break;
            case Commands.DELETE_ARTIST:
                deleteArtist(scanner);
                break;
            case Commands.SEARCH_MUSIC:
                searchMusic(scanner);
                break;
            case Commands.GIVE_PRIVILAGE:
                promoteUser(scanner);
                break;
            case Commands.INSERT_ALBUM:
                insertAlbum(scanner);
                break;
            case Commands.DELETE_USER:
                deleteUser(scanner);
                break;
            case Commands.GET_ALL_USERS:
                dbo.getAllUsers();
                break;
            case Commands.HELP:
                System.out.println(Commands.getAllCommands());
                break;
            default:
                System.err.println("Wrong command!");
                break;

        }
    }

    private void editorInterface(Scanner scanner, String input) throws Exception {
        if (input.equals(Commands.HELP)) {
            System.out.println(Commands.getEditorCommands());
        }
        switch (input) {
            case Commands.LOGOUT:
                logout();
                break;
            case Commands.INSERT_ARTIST_CONCERT:
                insertArtistToConcert(scanner);
                break;
            case Commands.GET_SHARED_SONGS:
                dbo.getSharedSongs(currentlyLoggedUser.getId());
                break;
            case Commands.BROWSE_LINEUP:
                getLineup(scanner);
                break;
            case Commands.SHOW_PLAYLIST_SONGS:
                getPlaylistsSongs(scanner);
                break;
            case Commands.BROWSE_ALL_PUBLISHERS:
                dbo.getAllPublishers();
                break;
            case Commands.BROWSE_ALL_CONCERTS:
                dbo.getAllConcerts();
                break;
            case Commands.INSERT_PUBLISHER:
                insertPublisher(scanner);
                break;
            case Commands.INSERT_CONCERT:
                insertConcert(scanner);
                break;
            case Commands.DELETE_CONCERT:
                deleteConcert(scanner);
                break;
            case Commands.DELETE_REVIEW:
                deleteReview(scanner);
                break;
            case Commands.INSERT_REVIEW:
                insertReview(scanner);
                break;
            case Commands.SHOW_MY_REVIEWS:
                dbo.showMyReviews(currentlyLoggedUser.getId());
                break;
            case Commands.SHOW_REVIEWS:
                showReviews(scanner);
                break;
            case Commands.BROWSE_ALL_PLAYLISTS:
                dbo.getAllPlaylists();
                break;
            case Commands.ADD_TO_PLAYLIST:
                addToPlaylist(scanner);
                break;
            case Commands.DELETE_PLAYLIST:
                deletePlaylist(scanner);
                break;
            case Commands.INSERT_PLAYLIST:
                createPlaylist(scanner);
                break;
            case Commands.SEND_SONG:
                sendSong(scanner);
                break;
            case Commands.DELETE_USER_FROM_GROUP:
                deleteUserGroup(scanner);
                break;
            case Commands.CREATE_GROUP:
                createGroup(scanner);
                break;
            case Commands.ADD_USER_TO_GROUP:
                addUserToGroup(scanner);
                break;
            case Commands.BROWSE_ALL_SONGS:
                dbo.getAllSongs();
                break;
            case Commands.BROWSE_ALL_ALBUMS:
                dbo.getAllAlbums();
                break;
            case Commands.BROWSE_ALL_ARTISTS:
                dbo.getAllArtists();
                break;
            case Commands.SHARE_SONG:
                shareSong(scanner);
                break;
            case Commands.INSERT_SONGS:
                insertSong(scanner);
                break;
            case Commands.CHANGE_SONG:
                changeSong(scanner);
                break;
            case Commands.SET_BREAKUP_DATE:
                setBreakUpDateOfBand(scanner);
                break;
            case Commands.CHANGE_ALBUM:
                changeAlbum(scanner);
                break;
            case Commands.CHANGE_ARTIST:
                changeArtist(scanner);
                break;
            case Commands.DELETE_SONG:
                deleteSong(scanner);
                break;
            case Commands.DELETE_ALBUM:
                deleteAlbum(scanner);
                break;
            case Commands.DELETE_ARTIST:
                deleteArtist(scanner);
                break;
            case Commands.SEARCH_MUSIC:
                searchMusic(scanner);
                break;
            case Commands.INSERT_ALBUM:
                insertAlbum(scanner);
                break;
            case Commands.GET_ALL_USERS:
                dbo.getAllUsers();
                break;
            case Commands.HELP:
                System.out.println(Commands.getAllCommands());
                break;
            default:
                System.err.println("Wrong command!");
                break;

        }
    }

    private void userInterface(Scanner scanner, String input) {
        if (input.equals(Commands.HELP)) {
            System.out.println(Commands.getUserCommands());
        }
        userCases(scanner, input);
    }

    private void userCases(Scanner scanner, String input) {
        switch (input) {
            case Commands.LOGOUT:
                logout();
                break;
            case Commands.BROWSE_LINEUP:
                getLineup(scanner);
                break;
            case Commands.SHOW_PLAYLIST_SONGS:
                getPlaylistsSongs(scanner);
                break;
            case Commands.GET_SHARED_SONGS:
                dbo.getSharedSongs(currentlyLoggedUser.getId());
                break;
            case Commands.BROWSE_ALL_PUBLISHERS:
                dbo.getAllPublishers();
                break;
            case Commands.BROWSE_ALL_CONCERTS:
                dbo.getAllConcerts();
                break;
            case Commands.DELETE_REVIEW:
                deleteReview(scanner);
                break;
            case Commands.INSERT_REVIEW:
                insertReview(scanner);
                break;
            case Commands.SHOW_MY_REVIEWS:
                dbo.showMyReviews(currentlyLoggedUser.getId());
                break;
            case Commands.SHOW_REVIEWS:
                showReviews(scanner);
                break;
            case Commands.BROWSE_ALL_PLAYLISTS:
                dbo.getAllPlaylists();
                break;
            case Commands.ADD_TO_PLAYLIST:
                addToPlaylist(scanner);
                break;
            case Commands.DELETE_PLAYLIST:
                deletePlaylist(scanner);
                break;
            case Commands.INSERT_PLAYLIST:
                createPlaylist(scanner);
                break;
            case Commands.SEND_SONG:
                sendSong(scanner);
                break;
            case Commands.DELETE_USER_FROM_GROUP:
                deleteUserGroup(scanner);
                break;
            case Commands.CREATE_GROUP:
                createGroup(scanner);
                break;
            case Commands.ADD_USER_TO_GROUP:
                addUserToGroup(scanner);
                break;
            case Commands.BROWSE_ALL_SONGS:
                dbo.getAllSongs();
                break;
            case Commands.BROWSE_ALL_ALBUMS:
                dbo.getAllAlbums();
                break;
            case Commands.BROWSE_ALL_ARTISTS:
                dbo.getAllArtists();
                break;
            case Commands.SHARE_SONG:
                shareSong(scanner);
                break;
            case Commands.SEARCH_MUSIC:
                searchMusic(scanner);
                break;
            case Commands.GET_ALL_USERS:
                dbo.getAllUsers();
                break;
            case Commands.HELP:
                System.out.println(Commands.getAllCommands());
                break;
            default:
                System.err.println("Wrong command!");
                break;

        }
    }

    private void addUserToGroup(Scanner scanner) {
        System.out.println("Enter username of user you wanna add:");
        String user = scanner.nextLine();
        dbo.getAllGroups();
        System.out.println("Enter group id you want to add the username to");
        String id = scanner.nextLine();

        if (!dbo.checkIfExists("users", "username", user) || !dbo.checkIfExists("groops", "id", id)) {
            System.out.println("User or group doesn't exist");
        } else {
            User u = dbo.getOneUser(user);
            System.out.println("Make user a group editor? (Y/whatever else)");
            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                if (dbo.insertUserToGroup(Integer.parseInt(id), u.getId(), 0, currentlyLoggedUser.getId())) {
                    System.out.println("Added user to group.");
                } else {
                    System.out.println("sth wrnt wrong");
                }
            } else {
                if (dbo.insertUserToGroup(Integer.parseInt(id), u.getId(), 1, currentlyLoggedUser.getId())) {
                    System.out.println("Added user to group.");
                } else {
                    System.out.println("sth wrnt wrong");
                }
            }
        }
    }


    private void createGroup(Scanner scanner) {
        System.out.println("Enter new group name:");
        String name = scanner.nextLine();
        if (dbo.checkIfExists("groops", "name", name)) {
            System.out.println("name exists");
        } else {
            dbo.insertGroup(name, currentlyLoggedUser.getId());
        }
    }


    private void getLineup(Scanner scanner) {
        dbo.getAllConcerts();
        System.out.println("Enter ID of concert:");
        String conid = scanner.nextLine();
        if (!dbo.checkIfExists("concerts", "id", conid)) {
            System.out.println("Wrong data entered.");
            return;
        } else {
            dbo.getLineup(Integer.parseInt(conid));
        }
    }

    private void getPlaylistsSongs(Scanner scanner){
        dbo.showPlaylists(currentlyLoggedUser);
        System.out.println("enter id of playlist you want to see");
        String id = scanner.nextLine();
        if(!dbo.checkIfExists("playlists", "id", id)){
            System.out.println("wrong data entered");
        }
        else {
            dbo.getPlaylistSongs(currentlyLoggedUser, id);
        }

    }

    private void insertArtistToConcert(Scanner scanner) {
        dbo.getAllConcerts();
        System.out.println("Enter ID of concert:");
        String conid = scanner.nextLine();
        dbo.getAllArtists();
        System.out.println("Enter ID of artist:");
        String arid = scanner.nextLine();
        if (!dbo.checkIfExists("concerts", "id", conid) ||
                !dbo.checkIfExists("artists", "id", arid)) {
            System.out.println("Wrong data entered.");
            return;
        } else {
            if (dbo.insertArtistToConcert(Integer.parseInt(conid), Integer.parseInt(arid))) {
                System.out.println("Inserted.");
            } else {
                System.out.println("Something went wrong.");
            }
        }


    }

    private void deleteConcert(Scanner scanner) {
        dbo.getAllConcerts();
        System.out.println("Enter ID of concert to delete:");
        String conid = scanner.nextLine();
        if (dbo.checkIfExists("concerts", "id", conid)) {
            if (dbo.deleteConcert(Integer.parseInt(conid))) {
                System.out.println("Deleted concert");
            } else {
                System.out.println("Something went wrong.");
            }
        } else {
            System.out.println("Wrong data entered.");
        }
    }

    private void insertConcert(Scanner scanner) {
        System.out.println("Enter name for concert:");
        String name = scanner.nextLine();
        System.out.println("Enter start date for concert (YYYY-MM-DD):");
        String start = scanner.nextLine();
        System.out.println("Enter end date for concert (YYYY-MM-DD):");
        String end = scanner.nextLine();

        if (dbo.insertConcert(start, end, name)) {
            System.out.println("Created concert: " + name);
        } else {
            System.out.println("Something went wrong.");
        }
    }

    private void insertIndividualArtist(Scanner scanner) {
        System.out.println("Enter artist name:");
        String name = scanner.nextLine();
        System.out.println("Enter full musician name:");
        String musicianName = scanner.nextLine();
        if (dbo.insertIndividualArtist(name, musicianName)) {
            System.out.println("Created individual artist: " + name);
        } else {
            System.out.println("Something went wrong.");
        }
    }

    private void insertGroupArtist(Scanner scanner) {
        System.out.println("Enter artist name:");
        String name = scanner.nextLine();
        System.out.println("Enter date of creation the band (yyyy-MM-dd)");
        String date = scanner.nextLine();
        if(dbo.insertGroupArtist(name, date)){
            System.out.println("Created the band: " + name);
        }
        else{
            System.out.println("Something went wrong");
        }
    }

    private void insertPublisher(Scanner scanner) {
        System.out.println("Enter name for publisher:");
        String pubid = scanner.nextLine();
        if (dbo.insertPublisher(pubid)) {
            System.out.println("Created publisher: " + pubid);
        } else {
            System.out.println("Something went wrong.");
        }
    }


    private void deleteReview(Scanner scanner) {
        dbo.showMyReviews(currentlyLoggedUser.getId());
        System.out.println("Pick review  ID you wanna delete");
        String revid = scanner.nextLine();
        if (dbo.checkIfExists("reviews", "id", revid)) {
            if (dbo.deleteReview(Integer.parseInt(revid), currentlyLoggedUser.getId())) {
                System.out.println("Deleted review");
            } else {
                System.out.println("Something went wrong.");
            }
        } else {
            System.out.println("Wrong data entered.");
        }
    }

    private void showReviews(Scanner scanner) {
        dbo.getAllAlbums();
        System.out.println("Enter id of album you want to see reviews of:");
        String albumid = scanner.nextLine();
        if (dbo.checkIfExists("albums", "id", albumid)) {
            dbo.getAlbumReviews(Integer.parseInt(albumid));
        }
    }

    private void insertReview(Scanner scanner) {
        System.out.println("Pick album ID for which you want to write a review:");
        dbo.getAllAlbums();
        String albumid = scanner.nextLine();
        if (dbo.checkIfExists("albums", "id", albumid)) {
            System.out.println("Enter review:");
            String review = scanner.nextLine();
            if (dbo.insertReview(Integer.parseInt(albumid), review, currentlyLoggedUser.getId())) {
                System.out.println("Created review:" + review);
            } else {
                System.out.println("Something went wrong.");
            }
        } else {
            System.out.println("Wrong data entered");
        }
    }

    private void createPlaylist(Scanner scanner) {
        System.out.println("Insert new name for a playlist:");
        String playlist = scanner.nextLine();
        if (dbo.insertPlaylist(playlist, currentlyLoggedUser.getId())) {
            System.out.println("Created playlist:" + playlist);
        } else {
            System.out.println("Something went wrong.");
        }
    }

    private void deletePlaylist(Scanner scanner) {
        dbo.getAllPlaylists();
        System.out.println("Enter playlist ID to delete:");
        String id = scanner.nextLine();
        if (dbo.checkIfExists("playlists", "id", id)) {
            if (dbo.deletePlaylist(Integer.parseInt(id), currentlyLoggedUser.getId())) {
                System.out.println("Deleted playlist: " + id);
            } else {
                System.out.println("Something went wrong.");
            }
        } else {
            System.out.println("Wrong ID entered.");
        }
    }

    private void addToPlaylist(Scanner scanner) {
        dbo.getUserPlaylists(currentlyLoggedUser.getId());
        System.out.println("Enter a playlist id to put songs in:");
        String idplay = scanner.nextLine();
        dbo.getAllSongs();
        System.out.println("Enter song's ID to put in the playlist:");
        String idsong = scanner.nextLine();
        if (!dbo.checkIfExists("playlists", "id", idplay) || !dbo.checkIfExists("songs", "id", idsong)) {
            System.out.println("Wrong data entered");
        } else {
            if (dbo.addSongToPlaylist(Integer.parseInt(idplay), Integer.parseInt(idsong), currentlyLoggedUser.getId())) {
                System.out.println("Inserted song to playlist.");
            } else {
                System.out.println("Something went wrong.");
            }
        }
    }

    private void sendSong(Scanner scanner) {
        dbo.getAllSongs();
        System.out.println("Enter id of song which song you want to upload:");
        String songid = scanner.nextLine();
        System.out.println("Enter name of the file:");
        String filename = scanner.nextLine();
        if (dbo.checkIfExists("songfiles", "filename", filename)) {
            System.out.println("File with given name already exists...");
        } else {
            if (dbo.insertFile(Integer.parseInt(songid), filename, currentlyLoggedUser.getId())) {
                System.out.println("File uploaded!");
            } else {
                System.out.println("Something went wrong.");
            }
        }
    }


    private void deleteUserGroup(Scanner scanner) {
        dbo.getAllUsers();
        System.out.println("Enter username ID you want to remove from group:");
        String user = scanner.nextLine();
        dbo.getAllGroups();
        System.out.println("Enter group id you want to delete from");
        String id = scanner.nextLine();
        if (!dbo.checkIfExists("users", "id", user) || !dbo.checkIfExists("groops", "id", id)) {
            System.out.println("User or group doesn't exist");
        } else {
            User u = dbo.getOneUser(user);
            if (dbo.deleteUserFromGroup(Integer.parseInt(id), u.getId(), currentlyLoggedUser.getId())) {
                System.out.println("Deleted user from group.");
            } else {
                System.out.println("sth wrnt wrong");
            }

        }
    }


    private void searchMusic(Scanner scanner) {
        System.out.println("Enter phrase to search:");
        String phrase = scanner.nextLine();
        System.out.println("Results:");
        dbo.searchMusic(phrase);
    }

    private void shareSong(Scanner scanner) {

        dbo.getSharedSongs(currentlyLoggedUser.getId());
        System.out.println("Enter name of the file:");
        String filename = scanner.nextLine();
        if (!dbo.checkIfExists("songfiles", "filename", filename)) {
            System.out.println("File with given name doesn't exist...");
        } else {
            System.out.println("Who do you want to share this file with? (group/user)");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("group")) {
                dbo.getAllGroups();
                System.out.println("Pick group ID to share:");
                int groupid = Integer.parseInt(scanner.nextLine());
                if (dbo.shareSong(groupid, filename, 0, currentlyLoggedUser.getId())) {
                    System.out.println("Song shared with this group");
                    return;
                }
            } else if (input.equalsIgnoreCase("user")) {
                dbo.getAllUsers();
                System.out.println("Pick user ID to share:");
                int userid = Integer.parseInt(scanner.nextLine());
                if (dbo.shareSong(userid, filename, 1, currentlyLoggedUser.getId())) {
                    System.out.println("Song shared with this user");
                    return;
                }
            } else {
            }
        }
        System.out.println("Sth went wrong");
    }

    private void deleteArtist(Scanner scanner) {
        System.out.println("insert name of artist you would like to delete:");
        dbo.getAllArtists();
        String name = scanner.nextLine();
        if (dbo.deleteArtist(name)) {
            System.out.println("Artist deleted");
        } else {
            System.out.println("Wrong name entered");
        }
    }

    private void insertAlbum(Scanner scanner) {
        System.out.println("name:");
        String name = scanner.nextLine();
        System.out.println("genre:");
        String gen = scanner.nextLine();
        System.out.println("description:");
        String des = scanner.nextLine();
        dbo.getAllArtists();
        System.out.println("artist ID:");
        String arId = scanner.nextLine();
        dbo.getAllPublishers();
        System.out.println("artist ID:");
        String pubId = scanner.nextLine();
        if (!dbo.checkIfExists("artists", "id", arId) ||
                !dbo.checkIfExists("publisher", "id", pubId)) {
            System.out.println("User or group doesn't exist");
        } else {
            if (dbo.insertAlbum(name, gen, des, Integer.parseInt(pubId), Integer.parseInt(arId))) {
                System.out.println("Album entered.");
            } else {
                System.out.println("Sth went wrong...");
            }
        }

    }


    private void deleteAlbum(Scanner scanner) {
        dbo.getAllAlbums();
        System.out.println("Insert ID of album you would like to delete:");
        String idalbum = scanner.nextLine();
        if (dbo.checkIfExists("songs", "id", idalbum)) {
            if (dbo.deleteAlbum(Integer.parseInt(idalbum))) {
                System.out.println("Album deleted.");
            } else {
                System.out.println("Something went wrong.");
            }
        } else {
            System.out.println("wrong data entered.");
        }
    }

    private void deleteSong(Scanner scanner) {
        dbo.getAllSongs();
        System.out.println("Insert ID of song you would like to delete:");
        String idsong = scanner.nextLine();
        if (dbo.checkIfExists("songs", "id", idsong)) {
            if (dbo.deleteSong(Integer.parseInt(idsong))) {
                System.out.println("Song deleted.");
            } else {
                System.out.println("Something went wrong.");
            }
        } else {
            System.out.println("wrong data entered.");
        }

    }

    private void changeArtist(Scanner scanner) {
        //todo
    }

    private void changeAlbum(Scanner scanner) {
        System.out.println("insert name of album you would like to change:");
        String changeAlbumName = scanner.nextLine();
        System.out.println("new name:");
        String albumNewName = scanner.nextLine();
        System.out.println(dbo.updateAlbum(albumNewName, changeAlbumName));
    }

    private void changeSong(Scanner scanner) {
        dbo.getAllSongs();
        System.out.println("insert ID of song you would like to change:");
        String idsong = scanner.nextLine();
        System.out.println("new name:");
        String newname = scanner.nextLine();
        if (dbo.checkIfExists("songs", "id", idsong)) {
            if (dbo.updateSong(newname, Integer.parseInt(idsong))) {
                System.out.println("Song title changed.");
            } else {
                System.out.println("Sth went wrong...");
            }
        } else {
            System.out.println("song id doesnt exist");
        }
    }
    private void setBreakUpDateOfBand(Scanner scanner) {
        dbo.getAllArtists();
        System.out.println("insert ID of band you would like to change the break up date:");
        String id = scanner.nextLine();
        System.out.println("date of break up (yyyy-MM-dd):");
        String date = scanner.nextLine();
        if (dbo.checkIfExists("artists", "id", id)) {
            if (dbo.updateArtistBandBreakupDate(date, Integer.parseInt(id))) {
                System.out.println("breakup date added");
            } else {
                System.out.println("Sth went wrong...");
            }
        } else {
            System.out.println("song id doesnt exist");
        }
    }

    private void insertSong(Scanner scanner) {
        System.out.println("Pick album(s) to which you want to add songs:");
        dbo.getAllAlbums();
        System.out.println("id of album(s): (if multiple, add space and type another id)");
        String album = scanner.nextLine();
        String[] albums = album.split(" ");
        for (String x : albums) {
            if (!dbo.checkIfExists("albums", "id", x)) {
                System.out.println("Wrong album(s) entered.");
                return;
            }
        }
        while (true) {
            System.out.println("Insert song (insert exit to abort):");
            String name = scanner.nextLine();
            if (name.equals("exit")) {
                System.out.println("Aborted.");
                break;
            }
            dbo.insertSong(albums, name);
        }
    }


    private void logout() {
        isLoggedIn = false;
        currentlyLoggedUser = null;
        System.out.println("logged out.");
    }


    private void promoteUser(Scanner scanner) {
        System.out.println("type username you would like to promote:");
        String promotedUser = scanner.nextLine();
        System.out.println("type either admin, editor or user");
        String newRole = scanner.nextLine();
        if (newRole.equals("admin") || newRole.equals("editor") || newRole.equals("user")) {
            if (dbo.changeRoleOfAUser(promotedUser, newRole)) {
                System.out.println("User role changed to: " + newRole);
            } else {
                System.out.println("User probably does not exist.");
            }
        } else {
            System.out.println("Wrong role entered.");
        }

    }


    private void login(Scanner scanner) throws Exception {
        System.out.println("username: ");
        String username = scanner.nextLine();
        System.out.println("password: ");
        String password = scanner.nextLine();
        System.out.println("logging...");

        if (dbo.login(username, password)) {
            System.out.println("Logged in as " + username);
            currentlyLoggedUser = dbo.getOneUser(username);
            isLoggedIn = true;

        } else {
            System.out.println("Wrong credentials");
        }

    }


    private void register(Scanner scanner) throws InterruptedException {
        System.out.println("username: ");
        String username = scanner.nextLine();
        System.out.println("password: ");
        String password = scanner.nextLine();
        System.out.println("mail: ");
        String mail = scanner.nextLine();
        System.out.println("select role - write admin or user: ");
        String role = scanner.nextLine();
        // .register(username, password, email, Role.valueOf(role.toUpperCase()));
        System.out.println("registering...");
        Thread.sleep(500);
        System.out.println(dbo.insertUser(username, password, mail, role));
        //System.out.println( .register(username, password, role.toUpperCase()));
    }


    private void deleteUser(Scanner scanner) {

        System.out.println("which user you would like to delete?");

        String username = scanner.nextLine();
        if (dbo.deleteUser(username)) {
            System.out.println("User deleted");
        } else {
            System.out.println("User doesn't exist");
        }
    }
}


