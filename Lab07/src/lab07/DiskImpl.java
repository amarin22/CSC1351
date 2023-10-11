package lab07;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author alexm
 */
public class DiskImpl implements DiskI{
    
    WarriorImpl loaded;

    @Override
    public WarriorI loadWarrior(File f) throws IOException {
        FileReader fr = new FileReader(f);
        Scanner scan = new Scanner(fr);
        WarriorImpl w = new WarriorImpl();
        w.initFromString(scan.nextLine());
        WeaponImpl we = new WeaponImpl();
        we.initFromString(scan.nextLine());
        w.setWeapon(we);
        scan.close();
        fr.close();
        return w;
    }

    @Override
    public File saveWarrior(WarriorI w) throws IOException {
        File f = new File(w.getName());
        PrintWriter pw = new PrintWriter(f);
        pw.println(w.toString());
        pw.println(w.getWeapon().toString());
        pw.close();
        return f;
    }
}