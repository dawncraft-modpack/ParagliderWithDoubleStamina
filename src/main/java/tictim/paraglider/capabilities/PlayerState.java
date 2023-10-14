package tictim.paraglider.capabilities;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Objects;

public enum PlayerState {
    IDLE("idle", false, 20),
    MIDAIR("midair", false, 0),
    RUNNING("running", false, -10),
    SWIMMING("swimming", false, -6),
    UNDERWATER("underwater", false, 3),
    BREATHING_UNDERWATER("breathingUnderwater", false, 10),
    RIDING("riding", false, 20),
    PARAGLIDING("paragliding", true, -3),
    ASCENDING("ascending", true, -3);

    public final String id;

    private final boolean paragliding;
    public final double defaultChange;

    private ForgeConfigSpec.DoubleValue change;

    PlayerState(String id, boolean paragliding, double defaultChange) {
        this.id = id;
        this.paragliding = paragliding;
        this.defaultChange = defaultChange;
    }

    public boolean isParagliding() {
        return paragliding;
    }

    public double doubleChange() {
        return change.get();
    }

    @Deprecated
    public int change() {
        return (int) doubleChange();
    }

    public boolean isConsume() {
        return doubleChange() < 0;
    }

    public void setConfig(ForgeConfigSpec.DoubleValue change) {
        if (this.change != null) throw new IllegalStateException("Multiple config entry");
        this.change = Objects.requireNonNull(change);
    }

    public static PlayerState of(int meta) {
        PlayerState[] values = values();
        return values[meta % values.length];
    }
}
