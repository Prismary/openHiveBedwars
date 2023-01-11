package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Format;
import net.prismarray.openhivebedwars.util.SoundHandler;
import net.prismarray.openhivebedwars.util.Title;
import org.bukkit.Sound;

public class WarmupTimer extends Countdown {

    String[] fightAnimation;

    public WarmupTimer() {
        super(11, 0, 1);

        setFightAnimation();
    }

    public void setFightAnimation() {
        fightAnimation = new String[15];
        fightAnimation[0] = "§c§lF          I          G          H          T          !";
        fightAnimation[1] = "§c§lF         I         G         H         T         !";
        fightAnimation[2] = "§c§lF        I        G        H        T        !";
        fightAnimation[3] = "§c§lF       I       G       H       T       !";
        fightAnimation[4] = "§c§lF      I      G      H      T      !";
        fightAnimation[5] = "§c§lF    I    G    H    T    !";
        fightAnimation[6] = "§c§lF  I  G  H  T  !";
        fightAnimation[7] = "§c§lF I G H T !";
        fightAnimation[8] = "§c§lFIGHT!";
        fightAnimation[9] = "§e§lF§c§lIGHT!";
        fightAnimation[10] = "§c§lF§e§lI§c§lGHT!";
        fightAnimation[11] = "§c§lFI§e§lG§c§lHT!";
        fightAnimation[12] = "§c§lFIG§e§lH§c§lT!";
        fightAnimation[13] = "§c§lFIGH§e§lT!";
        fightAnimation[14] = "§c§lFIGHT!";
    }

    public void playFightAnimation() {
        for (int frame = 0; frame < fightAnimation.length-1; frame++) {
            scheduleAnimationFrame(frame);
        }

        // Schedule final frame with longer duration
        scheduler.scheduleSyncDelayedTask(
                OpenHiveBedwars.getInstance(), () -> Title.sendToAll(
                        fightAnimation[14], "§7Protect your bed, destroy others!", 0, 40, 10
                ), 14
        );
    }

    private void scheduleAnimationFrame(int frame) {
        scheduler.scheduleSyncDelayedTask(
                OpenHiveBedwars.getInstance(), () -> Title.sendToAll(
                        fightAnimation[frame], "§7Protect your bed, destroy others!", 0, 2, 0
                ), frame
        );
    }


    @Override
    public void onDecrement() {
        if (getCount() == 9) {
            String modeName;
            switch (Game.getMode()) {
                case SOLO:
                    modeName = "Solo";
                    break;
                case DUOS:
                    modeName = "Duos";
                    break;
                default:
                    modeName = "Teams";
                    break;
            }

            Title.sendToAll("§6BedWars " + modeName, "§7A §rplay.HiveMC.com §7Re-Creation", 10, 60, 10);
        }

        if (getCount() <= 5) {
            String numPrefix;
            switch (getCount()) {
                case 5:
                case 4:
                    numPrefix = "§c";
                    break;
                case 3:
                    SoundHandler.globalPlayerSound("random.orb", 1f, 0.5f);
                    numPrefix = "§6";
                    break;
                case 2:
                    SoundHandler.globalPlayerSound("random.orb", 1f, 0.75f);
                    numPrefix = "§6";
                    break;
                case 1:
                    SoundHandler.globalPlayerSound("random.orb", 1f, 1f);
                    numPrefix = "§e";
                    break;
                default:
                    numPrefix = "";
                    break;
            }

            Title.sendToAll(numPrefix + Format.getRoundNumeral(getCount()), "§7until start", 0, 30, 0);
        }
    }

    @Override
    public void onCompletion() {
        SoundHandler.globalPlayerSound(Sound.ENDERDRAGON_GROWL);
        Game.ingame();
        playFightAnimation();
    }
}