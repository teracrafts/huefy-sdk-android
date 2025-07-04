package com.teracrafts.huefy;

/**
 * Health check response
 */
@com.squareup.moshi.JsonClass(generateAdapter = true)
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0014\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\u0017\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\tH\u00c6\u0003JR\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u0016\b\u0002\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\tH\u00c6\u0001\u00a2\u0006\u0002\u0010\u001aJ\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001J\t\u0010 \u001a\u00020\u0003H\u00d6\u0001R\u001f\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000e\u00a8\u0006!"}, d2 = {"Lcom/teracrafts/huefy/HealthResponse;", "", "status", "", "version", "uptime", "", "timestamp", "providers", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Ljava/util/Map;)V", "getProviders", "()Ljava/util/Map;", "getStatus", "()Ljava/lang/String;", "getTimestamp", "getUptime", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getVersion", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Ljava/util/Map;)Lcom/teracrafts/huefy/HealthResponse;", "equals", "", "other", "hashCode", "", "toString", "huefy-android"})
public final class HealthResponse {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String status = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String version = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long uptime = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String timestamp = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.Map<java.lang.String, java.lang.String> providers = null;
    
    public HealthResponse(@org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.String version, @org.jetbrains.annotations.Nullable()
    java.lang.Long uptime, @org.jetbrains.annotations.NotNull()
    java.lang.String timestamp, @org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, java.lang.String> providers) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getVersion() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getUptime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTimestamp() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Map<java.lang.String, java.lang.String> getProviders() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Map<java.lang.String, java.lang.String> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.teracrafts.huefy.HealthResponse copy(@org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.String version, @org.jetbrains.annotations.Nullable()
    java.lang.Long uptime, @org.jetbrains.annotations.NotNull()
    java.lang.String timestamp, @org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, java.lang.String> providers) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}