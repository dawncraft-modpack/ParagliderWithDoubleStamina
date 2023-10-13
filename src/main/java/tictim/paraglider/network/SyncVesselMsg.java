package tictim.paraglider.network;

import net.minecraft.network.FriendlyByteBuf;

public record SyncVesselMsg(double stamina, int heartContainers, int staminaVessels) {
    public static SyncVesselMsg read(FriendlyByteBuf buffer) {
        return new SyncVesselMsg(buffer.readDouble(), buffer.readVarInt(), buffer.readVarInt());
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeDouble(stamina);
        buffer.writeVarInt(heartContainers);
        buffer.writeVarInt(staminaVessels);
    }
}
