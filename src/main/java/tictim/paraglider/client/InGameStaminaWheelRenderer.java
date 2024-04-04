package tictim.paraglider.client;

import tictim.paraglider.ModCfg;
import tictim.paraglider.capabilities.PlayerMovement;
import tictim.paraglider.capabilities.PlayerState;
import tictim.paraglider.utils.Color;

import static tictim.paraglider.client.StaminaWheelConstants.*;

public class InGameStaminaWheelRenderer extends StaminaWheelRenderer {
    private double prevStamina;
    private long fullTime;

    @Override
    protected void makeWheel(PlayerMovement playerMovement) {
        double stamina = playerMovement.getDoubleStamina();
        double maxStamina = playerMovement.getDoubleMaxStamina();
        if (stamina >= maxStamina) {
            long time = System.currentTimeMillis();
            long timeDiff;
            if (prevStamina != stamina) {
                prevStamina = stamina;
                fullTime = time;
                timeDiff = 0;
            }
            else {timeDiff = time - fullTime;}
            Color color = StaminaWheelConstants.getGlowAndFadeColor(timeDiff);
            if (color.alpha <= 0) return;
            for (WheelLevel t : WheelLevel.values()) addWheel(t, 0, t.getProportion(stamina), color);
        }
        else {
            prevStamina = stamina;
            Color color = DEPLETED_1.blend(
                    DEPLETED_2,
                    cycle(System.currentTimeMillis(),
                          playerMovement.isDepleted() ? DEPLETED_BLINK : BLINK));
            PlayerState state = playerMovement.getState();
            for (WheelLevel t : WheelLevel.values()) {
                addWheel(t, 0, t.getProportion(maxStamina), EMPTY);
                if (!playerMovement.canAction()) {
                    addWheel(t, 0, t.getProportion(stamina), color);
                }
                else {
                    addWheel(t,
                             0,
                             t.getProportion(stamina),
                             IDLE.blend(DEPLETED_2, (float) (1 - (stamina / maxStamina))));
                    if (state.isConsume()
                        && (state.isParagliding()
                            ? ModCfg.paraglidingConsumesStamina()
                            : ModCfg.runningConsumesStamina())) {
                        addWheel(t,
                                 t.getProportion(stamina + state.doubleChange() * 10),
                                 t.getProportion(stamina),
                                 color);
                    }
                }
            }
        }
    }
}
