package com.zetcode;

import freemarker.template.Configuration;
import freemarker.template.Version;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import static spark.Spark.get;
import spark.template.freemarker.FreeMarkerEngine;

public class SparkFreeMarker {

    public static void main(String[] args) throws IOException {
        
        Configuration conf = new Configuration(new Version(2, 3, 23));
        conf.setClassForTemplateLoading(SparkFreeMarker.class, "/views");

        get("/hello/:name", SparkFreeMarker::message, new FreeMarkerEngine(conf));
    }

    public static ModelAndView message(Request req, Response res) {

        Map<String, Object> params = new HashMap<>();
        params.put("name", req.params(":name"));
        return new ModelAndView(params, "hello.ftl");
    }
}