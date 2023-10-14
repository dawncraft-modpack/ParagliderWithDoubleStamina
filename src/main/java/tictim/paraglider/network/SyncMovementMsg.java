package tictim.paraglider.network;

import net.minecraft.network.FriendlyByteBuf;
import tictim.paraglider.capabilities.PlayerMovement;
import tictim.paraglider.capabilities.PlayerState;

public record SyncMovementMsg(PlayerState state, double stamina, boolean depleted, int recoveryDelay) {
    public static SyncMovementMsg read(FriendlyByteBuf buffer) {
        return new SyncMovementMsg(
                PlayerState.of(buffer.readUnsignedByte()), buffer.readDouble(), buffer.readBoolean(), buffer.readVarInt());
    }

    public SyncMovementMsg(PlayerMovement h) {
        this(h.getState(), h.getDoubleStamina(), h.isDepleted(), h.getRecoveryDelay());
    }

    public void copyTo(PlayerMovement h) {
        h.setState(state);
        h.setStamina(stamina);
        h.setDepleted(depleted);
        h.setRecoveryDelay(recoveryDelay);
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeByte(state.ordinal());
        buffer.writeDouble(stamina);
        buffer.writeBoolean(depleted);
        buffer.writeVarInt(recoveryDelay);
    }
}
