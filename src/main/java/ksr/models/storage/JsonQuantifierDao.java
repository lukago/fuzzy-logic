package ksr.models.storage;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ksr.models.fuzzy.functions.IMembershipFunction;
import ksr.models.fuzzy.functions.TrapezoidalFunction;
import ksr.models.fuzzy.functions.TriangularFunction;
import ksr.models.fuzzy.sets.Quantyfier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonQuantifierDao implements IDao<List<Quantyfier>> {

    private String filePath;

    public JsonQuantifierDao(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Quantyfier> read() throws Exception {
        JsonObject jsonObject = new JsonParser()
                .parse(Files.asCharSource(new File(filePath), Charsets.UTF_8).read())
                .getAsJsonObject();

        List<Quantyfier> quantyfiers = new ArrayList<>();

        jsonObject.getAsJsonArray("qunatifiers").forEach(q -> {
            boolean relative = q.getAsJsonObject().get("relative").getAsBoolean();
            String label = q.getAsJsonObject().get("label").getAsString();
            String[] params = q.getAsJsonObject().get("fparams").getAsString().split(",");

            IMembershipFunction function;
            if (q.getAsJsonObject().get("ftype").getAsString().equalsIgnoreCase("triangular")) {
                function = new TriangularFunction(
                        Double.valueOf(params[0]),
                        Double.valueOf(params[1]),
                        Double.valueOf(params[2]));
            } else {
                function = new TrapezoidalFunction(
                        Double.valueOf(params[0]),
                        Double.valueOf(params[1]),
                        Double.valueOf(params[2]),
                        Double.valueOf(params[3]));
            }

            quantyfiers.add(new Quantyfier(function, label, relative));
        });

        return quantyfiers;
    }

    @Override
    public void write(List<Quantyfier> in) throws Exception {

    }

    @Override
    public void close() throws Exception {

    }
}
