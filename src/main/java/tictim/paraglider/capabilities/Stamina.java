package tictim.paraglider.capabilities;

public interface Stamina {
    /**
     * @return Amount of stamina
     */
    double getDoubleStamina();

    /**
     * @param stamina Amount of stamina to be set
     */
    void setStamina(double stamina);

    /**
     * @return Maximum amount of stamina, >=0
     */
    double getDoubleMaxStamina();

    /**
     * @return Whether or not depleted state is active
     */
    boolean isDepleted();

    /**
     * @param depleted Whether or not depleted state should be active
     */
    void setDepleted(boolean depleted);

    /**
     * Tries to add stamina by specific {@code amount} without exceeding {@link Stamina#getDoubleMaxStamina() maxStamina}.
     *
     * @param amount   Amount of stamina to be given
     * @param simulate Simulation only if {@code true}
     * @return Amount of stamina given
     */
    double giveStamina(double amount, boolean simulate);

    /**
     * Subtract stamina by specific {@code amount}. If stamina is currently in depleted state, unless {@code ignoreDepletion} is {@code true}, no stamina will be subtracted.
     *
     * @param amount          Amount of stamina to be taken
     * @param simulate        Simulation only if {@code true}
     * @param ignoreDepletion Bypasses depleted state check if {@code true}
     * @return Amount of stamina taken
     */
    double takeStamina(double amount, boolean simulate, boolean ignoreDepletion);
    /**
     * @return Amount of stamina
     */
    default int getStamina() {
        return (int) getDoubleStamina();
    }

    /**
     * @param stamina Amount of stamina to be set
     */
    default void setStamina(int stamina) {
        setStamina((double) stamina);
    }

    /**
     * @return Maximum amount of stamina, >=0
     */
    default int getMaxStamina() {
        return (int) getDoubleMaxStamina();
    }

    /**
     * Tries to add stamina by specific {@code amount} without exceeding {@link Stamina#getDoubleMaxStamina() maxStamina}.
     *
     * @param amount   Amount of stamina to be given
     * @param simulate Simulation only if {@code true}
     * @return Amount of stamina given
     */
    default int giveStamina(int amount, boolean simulate) {
        return (int) giveStamina((double) amount, simulate);
    }

    /**
     * Subtract stamina by specific {@code amount}. If stamina is currently in depleted state, unless {@code ignoreDepletion} is {@code true}, no stamina will be subtracted.
     *
     * @param amount          Amount of stamina to be taken
     * @param simulate        Simulation only if {@code true}
     * @param ignoreDepletion Bypasses depleted state check if {@code true}
     * @return Amount of stamina taken
     */
    default int takeStamina(int amount, boolean simulate, boolean ignoreDepletion) {
        return (int) takeStamina((double) amount, simulate, ignoreDepletion);
    }
}
