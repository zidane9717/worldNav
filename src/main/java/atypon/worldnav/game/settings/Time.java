package atypon.worldnav.game.settings;


import atypon.worldnav.mvc.controller.GameController;
import atypon.worldnav.mvc.controller.GameManager;

public class Time implements Runnable {

    int time;
    String number;

    public Time(String number) {
        this.number = number;
        time = 3000000;
    }

    @Override
    public void run() {
        try {
            while (time != 0) {
                GameController.getInstance().sendToClients(number, time());
                time = time - 60000;
                Thread.sleep(60000);
            }
            GameController.getInstance().sendToClients(number,"TIME IS UP EVERYONE LOST");
            GameManager.getInstance().removeGame(number);
        } catch (InterruptedException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String time() {
        return "================ Time remaining: " + time / 60 / 1000 + " minutes left================";
    }

}
//test