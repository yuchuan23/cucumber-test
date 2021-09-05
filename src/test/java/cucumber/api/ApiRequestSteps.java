package cucumber.player;

import cucumber.beans.*;
import cucumber.World;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.asynchttpclient.util.HttpConstants.Methods;
import static org.asynchttpclient.Dsl.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;

public class ApiRequestSteps {
    private static final Logger logger = LoggerFactory.getLogger(ApiRequestSteps.class);
    private static final int DEFAULT_TIMEOUT_MS = 5000;
    private static final ObjectMapper MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private World world;
    private String host;
    private AsyncHttpClient client;

    public ApiRequestSteps(World world) {
        this.world = world;
        this.host = System.getProperty("host");
        this.client = asyncHttpClient();
        logger.info("to construct ApiRequestSteps, host = {}", this.host);
    }

    @Given("A login user")
    public void a_login_user() {
        String cookie = "fakeCookie";
        this.world.context.set("cookie", cookie);
        logger.info("a login user cookie: {}", this.world.context.get("cookie"));
    }

    @Given("A non login user")
    public void a_non_login_user() {
        this.world.context.set("cookie", "");
        logger.info("a non login user cookie: {}", this.world.context.get("cookie"));
    }

    @When("I make a {string} request to {string}")
    public void i_make_a_request_to(String method, String path) {
        executeRequest(client, method, path, null, 0);
    }

    @When("I make a {string} request to {string} and payload={string}")
    public void i_make_a_request_to_and_payload(String method, String path, String payload) {
        executeRequest(client, method, path, payload, 0);
    }

    @When("I make a {string} request to {string} and retry={int}")
    public void i_make_a_request_to(String method, String path, int maxRetry) {
        executeRequest(client, method, path, null, maxRetry);
    }

    @When("I make a {string} request to {string} and payload={string} and retry={int}")
    public void i_make_a_request_to_and_payload(String method, String path, String payload, int maxRetry) {
        executeRequest(client, method, path, payload, maxRetry);
    }

    private void executeRequest(AsyncHttpClient client, String method, String path, String payload, int maxRetry) {
        String url = this.host + path;
        RequestBuilder request = getDefaultRequestBuilder(method)
            .setUrl(url)
            .setBody(payload);

        String cookie = (String) this.world.context.get("cookie");
        if (StringUtils.isNotBlank(cookie)) {
            logger.info("with cookie={}", cookie);
            request.addHeader("Cookie", cookie);
        }

        int retry = 0;
        while(retry++ <= maxRetry) {
            try {
                Response response = client.executeRequest(request).get();

                int status = response.getStatusCode();
                String body = response.getResponseBody();
                logger.info("status={}, body={}", status, body);

                if (status > 400 && retry <= maxRetry) {
                    continue;
                }

                this.world.context.set(World.Context.LAST_RESPONSE_STATUS, status);
                this.world.context.set(World.Context.LAST_RESPONSE_BODY, body);
                this.world.context.set(World.Context.LAST_RESPONSE_JSON, toJsonNode(body));
            } catch (Exception e) {
                logger.error("Failed to get response, method={}, api={}", method, path);

                if (retry <= maxRetry) {
                    continue;
                }

                this.world.context.set(World.Context.LAST_RESPONSE_STATUS, 0);
                this.world.context.set(World.Context.LAST_RESPONSE_BODY, null);
                this.world.context.set(World.Context.LAST_RESPONSE_JSON, null);
                return;
            }
        }
    }

    private RequestBuilder getDefaultRequestBuilder(String method) {
        return new RequestBuilder(method)
            .setRequestTimeout(DEFAULT_TIMEOUT_MS)
            .setReadTimeout(DEFAULT_TIMEOUT_MS)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json");
    }

    private JsonNode toJsonNode(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            return MAPPER.readTree(json);
        } catch (Exception e) {
            logger.error("Failed to parse to JsonNode: ", e);
            return null;
        }
    }
}
