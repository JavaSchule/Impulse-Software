package net.impulse.lib.document;

import com.google.gson.*;
import javafx.util.Callback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;


public class GsonService {

    public static JsonElement load(File file) throws IOException {
        return new JsonParser().parse(new String(Files.readAllBytes(file.toPath())));
    }

    public static <J extends JsonElement> J loadAs(File file, String name, Callback<JsonElement, Boolean> check, Callback<JsonElement, J> cast) throws IOException {
        JsonElement element = load(file);
        if (check.call(element)) return cast.call(element);
        else throw new ClassCastException("The loaded JsonElement is not a " + name);
    }


    public static JsonObject loadAsJsonObject(File file) throws IOException{
        return loadAs(file, "JsonObject", JsonElement::isJsonObject, JsonElement::getAsJsonObject);
    }


    public static JsonArray loadAsJsonArray(File file) throws IOException {
        return loadAs(file, "JsonArray", JsonElement::isJsonArray, JsonElement::getAsJsonArray);
    }

    public static void save(File file, JsonElement jsonElement) throws IOException {
        save(file, jsonElement, true);
    }


    public static void save(File file, JsonElement jsonElement, Boolean serializeNulls) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(formatJson(jsonElement, serializeNulls));
        writer.flush();
        writer.close();
    }

    public static String formatJson(JsonElement jsonElement) {
        return formatJson(jsonElement, true);
    }

    public static String formatJson(JsonElement jsonElement, Boolean serializeNulls) {
        GsonBuilder result = new GsonBuilder().setPrettyPrinting();
        if (serializeNulls) result.serializeNulls();
        return result.create().toJson(jsonElement);
    }

}