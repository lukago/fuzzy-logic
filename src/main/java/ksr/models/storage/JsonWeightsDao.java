package ksr.models.storage;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonWeightsDao implements IDao<List<Double>> {

    private String filePath;

    public JsonWeightsDao(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Double> read() throws Exception {
        JsonObject jsonObject = new JsonParser()
                .parse(Files.asCharSource(new File(filePath), Charsets.UTF_8).read())
                .getAsJsonObject();

        List<Double> weights = new ArrayList<>();
        String[] keys = jsonObject.get("keys").getAsString().split(",");

        for (String key : keys) {
            weights.add(jsonObject.get(key).getAsDouble());
        }

        return weights;
    }

    @Override
    public void write(List<Double> in) throws Exception {

    }

    @Override
    public void close() throws Exception {

    }
}
