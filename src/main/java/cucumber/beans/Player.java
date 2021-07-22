package cucumber.beans;

import lombok.Data;

@Data
public class Player {
    private String name;
    private int level;

    public Player(String name) {
        this.name =  name;
        this.level = 0;
    }

    public Player(String name, int level) {
        this.name =  name;
        this.level = level;
    }

    public void levelUp() {
        this.level++;
    }

    public void levelUp(int n) {
        this.level += n;
    }

    public void resetLevel() {
        this.level = 0;
    }

    public String getRank() {
        if (level >= 90) {
            return "A";
        } else if (level >= 70) {
            return "B";
        } else if (level >= 50) {
            return "C";
        } else if (level >= 30){
            return "D";
        } else {
            return "E";
        }
    }
}
