package atypon.worldnav.game.items;

import atypon.worldnav.game.playerSystem.Player;
import atypon.worldnav.game.settings.Gold;

public class FlashLight extends Item {

    private int state;

    FlashLight(String flashlight,Gold price) {
        setState(0);
        setName("flashlight");
        setPrice(price);
    }

    public int getState() {
        return state;
    }

    private void setState(int state) {
        this.state = state;
    }

    @Override
    public String use(Player player) {
        if (state == 0) {
            setState(1);
            return "Flash light is ON";
        }
        setState(0);
        return "Flash light is OFF";
    }

}
