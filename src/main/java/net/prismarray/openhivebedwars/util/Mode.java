package net.prismarray.openhivebedwars.util;

public enum Mode {
    SOLO (1),
    DUOS (2),
    TEAMS (4);

    public final int teamSize;
    Mode(int teamSize) {
        this.teamSize = teamSize;
    }
}
