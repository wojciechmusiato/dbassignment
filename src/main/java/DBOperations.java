import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class DBOperations {
    private Connection c;

    public DBOperations() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost/db", "root", "root");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String insertUser(String username, String password, String email, String role) {
        PreparedStatement stmt;
        Statement stmt2 = null;
        ResultSet rs = null;
        try {
            stmt2 = c.createStatement();
            rs = stmt2.executeQuery("SELECT COUNT(*)FROM USERS WHERE USERNAME = '" + username + "'");
            rs.next();
            if (rs.getInt(1) == 1) {
                return "User " + username + " already exists in the database.";
            }

            stmt = c.prepareStatement("INSERT INTO USERS (USERNAME,PASSWORD,EMAIL,TYPE ) " +
                    "VALUES ('" + username + "','" + password + "','" + email + "','" + role + "');");
            int i = stmt.executeUpdate();
            if (i > 0) {
                System.out.println("User successfully inserted.");
            } else {
                return "Problem inserting " + username + ". Server side error";

            }
            stmt = c.prepareStatement("insert into users_groups values (" +
                    "(select id from users where username = '" + username + "')" +
                    ",1,0)");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "User inserted to public group";
    }

    public void searchMusic(String phrase) {
        Statement stmt;
        ResultSet rs = null;
        String format = "%-20s%-20s%-20s%n";
        System.out.printf(format, "Title", "Album", "Artist");
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("select s.title,a.album_name,ar.artist_name\n" +
                    "from songs s,albums_songs als,albums a,artists ar\n" +
                    "where s.id=als.songs_id and als.albums_id = a.id and a.artists_id=ar.id" +
                    " and (s.title like('%" + phrase + "%') or a.album_name like('%" + phrase + "%') or ar.artist_name like('%" + phrase + "%'))");
            while (rs.next()) {
                String user = rs.getString(1);
                String pass = rs.getString(2);
                String email = rs.getString(3);

                System.out.printf(format, user, pass, email);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }




    public boolean deleteAlbum(int albumid) {
        PreparedStatement stmt;
        try {
            stmt = c.prepareStatement("delete from albums where albums.id=" + albumid + " ");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean deleteSong(int songid) {
        PreparedStatement stmt;
        try {
            stmt = c.prepareStatement("delete from songs where songs.id=" + songid + " ");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean updateArtist(String artistNewName, String changeArtistsName) {
        return true;
    }

    public boolean updateAlbum(String albumNewName, String changeAlbumName) {
        return true;
    }

    public boolean updateSong(String title, int idsong) {
        PreparedStatement stmt;

        try {
            stmt = c.prepareStatement("update songs set title = '"+title+"' where "+idsong+"=id");
            if (stmt.executeUpdate() == 0) {
                System.out.println("Nothing inserted...WTF");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean insertGroup(String name, int ownerid) {
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = c.prepareStatement("insert into groops (name, owner_id) values ('" + name + "'," + ownerid + ")",
                    Statement.RETURN_GENERATED_KEYS);
            if (stmt.executeUpdate() == 0) {
                System.out.println("Nothing inserted...WTF");
                return false;
            }
            rs = stmt.getGeneratedKeys();
            rs.next();

            stmt = c.prepareStatement("insert into users_groups values" +
                    " (" + ownerid + "," + rs.getInt(1) + ",1)");
            if (stmt.executeUpdate() == 0) {
                System.out.println("Nothing inserted...WTF");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean insertSong(String[] albums, String title) {
        Random r = new Random();
        int a = r.nextInt(9);
        int b = r.nextInt(50) + 10;
        String length = a + ":" + b;
        PreparedStatement stmt;
        Statement stmt2;
        ResultSet rs;
        try {
            stmt = c.prepareStatement("insert into songs (title, length) values ('" + title + "','" + length + "')",
                    Statement.RETURN_GENERATED_KEYS);
            int result = stmt.executeUpdate();
            if (result == 0) {
                System.out.println("Nothing inserted...WTF");
            }
            rs = stmt.getGeneratedKeys();
            rs.next();

            for (String x : albums) {
                stmt = c.prepareStatement("insert into albums_songs" +
                        " values (" + Integer.parseInt(x) + "," + rs.getInt(1) + ")");
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }



    public boolean changeRoleOfAUser(String user, String role) {
        PreparedStatement stmt;
        Statement stmt2;
        ResultSet rs;
        try {

            stmt2 = c.createStatement();
            rs = stmt2.executeQuery("SELECT COUNT(*)FROM users where username='" + user + "'");
            rs.next();
            if (rs.getInt(1) == 0) {
                return false;
            }

            stmt = c.prepareStatement("update users set type = '" + role + "' where username='" + user + "'");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void getAllUsers() {
        Statement stmt;
        ResultSet rs = null;
        String format = "%-20s%-20s%-20s%-20s%-20s%n";
        System.out.printf(format, "User ID", "Username", "Password", "Email", "Role");
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM USERS");
            while (rs.next()) {
                int id = rs.getInt(1);
                String user = rs.getString(2);
                String pass = rs.getString(3);
                String email = rs.getString(4);
                String role = rs.getString(5);
                System.out.printf(format, id, user, pass, email, role);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }


    public void getAllSongs() {
        Statement stmt;
        ResultSet rs = null;
        String format = "%-20s%-20s%-20s%-20s%n";
        System.out.printf(format, "Song ID", "Title", "Album", "Artist");
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("select s.id,s.title,a.album_name,ar.artist_name\n" +
                    "from songs s,albums_songs als,albums a,artists ar\n" +
                    "where s.id=als.songs_id and als.albums_id = a.id and a.artists_id=ar.id");
            while (rs.next()) {
                int user = rs.getInt(1);
                String pass = rs.getString(2);
                String email = rs.getString(3);
                String sth = rs.getString(4);
                System.out.printf(format, user, pass, email, sth);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public void getAllAlbums() {
        Statement stmt;
        ResultSet rs = null;
        String format = "%-20s%-20s%-20s%n";
        System.out.printf(format, "AlbumID", "Album", "Artist");
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("select a.id,a.album_name,ar.artist_name\n" +
                    "from albums a,artists ar\n" +
                    "where a.artists_id=ar.id");
            while (rs.next()) {
                int user = rs.getInt(1);
                String pass = rs.getString(2);
                String mail = rs.getString(3);

                System.out.printf(format, user, pass, mail);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public void getAllArtists() {
        Statement stmt;
        ResultSet rs = null;
        String format = "%-20s%-20s%-20s%-20s%-20s%n";
        System.out.printf(format, "Artist ID", "Name",
                "Band Creation date", "Band Breakup Date", "Musician's Full name");
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("select * from artists");
            while (rs.next()) {
                int a = rs.getInt(1);
                String b = rs.getString(2);
                String c = rs.getString(3);
                String d = rs.getString(4);
                String e = rs.getString(5);
                System.out.printf(format, a,b,c,d,e);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }


    public boolean login(String username, String password) {

        Statement stmt = null;
        String query = "SELECT COUNT(*) FROM USERS WHERE username = '" + username + "' AND password = '" + password + "'";
        try {

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            if (rs.getInt(1) == 1) {
                stmt.close();
                return true;
            } else {
                stmt.close();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void getSharedSongs(int userid) {
        Statement stmt;
        ResultSet rs = null;
        String format = "%-20s%-20s%n";
        System.out.printf(format, "Song ID", "Filename");
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT distinct s.songs_id,s.filename " +
                    "FROM songfiles s, usershares u,groupshares g " +
                    "where u.users_id = " + userid + " and " +
                    "s.filename = u.songfiles_filename " +
                    "union " +
                    "select distinct s.songs_id,s.filename " +
                    "FROM songfiles s, groupshares g " +
                    "where s.filename = g.songfiles_filename and " +
                    "g.groups_id in(select ug.groups_id from  users_groups ug where " + userid + "=ug.users_id)");

            while (rs.next()) {
                int id = rs.getInt(1);
                String user = rs.getString(2);
                System.out.printf(format, id, user);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public boolean deleteUser(String username) {
        PreparedStatement stmt;
        Statement stmt2;
        ResultSet rs;
        try {
            stmt2 = c.createStatement();
            rs = stmt2.executeQuery("SELECT COUNT(*)FROM users where username='" + username + "'");
            rs.next();
            if (rs.getInt(1) == 0) {
                return false;
            }
            stmt = c.prepareStatement("delete from groops where owner_id = (select id from users where username='" + username + "')");
            stmt.executeUpdate();

            System.out.println("Deleted groups created by this user. Deleting user...");

            stmt = c.prepareStatement("delete from users where username='" + username + "'");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }


    public User getOneUser(String username) {
        Statement stmt = null;
        String query = "";
        if (username.matches("-?\\d+(\\.\\d+)?")) {
            Integer.parseInt(username);
            query = "SELECT * FROM USERS WHERE id = " + username + "";
        } else {
            query = "SELECT * FROM USERS WHERE username = '" + username + "'";
        }

        try {

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int id = rs.getInt(1);
            String usr = rs.getString(2);
            String password = rs.getString(3);
            String email = rs.getString(4);
            String type = rs.getString(5);


            return new User(id, usr, password, email, type);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean deleteArtist(String name) {
        Statement stmt = null;
        ResultSet rs = null;
        String query = "delete from artists where artist_name = '" + name + "'";
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*)FROM artists where artist_name='" + name + "'");
            rs.next();
            if (rs.getInt(1) == 0) {
                return false;
            }
            stmt = c.prepareStatement(query);
            ((PreparedStatement) stmt).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public int getID(String table, String idcolumn, String column, String record) {
        Statement stmt;
        ResultSet rs;
        int result = -1;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT " + idcolumn + "from " + table + " where " + column + "='" + record + "')");
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("User doesn't exist with that ID");
            }
            result = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkIfExists(String table, String column, String record) {
        Statement stmt;
        ResultSet rs;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*)FROM " + table + " where " + column + "='" + record + "'");
            rs.next();
            if (rs.getInt(1) == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean insertUserToGroup(int groupid, int userid, int isEditor, int ownerid) {
        PreparedStatement stmt;

        ResultSet rs;
        try {
            Statement stmt2 = c.createStatement();
            rs = stmt2.executeQuery("SELECT COUNT(*)FROM users_groups where isEditor = 1 and " +
                    " " + groupid + " = groups_id and " + ownerid + "=users_id");
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("You aren't the editor of this group lmao");
                return false;
            }
            rs = stmt2.executeQuery("SELECT COUNT(*)FROM users_groups where " +
                    " " + groupid + " = groups_id and " + userid + "=users_id");
            rs.next();
            if (rs.getInt(1) == 1) {
                System.out.println("User already in group.");
                return false;
            }
            stmt = c.prepareStatement("insert into users_groups values (" + userid + "," + groupid + "," + isEditor + ")");
            int result = stmt.executeUpdate();
            if (result == 0) {
                System.out.println("Nothing inserted...WTF");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void getAllGroups() {
        Statement stmt;
        ResultSet rs = null;
        String format = "%-20s%-20s%-20s%n";
        System.out.printf(format, "Group ID", "Name", "Owner ID");
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM groops");
            while (rs.next()) {
                int user = rs.getInt(1);
                String pass = rs.getString(2);
                int email = rs.getInt(3);
                System.out.printf(format, user, pass, email);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public boolean deleteUserFromGroup(int groupid, int userid, int ownerid) {
        PreparedStatement stmt;

        ResultSet rs;
        try {
            Statement stmt2 = c.createStatement();
            rs = stmt2.executeQuery("SELECT COUNT(*)FROM users_groups where isEditor = 1 and " +
                    " " + groupid + " = groups_id and " + ownerid + "=users_id");
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("You aren't the editor of this group lmao");
                return false;
            }
            rs = stmt2.executeQuery("SELECT COUNT(*)FROM users_groups where " +
                    " " + groupid + " = groups_id and " + userid + "=users_id");
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("User isnt in this group.");
                return false;
            }
            rs = stmt2.executeQuery("SELECT COUNT(*)FROM groops where " +
                    " " + groupid + " = id and " + userid + "=owner_id");
            rs.next();
            if (rs.getInt(1) == 1) {
                System.out.println("You can't delete owner of the group xd");
                return false;
            }
            stmt = c.prepareStatement("delete from users_groups where " + groupid + "=groups_id and " + userid + "=users_id");
            int result = stmt.executeUpdate();
            if (result == 0) {
                System.out.println("Nothing deleted...WTF");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean insertFile(int songid, String filename, int userid) {
        PreparedStatement stmt;
        Statement stmt2 = null;
        ResultSet rs = null;
        try {
            stmt = c.prepareStatement("insert into songfiles values ('" + filename + "'," + songid + "," + userid + ")");
            if (stmt.executeUpdate() == 0) {
                System.out.println("Nothing inserted... WTF");
                return false;
            }
            stmt = c.prepareStatement("insert into usershares values ('" + filename + "'," + userid + ")");
            if (stmt.executeUpdate() == 0) {
                System.out.println("Nothing inserted... WTF");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean shareSong(int id, String filename, int which, int userid) {
        PreparedStatement stmt;
        Statement stmt2 = null;
        ResultSet rs = null;
        try {
            if (which == 0) {//group
                stmt2 = c.createStatement();
                rs = stmt2.executeQuery("SELECT COUNT(*)FROM users_groups WHERE " +
                        "users_groups.groups_id = " + id + " and users_groups.users_id = " + userid + " ");
                rs.next();
                if (rs.getInt(1) == 0) {
                    System.out.println("You don't belong to this group");
                    return false;
                }
                stmt = c.prepareStatement("insert into groupshares values ('" + filename + "'," + id + ")");
                if (stmt.executeUpdate() == 0) {
                    System.out.println("Nothing happened WTF");
                    return false;
                }
            } else {//user
                stmt2 = c.createStatement();
                rs = stmt2.executeQuery("SELECT COUNT(*)FROM usershares WHERE " +
                        "usershares.users_id = " + id + " and usershares.songfiles_filename = '" + filename + "' ");
                rs.next();
                if (rs.getInt(1) == 1) {
                    System.out.println("User has this song shared already.");
                    return false;
                }
                stmt = c.prepareStatement("insert into usershares values ('" + filename + "'," + id + ")");
                if (stmt.executeUpdate() == 0) {
                    System.out.println("Nothing happened WTF");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean insertPlaylist(String playlist, int userid) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = c.prepareStatement("insert into playlists (name) values ('" + playlist + "')",
                    Statement.RETURN_GENERATED_KEYS);
            if (stmt.executeUpdate() == 0) {
                System.out.println("Nothing inserted...WTF");
                return false;
            }
            rs = stmt.getGeneratedKeys();
            rs.next();
            stmt = c.prepareStatement("insert into users_playlists values (" + userid + "," + rs.getInt(1) + ")");
            if (stmt.executeUpdate() == 0) {
                System.out.println("Nothing inserted...WTF");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void getAllPlaylists() {
        Statement stmt;
        ResultSet rs = null;
        String format = "%-20s%-20s%n";
        System.out.printf(format, "Playlist ID", "Playlist");
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("select * from playlists");
            while (rs.next()) {
                int idd = rs.getInt(1);
                String user = rs.getString(2);
                System.out.printf(format, idd, user);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public boolean deletePlaylist(int playlistid, int userid) {
        PreparedStatement stmt = null;
        Statement stmt2;
        ResultSet rs;
        try {
            stmt2 = c.createStatement();
            rs = stmt2.executeQuery("SELECT COUNT(*)FROM users_playlists " +
                    "where playlists_id =" + playlistid + " and " +
                    "users_id =" + userid + " ");
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("This playlist isn't yours.");
                return false;
            }
            stmt = c.prepareStatement("delete from playlists where id = "+playlistid+" ");
            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void getUserPlaylists(int userid) {
        Statement stmt;
        ResultSet rs = null;
        String format = "%-20s%-20s%n";
        System.out.printf(format, "Playlist ID", "Playlist");
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("select p.id,p.name " +
                    "from playlists p, users_playlists up " +
                    "where up.playlists_id = p.id and " +
                    "up.users_id = "+userid+" ");
            while (rs.next()) {
                int idd = rs.getInt(1);
                String user = rs.getString(2);
                System.out.printf(format, idd, user);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public boolean addSongToPlaylist(int idplay, int idsong) {
        PreparedStatement stmt;
        Statement stmt2;
        ResultSet rs= null;
        try {
            stmt2 = c.createStatement();
            rs = stmt2.executeQuery("SELECT COUNT(*)FROM users_playlists " +
                    "where playlists_id =" + idplay + " and " +
                    "users_id =" + idsong + " ");
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("This playlist isn't yours.");
                return false;
            }
            stmt = c.prepareStatement("insert into playlists_songs values ("+idplay+","+idsong+")");
            if (stmt.executeUpdate() == 0) {
                System.out.println("Nothing inserted...WTF");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean insertReview(int albumid,String review, int userid) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = c.prepareStatement("insert into reviews (text, users_id, albums_id) " +
                    "values ('"+review+"',"+userid+","+albumid+")");
            if (stmt.executeUpdate() == 0) {
                System.out.println("Nothing inserted...WTF");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void showMyReviews(int userid) {
        Statement stmt;
        ResultSet rs = null;
        String format = "%-20s%-60s%-20s%n";
        System.out.printf(format, "Review ID", "Review", "Album");
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("select r.id,r.text,a.album_name from reviews r,albums a " +
                    "where "+userid+"= r.users_id and r.albums_id = a.id");
            while (rs.next()) {
                int idd = rs.getInt(1);
                String user = rs.getString(2);
                String se = rs.getString(3);
                System.out.printf(format, idd, user,se);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public void getAlbumReviews(int albumid) {
        Statement stmt;
        ResultSet rs = null;
        String format = "%-20s%-60s%-20s%n";
        System.out.printf(format, "Review ID", "Review", "User ID");
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("select r.id,r.text,r.users_id from reviews r,albums a " +
                    "where "+albumid+"= r.albums_id and r.albums_id = a.id");
            while (rs.next()) {
                int idd = rs.getInt(1);
                String user = rs.getString(2);
                String se = rs.getString(3);
                System.out.printf(format, idd, user,se);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public boolean deleteReview(int revid,int userid) {
        PreparedStatement stmt;
        Statement stmt2;
        ResultSet rs;
        try {
            stmt2 = c.createStatement();
            rs = stmt2.executeQuery("SELECT COUNT(*)FROM reviews " +
                    "where id =" + revid + " and " +
                    "users_id =" + userid + " ");
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("This review isn't yours.");
                return false;
            }
            stmt = c.prepareStatement("delete from reviews where reviews.id=" + revid + " ");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public void getAllPublishers() {
        Statement stmt;
        ResultSet rs = null;
        String format = "%-20s%-20s%n";
        System.out.printf(format, "Publisher ID","Publisher name");
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("select * from publisher");
            while (rs.next()) {
                int id = rs.getInt(1);
                String user = rs.getString(2);
                System.out.printf(format, id ,user);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public void getAllConcerts( ) {
        Statement stmt;
        ResultSet rs = null;
        String format = "%-20s%-20s%-20s%-20s%n";
        System.out.printf(format, "Start Date","End Date","ID","Name");
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("select * from concerts");
            while (rs.next()) {
                String user = rs.getString(1);
                String s = rs.getString(2);
                int id = rs.getInt(3);
                String ss = rs.getString(4);
                System.out.printf(format,  user,s,id,ss);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public boolean insertPublisher(String pubid) {
        PreparedStatement stmt = null;
        try {

            stmt = c.prepareStatement("insert into publisher (name) values ('" + pubid + "')");
            if (stmt.executeUpdate() == 0) {
                System.out.println("Nothing inserted...WTF");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean insertConcert(String start, String end, String name) {
        PreparedStatement stmt = null;
        try {

            stmt = c.prepareStatement("insert into concerts (date_start, date_end, name) " +
                    "values ('" + start + "','" + end + "','" + name + "')");
            if (stmt.executeUpdate() == 0) {
                System.out.println("Nothing inserted...WTF");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean deleteConcert(int conid) {
        PreparedStatement stmt;
        try {
            stmt = c.prepareStatement("delete from concerts where concerts.id=" + conid + " ");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean insertAlbum(String name, String gen, String des, int pubid, int arid) {
        PreparedStatement stmt = null;
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        String formatter = formmat1.format(ldt);

        try {

            stmt = c.prepareStatement("insert into albums " +
                    "(album_name, creation_date, genre, descr, publisher_id, artists_id) " +
                    "values ('" + name + "','" + formatter + "','" + gen + "','" + des + "'," + pubid + "," + arid +" )");
            if (stmt.executeUpdate() == 0) {
                System.out.println("Nothing inserted...WTF");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean insertArtistToConcert(int conid, int arid) {
        PreparedStatement stmt = null;
        Statement stmt2 = null;
        ResultSet rs = null;
        try {
            stmt2 = c.createStatement();
            rs = stmt2.executeQuery("SELECT COUNT(*) FROM concerts_artists " +
                    "where artists_id =" + arid + " and " +
                    "concerts_id=" + conid + " ");
            rs.next();
            if (rs.getInt(1) == 1) {
                System.out.println("Already in the lineup.");
                return false;
            }
            stmt = c.prepareStatement("insert into concerts_artists " +
                    "values (" + conid + "," + arid +" )");
            if (stmt.executeUpdate() == 0) {
                System.out.println("Nothing inserted...WTF");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void getLineup(int conid) {
        Statement stmt;
        ResultSet rs = null;
        String format = "%-20s%-20s%n";
        System.out.printf(format, "Concert Name","Artist Name");
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("select c.name,a.artist_name " +
                    "from concerts c, artists a, concerts_artists ca " +
                    "where c.id = ca.concerts_id and ca.artists_id=a.id" +
                    "and "+conid+"= ca.concerts_id");
            while (rs.next()) {
                String user = rs.getString(1);
                String s = rs.getString(2);

                System.out.printf(format,  user,s);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }
}
