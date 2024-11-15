package Utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Utils{

    static RequestSpecification req;

    public RequestSpecification getReq() throws IOException {
        if (req == null){
            PrintStream log = new PrintStream(new PrintStream(Files.newOutputStream(Paths.get("logging.txt"))));
            req = new RequestSpecBuilder()
                    .setBaseUri(getURL("baseUrl"))
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();

            return req;
        }
        return req;
    }

    public String getURL(String key) throws IOException {
        Properties properties = new Properties();
        FileInputStream file = new FileInputStream("E:\\Projects\\petStore\\src\\test\\resources\\Config\\global.properties");
        properties.load(file);
        return properties.getProperty(key);
    }

    public String getJsPath(Response response, String key){
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }
}