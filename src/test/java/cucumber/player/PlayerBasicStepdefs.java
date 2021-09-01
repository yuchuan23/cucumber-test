package cucumber.player;

import cucumber.beans.*;
import cucumber.World;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerBasicStepdefs {
    private static final Logger logger = LoggerFactory.getLogger(PlayerBasicStepdefs.class);

    private World world;

    public PlayerBasicStepdefs(World world) {
        logger.info("to construct PlayerBasicStepdefs, world = {}", world);
        this.world = world;
    }

    @Given("player with name is {string} and default level")
    public void player_with_name_and_default_level(String name) {
        Player p = new Player(name);
        this.world.context.set("player", p);
        logger.info("current world player: {}", this.world.context.get("player"));
    }

    @Given("player with name is {string} and level is {int}")
    public void player_with_name_and_level(String name, int level) {
        Player p = new Player(name, level);
        this.world.context.set("player", p);
        logger.info("current world player: {}", this.world.context.get("player"));
    }

    @When("I rename player to {string}")
    public void i_rename_player_to(String name) {
        Player p = (Player) this.world.context.get("player");
        p.setName(name);
        this.world.context.set("player", p);
        logger.info("current world player: {}", this.world.context.get("player"));
    }

    @When("I levelup once")
    public void i_levelup_once() {
        Player p = (Player) this.world.context.get("player");
        p.levelUp();
        this.world.context.set("player", p);
        logger.info("current world player: {}", this.world.context.get("player"));
    }

    @When("I levelup with {int}")
    public void i_levelup_with(int n) {
        Player p = (Player) this.world.context.get("player");
        p.levelUp(n);
        this.world.context.set("player", p);
        logger.info("current world player: {}", this.world.context.get("player"));
    }

    @When("I ask player rank")
    public void i_ask_player_rank() {
        Player p = (Player) this.world.context.get("player");
        String rank = p.getRank();
        this.world.context.set("rank", rank);
        logger.info("current world: {}", this.world.context);
    }
}
