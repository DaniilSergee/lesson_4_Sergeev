import java.util.Random;

public class Main {
    public static int bossHealth = 1000;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static String[] heroesAttackType = {"Berserk", "Magical", "Mental", "Lucky", "Golem", "Thor", "Physical", "Сleric"};
    public static int[] heroesHealth =        {270,         260,        250,      250,     500,     100,     270,     250};
    public static int[] heroesDamage =        {10,           15,         20,       10,      5,       10,      10,     0};
    public static int roundNumber = 0;
    public static int flag1 = 0;//крит урон
    public static int flag2 = 0;//уклонение от босса
    public static int flag3 = 0;//оглушение босса
    public static int block = 20;//блок берсерка
    public static String bigDamager;

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
        if (heroesHealth[7] > 0){
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
                            System.out.println("healSpell to " + heroesAttackType[i]);
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
        flag3 = 0;
        if (Math.random()*5 > 4 && heroesHealth[5] > 0){
            flag3 = 1; //Босс оглушён
        }
        for (int i = 0; i < heroesDamage.length; i++) {
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                int damage = 0;
                if (i != 0) {
                    damage = heroesDamage[i];
                }else{
                    damage = heroesDamage[i]; // Пусть берсерк вернёт 10 урона
                }
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    flag1 = damage;
                    bigDamager = heroesAttackType[i];
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
        if (flag3 == 0 && bossHealth > 0) { // жив и оглушён ли босс
            //код для берсерка
            flag2 = 0;
            if (heroesHealth[3] > 0 && (Math.random() * 6 < 2)) { //шанс уклонения 0.333
                flag2 = 1;
            }
            /////////////////
            if (heroesHealth[4] == 0) {
                if (heroesHealth[0] > 0) {
                    if (heroesHealth[0] - bossDamage + block <= 0) {
                        heroesHealth[0] = 0;
                    } else {
                        heroesHealth[0] = heroesHealth[0] - bossDamage + block;
                        if (bossHealth - block <= 0) {
                            bossHealth = 0;
                        } else {
                            bossHealth = bossHealth - block;
                        }
                    }
                }
            } else{
                if (heroesHealth[0] > 0) {
                    if (heroesHealth[0] - bossDamage * 4 / 5 + block <= 0) {
                        heroesHealth[0] = 0;
                        if (heroesHealth[4] - bossDamage / 5 < 0) {
                            heroesHealth[4] = 0;
                        } else {
                            heroesHealth[4] = (int) (heroesHealth[4] - bossDamage / 5);
                        }
                    } else {

                        heroesHealth[0] = heroesHealth[0] - (bossDamage * 4 / 5) + block;
                        if (heroesHealth[4] - bossDamage / 5 < 0) {
                            heroesHealth[4] = 0;
                        } else {
                            heroesHealth[4] = heroesHealth[4] - bossDamage / 5;
                        }
                        if (bossHealth - block <= 0) {
                            bossHealth = 0;
                        } else {
                            bossHealth = bossHealth - block;
                        }
                    }
                }
            }
            /////////////
            for (int i = 1; i < heroesHealth.length; i++) {
                    if (i == 3 && flag2 == 1) { //шанс уклонения 0.333
                        continue;
                    }
                    if (heroesHealth[4] == 0) {
                        if (heroesHealth[i] > 0) {
                            if (heroesHealth[i] - bossDamage < 0) {
                                heroesHealth[i] = 0;
                            } else {
                                heroesHealth[i] = heroesHealth[i] - bossDamage;
                            }
                        }
                    } else{
                        if (heroesHealth[i] > 0) {
                            if (heroesHealth[i] - bossDamage * 4 / 5 < 0) {
                                heroesHealth[i] = 0;
                                if (heroesHealth[4] - bossDamage / 5 < 0) {
                                    heroesHealth[4] = 0;
                                } else {
                                    heroesHealth[4] = (int) (heroesHealth[4] - bossDamage / 5);
                                }
                            } else {

                                heroesHealth[i] = (int) (heroesHealth[i] - bossDamage * 4 / 5);
                                if (heroesHealth[4] - bossDamage / 5 < 0) {
                                    heroesHealth[4] = 0;
                                } else {
                                    heroesHealth[4] = (int) (heroesHealth[4] - bossDamage / 5);
                                }
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

        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No Defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
        if (flag2 == 1){
            System.out.println("Boss - Mazila Hahaha ");
        }
        flag2 = 0;
        if (bigDamager != null && bigDamager == bossDefence){
            System.out.println("Critical damage: " + flag1);
            System.out.println("Critical damager: " + bigDamager);
        }
        if (flag3 == 1 && bossHealth > 0){
            System.out.println("Boss was stunned for the next round ");
        }
        System.out.println("It was ROUND^ " + roundNumber + " --------------------");
    }
}