
import java.util.Arrays;
import java.util.List;

/**
 * commands in command prompt
 */
public final class Commands {
    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String HELP = "help";
    public static final String EXIT = "exit";
    public static final String LOGOUT = "logout";
    public static final String BROWSE_ALL_SONGS = "getAllSongs";
    public static final String BROWSE_ALL_ARTISTS = "getAllArtists";
    public static final String BROWSE_ALL_ALBUMS = "getAllAlbums";
    public static final String INSERT_SONGS = "insertSong";
    public static final String INSERT_ARTIST = "insertArtist";//todo
    public static final String INSERT_ALBUM = "insertAlbum";
    public static final String CHANGE_SONG = "updateSong";
    public static final String CHANGE_ARTIST = "updateArtist";//todo
    public static final String CHANGE_ALBUM = "updateAlbum";//todo
    public static final String DELETE_SONG = "deleteSong";
    public static final String DELETE_ALBUM = "deleteAlbum";
    public static final String DELETE_ARTIST = "deleteArtist";
    public static final String GIVE_PRIVILAGE = "promote";
    public static final String GET_SHARED_SONGS = "getSharedSongs";
    public static final String SEND_SONG = "sendSong";
    public static final String DELETE_USER = "deleteUser";
    public static final String GET_ALL_USERS = "getAllUsers";
    public static final String GET_SONG = "getSong";
    public static final String SHARE_SONG = "shareSong";
    public static final String SEARCH_MUSIC = "searchMusic";
    public static final String UPLOAD_SONG = "uploadSong";
    public static final String DELETE_GROUP = "deleteGroup";
    public static final String CREATE_GROUP = "createGroup";
    public static final String ADD_USER_TO_GROUP = "addUserToGroup";
    public static final String DELETE_USER_FROM_GROUP = "deleteUserGroup";
    public static final String INSERT_PLAYLIST = "createPlaylist";
    public static final String ADD_TO_PLAYLIST = "addToPlaylist";
    public static final String DELETE_PLAYLIST = "deletePlaylist";
    public static final String BROWSE_ALL_PLAYLISTS = "getAllPlaylists";
    public static final String INSERT_REVIEW = "writeReview";
    public static final String DELETE_REVIEW = "deleteReview";
    public static final String SHOW_MY_REVIEWS = "showMyReviews";
    public static final String SHOW_REVIEWS = "showAllReviews";
    public static final String BROWSE_ALL_PUBLISHERS = "getAllPublishers";
    public static final String BROWSE_ALL_CONCERTS = "getAllConcerts";
    public static final String INSERT_PUBLISHER = "insertPublisher";
    public static final String DELETE_CONCERT = "deleteConcert";
    public static final String INSERT_CONCERT = "createConcert";
    public static final String INSERT_ARTIST_CONCERT = "makeLineup";
    public static final String BROWSE_LINEUP = "getLineup";

    public static List<String> getAllCommands() {
        return Arrays.asList(REGISTER, LOGIN, HELP, EXIT, LOGOUT,
                BROWSE_ALL_ALBUMS, BROWSE_ALL_ARTISTS, BROWSE_ALL_SONGS,
                BROWSE_ALL_PUBLISHERS, BROWSE_ALL_CONCERTS, BROWSE_LINEUP,
                BROWSE_ALL_PLAYLISTS, GET_ALL_USERS, GET_SHARED_SONGS,
                SHOW_REVIEWS, SHOW_MY_REVIEWS,

                INSERT_SONGS, INSERT_ALBUM, INSERT_ARTIST, INSERT_PLAYLIST,
                INSERT_REVIEW, INSERT_PUBLISHER, INSERT_CONCERT, INSERT_ARTIST_CONCERT,

                DELETE_REVIEW, DELETE_USER, DELETE_ARTIST, DELETE_ALBUM, DELETE_CONCERT, DELETE_SONG,
                DELETE_PLAYLIST, DELETE_GROUP,

                CHANGE_ALBUM, CHANGE_ARTIST, CHANGE_SONG,
                SHARE_SONG, ADD_TO_PLAYLIST,
                GIVE_PRIVILAGE, UPLOAD_SONG,

                SEND_SONG, GET_SONG);
    }

    public static List<String> getEditorCommands() {
        return Arrays.asList(REGISTER, LOGIN, HELP, EXIT, LOGOUT,
                BROWSE_ALL_ALBUMS, BROWSE_ALL_ARTISTS, BROWSE_ALL_SONGS,
                BROWSE_ALL_PUBLISHERS, BROWSE_ALL_CONCERTS, BROWSE_LINEUP,
                BROWSE_ALL_PLAYLISTS, GET_ALL_USERS, GET_SHARED_SONGS,
                SHOW_REVIEWS, SHOW_MY_REVIEWS,

                INSERT_SONGS, INSERT_ALBUM, INSERT_ARTIST, INSERT_PLAYLIST,
                INSERT_REVIEW, INSERT_PUBLISHER, INSERT_CONCERT, INSERT_ARTIST_CONCERT,

                DELETE_REVIEW, DELETE_ARTIST, DELETE_ALBUM, DELETE_CONCERT, DELETE_SONG,
                DELETE_PLAYLIST, DELETE_GROUP,

                CHANGE_ALBUM, CHANGE_ARTIST, CHANGE_SONG,
                SHARE_SONG, ADD_TO_PLAYLIST,
                UPLOAD_SONG,

                SEND_SONG, GET_SONG);
    }

    public static List<String> getUserCommands() {
        return Arrays.asList(REGISTER, LOGIN, HELP, EXIT, LOGOUT,
                BROWSE_ALL_ALBUMS, BROWSE_ALL_ARTISTS, BROWSE_ALL_SONGS,
                BROWSE_ALL_PUBLISHERS, BROWSE_ALL_CONCERTS, BROWSE_LINEUP,
                BROWSE_ALL_PLAYLISTS, GET_ALL_USERS, GET_SHARED_SONGS,
                SHOW_REVIEWS, SHOW_MY_REVIEWS,

                INSERT_PLAYLIST,
                INSERT_REVIEW,

                DELETE_REVIEW,
                DELETE_PLAYLIST,


                SHARE_SONG, ADD_TO_PLAYLIST,
                UPLOAD_SONG,

                SEND_SONG, GET_SONG);
    }
}
