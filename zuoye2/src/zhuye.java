import java.util.Random;
import java.util.Scanner;
class Person{
    public String name;
    public int hp;
}
class Hero extends Person {
    private int attack;
    private int armor;

    public int getDefense(){
        return armor;
    }
    public void setDefense(int armor){
        this.armor = armor;
    }
    public int getAttack(){
        return attack;
    }
    public void setAttack(int attack){
        this.attack = attack;
    }
    public int phyattack(Monster m){
        System.out.println(this.name+"对"+m.name+"使用普通攻击,造成"+this.attack+"点伤害。");
        int mhp = m.hp - this.attack;
        return mhp;
    }
    public int skillattack(Monster m){
        System.out.println(this.name+"对"+m.name+"使用致命一击，造成"+(3*this.attack)+"点伤害。");
        int mhp = m.hp - (3*this.attack);
        return mhp;
    }
}
class Monster extends Person{
    private int attack;
    public Monster(String name,int attack,int hp){
        this.name = name;
        this.hp = hp;
        this.attack = attack;
    }
    public int getAttack() {
        return attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public int phyattack(Hero h){
        System.out.println(this.name+"对"+h.name+"使用普通攻击，造成"+(this.attack-h.getDefense())+"点伤害。");
        int hhp = h.hp - this.attack+h.getDefense();
        return hhp;
    }
}
public class zhuye {
    public static int round = 1;
    public static int deathnum = 0;                         //记录怪物真实阵亡数，这样避免采用arraylist.remove
    public static void Create(Person p) {                    //人物创建
        Scanner input = new Scanner(System.in);
        Hero player1 = (Hero) p;
        System.out.println("请输入请输入玩家的基本信息.");
        System.out.println("姓名:(50个字符以内)");
        player1.name = input.nextLine();
        System.out.println("生命值:(1-999)");
        player1.hp = input.nextInt();
        System.out.println("初始攻击力:(1-999)");
        player1.setAttack(input.nextInt());
        System.out.println("初始护甲:(1-100)");
        player1.setDefense(input.nextInt());
        System.out.println("玩家的姓名:" + player1.name + "\n生命值:  " + player1.hp + "\n初始攻击力:" +
                player1.getAttack() + "\n初始护甲:" + player1.getDefense());
    }
    public static Monster CreatMonster(String name1, int hp1, int attack1) {
        Monster s0 = new Monster(name1, hp1, attack1);
        Random rand = new Random();
        int c1 = rand.nextInt(9);
        if (c1 % 2 == 1) {
            s0.hp = s0.hp + c1 * 10;
            s0.setAttack(s0.getAttack() + c1);
        }
        if (c1 % 2 == 0) {                          //c1是偶数的话对应的初始值就 -
            s0.hp = s0.hp - c1 * 5;
            s0.setAttack(s0.getAttack() + c1);
        }
        return s0;
    }

    public static void main(String[] args) {
        Hero player1 = new Hero();
        Create(player1);                                        //调用函数创建人物
        System.out.println("----------***********---------");
        System.out.println("怪物降临！");
        Scanner input = new Scanner(System.in);
        System.out.println("请输入怪物的名字:(50个字符以内)");
        String name1 = input.nextLine();
        System.out.println("请输入怪物生命值:(150-200)");
        int hp1 = input.nextInt();
        System.out.println("请输入怪物初始攻击力:");
        int attack1 = input.nextInt();
        Monster M1 = CreatMonster(name1,hp1,attack1);
        System.out.println("小怪兽:"+M1.name+"\n生命值:"+
                M1.hp + "\n攻击力"+M1.getAttack());
        System.out.println("----------开始战斗！！---------");
        while (true){
            System.out.println("\n----------第"+round+"回合---------");
            System.out.println("轮到"+player1.name+"采取行动");
            System.out.println("1.普通攻击");
            System.out.println("2.*技能*");
            System.out.println("3.逃跑");
            int atcfangshi = input.nextInt();                 //攻击的方式
            input.nextLine();
            if (atcfangshi==1) {
                M1.hp = player1.phyattack(M1);
                if(M1.hp<=0){
                    System.out.println(M1+"阵亡!\n"+player1.name+"获胜!");
                    System.exit(0);
                }
                System.out.println(M1.name+"还剩"+M1.hp+"血。");
            }
            if (atcfangshi==2){
                M1.hp = player1.skillattack(M1);
                System.out.println(M1.name+"还剩"+M1.hp+"血。");
                if(M1.hp<=0){
                    System.out.println(player1.name+"获胜!");
                    System.exit(0);
                }
            }
            if (atcfangshi == 3){
                System.out.println("逃跑成功！");
                System.exit(-1);
            }
            System.out.println("轮到"+name1+"行动.");
            player1.hp = M1.phyattack(player1);
            if (player1.hp<=0){
                System.out.println(player1.name+"阵亡!\n"+name1+"获胜!\n游戏结束,感谢你的游玩。");
                System.exit(0);
            }
            System.out.println(player1.name + "的剩余生命值为:  " + player1.hp);
            round++;
        }
    }
}

