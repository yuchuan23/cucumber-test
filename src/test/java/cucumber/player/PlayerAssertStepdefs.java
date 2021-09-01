package cucumber.player;

import cucumber.beans.*;
import cucumber.World;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerAssertStepdefs {
    private static final Logger logger = LoggerFactory.getLogger(PlayerAssertStepdefs.class);

    private World world;

    public PlayerAssertStepdefs(World world) {
        logger.info("to construct PlayerAssertStepdefs, world = {}", world);
        this.world = world;
    }

    @Then("player name should be {string}")
    public void player_name_should_be(String expectedName) {
        Player p = (Player) this.world.context.get("player");
        assertEquals(expectedName, p.getName());
    }

    @Then("player level should be {int}")
    public void player_level_should_be(int expectedLevel) {
        Player p = (Player) this.world.context.get("player");
        assertEquals(expectedLevel, p.getLevel());
    }

    @Then("player rank should be {string}")
    public void player_rank_should_be(String expectedRank) {
        String rank = (String) this.world.context.get("rank");
        assertEquals(expectedRank, rank);
    }
}
