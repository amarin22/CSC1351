package lab07;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.MatchResult;

public class WeaponImpl implements WeaponI{
    private String name = "a";
    private int maxDamage = 4;
    Random rand = new Random();
    
    public WeaponImpl(){
    }

    public WeaponImpl(String n, int md){
        this.name = n;
        this.maxDamage = md;;
    }

    @Override
    public int getDamage() {
        return rand.nextInt(1, getMaxDamage() + 1);
    }

    @Override
    public int getMaxDamage() {
        return maxDamage;
    }

    @Override
    public String toString(){
        return "Weapon " + name + ", damage=" + maxDamage;
    }
 
    @Override
    public void initFromString(String input) throws PatternMatchException {
        Scanner scan = new Scanner(input);
        String pattern = "Weapon\\s*(\\w+),\\s*damage=(\\d+)\\s*";
        if(input.equals(""))
            throw new PatternMatchException();
        while(scan.hasNextLine()){
            if(scan.findInLine(pattern) != null){
                MatchResult m = scan.match();
                    if(m.group(1).equals("") || m.group(2).equals("")){
                        throw new PatternMatchException();
                    }
                    name = m.group(1);
                    maxDamage = Integer.parseInt(m.group(2));
                } else {
                throw new PatternMatchException();
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }
}