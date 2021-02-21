package atypon.worldnav.game.settings;

public class TextInterface{

    public static void winMessage(){
        System.out.println("==========YOU DID IT!!=========");
        System.out.println("==========YOU HAVE ESCAPED!!==========");
        System.out.println("==========CONGRATULATIONS==========");

    }

    public static void loseMessage(){
        System.out.println("==========TIME'S UP=========");
        System.out.println("==========YOU LOST==========");
        System.out.println("==========YOU GOT TRAPPED INSIDE==========");
    }

    public static void frameMessage(){
        System.out.println("============================================");
    }

    public static void tradeModeMessage() {
        System.out.println("===========Seller store===========");
        System.out.println("'buy <item>'  -> to buy affordable item");
        System.out.println("'sell <item>' -> to sell item you own");
        System.out.println("'list'        -> to list sellers items");
        System.out.println("'finish'      -> to exit trade mode");
        System.out.println("==================================");

    }

}
