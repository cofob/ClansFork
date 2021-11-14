package ru.cofob.Clans.MojangHandler;

public class CachedClient {
    private long cacheMillis;

    private String cacheName;

    private String cacheUUID;

    private String texture;

    public CachedClient(String name, String uuid, long cacheMillis) {
        this.cacheName = name;
        this.cacheUUID = uuid;
        this.cacheMillis = cacheMillis;
    }

    public void setTexture(String textureUrl) {
        this.texture = textureUrl;
    }

    public String getTexture() {
        return this.texture;
    }

    public long getCacheMillis() {
        return this.cacheMillis;
    }

    public String getCacheName() {
        return this.cacheName;
    }

    public String getCacheUUID() {
        return this.cacheUUID;
    }
}
