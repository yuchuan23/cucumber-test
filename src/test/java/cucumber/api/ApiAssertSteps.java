package cucumber.player;

import cucumber.beans.*;
import cucumber.World;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;

public class ApiAssertSteps {
    private static final Logger logger = LoggerFactory.getLogger(ApiAssertSteps.class);

    private World world;

    public ApiAssertSteps(World world) {
        //logger.info("to construct ApiAssertSteps, world = {}", world);
        this.world = world;
    }

    // status code

    @Then("The status code of last response should be {int}")
    public void the_status_code_should_be(int expect) {
        int status = (int) this.world.context.get(World.Context.LAST_RESPONSE_STATUS);
        assertEquals("status code should be " + expect, expect, status);
    }

    // number: larger and smaller, could be int, double

    @Then("The {string} of last response should be larger than {string}")
    public void the_field_should_be_larger_than(String jsonPointer, String expect) throws Exception {
        double expectNumber = Double.parseDouble(expect);
        JsonNode response = (JsonNode) this.world.context.get(World.Context.LAST_RESPONSE_JSON);

        double actualNumber = response.at(jsonPointer).asDouble();

        assertTrue(String.format("%s should be larger than %s, actual=%s", jsonPointer, expect, actualNumber), actualNumber > expectNumber);
    }

    @Then("The {string} of last response should be smaller than {string}")
    public void the_field_should_be_smaller_than(String jsonPointer, String expect) throws Exception {
        double expectNumber = Double.parseDouble(expect);
        JsonNode response = (JsonNode) this.world.context.get(World.Context.LAST_RESPONSE_JSON);

        double actualNumber = response.at(jsonPointer).asDouble();

        assertTrue(String.format("%s should be smaller than %s, actual=%s", jsonPointer, expect, actualNumber), actualNumber < expectNumber);
    }

    // string: equal and not equal, special case: is "empty"

    @Then("The {string} of last response should be {string}")
    public void the_field_should_be(String jsonPointer, String expect) throws Exception {
        JsonNode response = (JsonNode) this.world.context.get(World.Context.LAST_RESPONSE_JSON);

        String actual = response.at(jsonPointer).asText(null);

        if ("empty".equals(expect)) {
            assertTrue(String.format("%s should be empty, actual=%s", jsonPointer, actual), StringUtils.isEmpty(actual));
        } else {
            assertEquals(String.format("%s should be %s", jsonPointer, expect), expect, actual);
        }
    }

    @Then("The {string} of last response should not be {string}")
    public void the_field_should_not_be(String jsonPointer, String expect) throws Exception {
        JsonNode response = (JsonNode) this.world.context.get(World.Context.LAST_RESPONSE_JSON);

        String actual = response.at(jsonPointer).asText(null);

        if ("empty".equals(expect)) {
            assertTrue(String.format("%s should not be empty, actual=%s", jsonPointer, actual), StringUtils.isNotEmpty(actual));
        } else {
            assertTrue(String.format("%s should not be %s, actual=%s", jsonPointer, expect, actual), !expect.equals(actual));
        }
    }

    // list: size

    @Then("The size of {string} of last response should be {string}")
    public void the_size_of_field_should_be(String jsonPointer, String expect) throws Exception {
        int expectSize = Integer.parseInt(expect);
        JsonNode response = (JsonNode) this.world.context.get(World.Context.LAST_RESPONSE_JSON);

        JsonNode actualNode = response.at(jsonPointer);

        if (!actualNode.isArray()) {
            assertTrue(jsonPointer + "should be an array", false);
            return;
        }

        assertEquals(String.format("%s's size should be %s", jsonPointer, expect), expectSize, actualNode.size());
    }

    @Then("The size of {string} of last response should be larger than {string}")
    public void the_size_of_field_should_be_larger_than(String jsonPointer, String expect) throws Exception {
        int expectSize = Integer.parseInt(expect);
        JsonNode response = (JsonNode) this.world.context.get(World.Context.LAST_RESPONSE_JSON);

        JsonNode actualNode = response.at(jsonPointer);

        if (!actualNode.isArray()) {
            assertTrue(jsonPointer + "should be an array", false);
            return;
        }

        assertTrue(String.format("%s's size should be larger than %s, actual=%s", jsonPointer, expect, actualNode.size()), actualNode.size() > expectSize);
    }
}
