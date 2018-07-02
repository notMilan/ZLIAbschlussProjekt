package HUD;

public class Taskbar {

    private Health health;

    private double startingHP;

    public Taskbar(Health health) {
        this.health = health;
        this.startingHP = health.getHealth();
    }

    //Zeiche Leben je nach MainChars momentaner Health
    public void drawHealth() {
        double spacing;
        for (int i = 0; i < startingHP; i++) {
            spacing = health.getWidth()*i + 10;
            health.getGc().clearRect(health.getObjectX()+spacing, health.getObjectY(), health.getWidth(), health.getHeight());
        }

        for (int j = 0; j < health.getHealth(); j++) {
            spacing = health.getWidth()*j + 10;
            health.drawHeart(spacing, false);
        }
        if (health.getHealth() >= 0) {
            for (int k = (int) health.getHealth(); k < startingHP; k++) {
                spacing = health.getWidth() * k + 10;
                health.drawHeart(spacing, true);
            }
        }

    }

}
