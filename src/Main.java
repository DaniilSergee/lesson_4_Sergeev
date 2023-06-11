import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static String[] heroesAttackType = {"Physical", "Magical", "Mental", "Golem", "Сleric"};
    public static int[] heroesHealth =       {270,    260,    250,    500,    250};
    public static int[] heroesDamage =       {10,     15,     20,     5,       0};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        healSpell();
        printStatistics();
    }

    public static void healSpell() {
        if (heroesHealth[4] > 0){
            int k = 0; //количество героев которым нужно лечение
            for (int i = 0; i < heroesHealth.length - 1; i++) { //медик сам себя не лечит
                if (heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                    k++;
                }
            }
            if (k > 0){
                int m = (int)(Math.random()*k + 1); //номер героя которому рандомно выпадет лечение
                for (int i = 0; i < heroesHealth.length - 1; i++) {
                    if (heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                        m--;
                        if (m==0){
                            heroesHealth[i] += 40; //пусть N равно 40
                            break;
                        }
                    }
                }
            }
        }

    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); //0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);

                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[3] == 0){
                if (heroesHealth[i] > 0) {
                    if (heroesHealth[i] - bossDamage < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] = heroesHealth[i] - bossDamage;
                    }
                }
            } else {
                if (heroesHealth[i] > 0) {
                    if (heroesHealth[i] - bossDamage*4/5 < 0) {
                        heroesHealth[i] = 0;
                        if (heroesHealth[3] - bossDamage/5 < 0) {
                            heroesHealth[3] = 0;
                        }else{
                            heroesHealth[3] = (int)(heroesHealth[3] - bossDamage/5);
                        }
                    } else {

                        heroesHealth[i] = (int)(heroesHealth[i] - bossDamage*4/5);
                        if (heroesHealth[3] - bossDamage/5 < 0) {
                            heroesHealth[3] = 0;
                        }else{
                            heroesHealth[3] = (int)(heroesHealth[3] - bossDamage/5);
                        }
                    }
                }
            }
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        /*String defence;
        if (bossDefence == null) {
            defence = "No Defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("ROUND " + roundNumber + " --------------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No Defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}