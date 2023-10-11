package lab07;

 

import java.util.Scanner;

import java.util.regex.MatchResult;

 

public class WarriorImpl implements WarriorI {
    private String name;
    private WeaponI weapon;
    private int health;
    private int maxhealth;

    

    public WarriorImpl() {   
        name = "a";
        maxhealth = 85;
        health = maxhealth;
        weapon = new WeaponImpl();
    }

   

    public WarriorImpl(String n, int maxh, WeaponImpl a){
        name = n;
        maxhealth = maxh;
        weapon = a;
    }

   

    WarriorImpl(String str) {

        initFromString(str);

    }

 

      /**

   * Return the weapon that the warrior

   * currently has equipped.

   * There should be * a variable of type

   * WeaponI to hold this data.

   */

    @Override

    public WeaponI getWeapon() {
        return weapon;
    }

  /**

   * Place the weapon object in the

   * warrior's hand.

   */

    @Override

    public void setWeapon(WeaponI w) {
        weapon = (WeaponI)w;
    }

     /**

   * Return the warrior's name, e.g.

   * Conan, RedSonja, etc. The

   * variable 'name' should be a field

   * on your class.

   */

 

    @Override

    public String getName() {
        return name;
    }

   

  /**

   * Returns true if the warrior

   * has taken more damage than

   * the warrior's maxHealth.

   */

 

    @Override

    public boolean isDefeated() {
       return health <= 0;
    }

 

  /**

   * Returns true if the warrior

   * has taken no damage.

   */

    @Override

    public boolean isHealthy() {
        return health == maxhealth;
    }

  /**

   * Invokes the getDamage() method

   * on the weapon and subtracts the returned

   * value from the warrior's current health points.

   * If the warrior's health points fall

   * to zero or lower, the warrior is

   * defeated. This method returns

   * the amount of damage dealt.

   */

    @Override

    public int takeDamage(WeaponI w) {
        int a = w.getDamage();
        health -= a;
        return a;
    }

  /**

   * The maximum amount of damage the

   * warrior could take if the warrior

   * were healthy before being defeated.

   * maxHealth should be a field on your

   * class.

   */

    @Override

    public int getMaxHealth() {
        return maxhealth;
    }

 

  /**

   * The current amount of damage the

   * warrior could take if the warrior

   * were healthy before being defeated.

   * health should be a field on your class.

   */

    @Override

    public int getHealth() {
       return health;
    }

   

  /**

   * The format should look like this:

   *

   * "Warrior {name}, health={number}, max health={number}"

   *

   * */

    public String toString() {
        return "Warrior " + name + ", health=" + health + ", max health=" + maxhealth;
    }

 

  /**

   * This method should take a string in the same format

   * as that generated by the toString() method above, i.e.,

   *

   * "Warrior {name}, health={number}, max health={number}"

   *

   * It should parse  using the findInLine() method on Scanner,

   * and sets the values.

   * Note that spaces may vary in length. The

   * following is valid.

   * Warrior conan,health=10, max  health=13

   *

   * If the string doesn't mattch the above pattern,

   * you should throw a PatternMatchException.

   *

   * <em>Note</em>: it's probably a good idea to add \\s*

   * to the end of your pattern in order to deal with line

   * ending problems.

   */

    @Override

    public void initFromString(String input) throws PatternMatchException {
        if (input.equals(""))
            throw new PatternMatchException();
        String pattern = "\\w+\\s*(\\w+),\\s*\\w+\\=(\\d+),\\s*\\w+\\s*\\w+\\=(\\d+)\\s*";
        try (Scanner in = new Scanner(input)) {
            while (in.hasNextLine()) {
                if (in.findInLine(pattern) != null) {
                    MatchResult m = in.match();
                    name = m.group(1);
                    health = Integer.parseInt(m.group(2));
                    maxhealth = Integer.parseInt(m.group(3));
                } else {
                throw new PatternMatchException();
                }
            }
        }
    }

 

  /**

   * Restore a warrior to full health.

   */

    @Override

    public void heal() {
        health = maxhealth;
    }
}