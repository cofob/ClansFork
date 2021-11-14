package ru.cofob.Clans.MojangHandler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MojangProfileReader {
    public static Map<String, CachedClient> cachedUsernames = new HashMap<String, CachedClient>();

    public static Map<String, CachedClient> cachedUUIDs = new HashMap<String, CachedClient>();

    public static long cacheDelay = 320000L;

    private JsonParser parser = new JsonParser();

    public String getName(String uuid) {
        CachedClient cachedClient = cachedUsernames.get(uuid);
        if (cachedClient != null) {
            if (System.currentTimeMillis() < cachedClient.getCacheMillis() + cacheDelay)
                return cachedClient.getCacheName();
            cachedUsernames.remove(uuid);
        }
        cacheUser(uuid, false);
        if (!cachedUsernames.containsKey(uuid))
            return null;
        return ((CachedClient)cachedUsernames.get(uuid)).getCacheName();
    }

    public String getUUID(String name) {
        CachedClient cachedClient = cachedUUIDs.get(name);
        if (cachedClient != null) {
            if (System.currentTimeMillis() < cachedClient.getCacheMillis() + cacheDelay)
                return cachedClient.getCacheUUID();
            cachedUUIDs.remove(name);
        }
        cacheUser(name, false);
        if (!cachedUUIDs.containsKey(name))
            return null;
        return ((CachedClient)cachedUUIDs.get(name)).getCacheUUID();
    }

    public String getTexture(String uuid) {
        CachedClient cachedClient = cachedUsernames.get(uuid);
        if (cachedClient != null)
            if (System.currentTimeMillis() < cachedClient.getCacheMillis() + cacheDelay) {
                if (cachedClient.getTexture() != null)
                    return cachedClient.getTexture();
            } else {
                cachedUsernames.remove(uuid);
            }
        cacheUser(uuid, true);
        if (!cachedUsernames.containsKey(uuid))
            return null;
        return ((CachedClient)cachedUsernames.get(uuid)).getTexture();
    }

    public void cacheUser(String value, boolean texture) {
        String url = readURL("https://api.minetools.eu/uuid/" + value.replace("-", ""));
        if (url != null) {
            Object json = this.parser.parse(url);
            JsonObject object = (JsonObject)json;
            JsonElement nameElement = object.get("name");
            JsonElement uuidElement = object.get("id");
            if (uuidElement.getAsString().equals("null"))
                return;
            CachedClient uuidClient = new CachedClient(nameElement.getAsString(), convertUUID(uuidElement.getAsString()), System.currentTimeMillis());
            cachedUUIDs.put(nameElement.getAsString(), uuidClient);
            CachedClient nameClient = new CachedClient(nameElement.getAsString(), convertUUID(uuidElement.getAsString()), System.currentTimeMillis());
            cachedUsernames.put(convertUUID(uuidElement.getAsString()), nameClient);
            if (texture) {
                String textureProfile = readURL("https://api.minetools.eu/profile/" + value.replace("-", ""));
                json = this.parser.parse(textureProfile);
                object = (JsonObject)json;
                if (textureProfile != null) {
                    JsonObject propertiesDecodedElement = object.getAsJsonObject("decoded");
                    JsonObject textures = propertiesDecodedElement.getAsJsonObject("textures");
                    JsonObject skin = textures.getAsJsonObject("SKIN");
                    JsonElement textureElement = skin.get("url");
                    String textureUrl = textureElement.getAsString();
                    ((CachedClient)cachedUUIDs.get(nameElement.getAsString())).setTexture(textureUrl);
                    ((CachedClient)cachedUsernames.get(convertUUID(uuidElement.getAsString()))).setTexture(textureUrl);
                }
            }
        }
    }

    public String convertUUID(String uuid) {
        String realUUID = uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
        return realUUID;
    }

    private String readURL(String url) {
        try {
            Process process = Runtime.getRuntime().exec("curl " + url);
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null)
                line = line + inputLine;
            if (line.equals(""))
                return null;
            inputStreamReader.close();
            bufferedReader.close();
            return line;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
