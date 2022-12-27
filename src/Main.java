import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    static void saveGame(String s) {
        GameProgress gameProgress = new GameProgress(33, 10, 24, 954.32);
        GameProgress gameProgress1 = new GameProgress(65, 43, 44, 1234.32);
        GameProgress gameProgress2 = new GameProgress(23, 51, 104, 2345.32);
        try (FileOutputStream fos = new FileOutputStream(s, true);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            oos.writeObject(gameProgress1);
            oos.writeObject(gameProgress2);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void zipFiles(String s) {
        try (ZipOutputStream zit = new ZipOutputStream(new FileOutputStream("F:/Game/savegames/zip.zip", true));
             FileInputStream fis = new FileInputStream(s)) {
            ZipEntry entry = new ZipEntry("zipSave.dat");
            zit.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zit.write(buffer);
            zit.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void zipFilesRevert(String s) {
        try (ZipInputStream zit = new ZipInputStream(new FileInputStream(s))) {
            ZipEntry entry;
            while ((entry = zit.getNextEntry()) != null) {
                FileOutputStream fil = new FileOutputStream("F:/Game/savegames/newSave.dat", true);
                for (int i = zit.read(); i != -1; i = zit.read()) {
                    fil.write(i);
                }
                fil.flush();
                zit.closeEntry();
                fil.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static void desirializacia(String s) {
        GameProgress gameProgress = null;
        GameProgress gameProgress1 = null;
        GameProgress gameProgress2 = null;
        try (FileInputStream fis = new FileInputStream(s);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
            gameProgress1 = (GameProgress) ois.readObject();
            gameProgress2 = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(gameProgress);
        System.out.println(gameProgress1);
        System.out.println(gameProgress2);

    }

    static void delete(String s) {
        File f = new File(s);
        f.delete();
    }

    public static void main(String[] args) {
        saveGame("F:/Game/savegames/save.dat");
        zipFiles("F:/Game/savegames/save.dat");
        delete("F:/Game/savegames/save.dat");
        zipFilesRevert("F:/Game/savegames/zip.zip");
        desirializacia("F:/Game/savegames/newSave.dat");
    }
}