package tictim.paraglider.capabilities;

import net.minecraft.world.entity.player.Player;
import tictim.paraglider.ModCfg;
import tictim.paraglider.contents.Contents;

public class ClientPlayerMovement extends RemotePlayerMovement {
    private PlayerState prevState = PlayerState.IDLE;

    public ClientPlayerMovement(Player player) {
        super(player);
    }

    @Override
    public void update() {
        updateStamina();

        if (!player.isCreative() && !canAction()) {
            player.setSprinting(false);
            player.setSwimming(false);
        }
        else if (prevState.isParagliding() != getState().isParagliding()) {
			player.setSprinting(getState().isParagliding());
		}

        applyMovement();

        prevState = getState();
    }
}
