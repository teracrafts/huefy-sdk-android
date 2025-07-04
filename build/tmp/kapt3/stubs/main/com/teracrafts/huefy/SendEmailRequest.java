package com.teracrafts.huefy;

/**
 * Request to send a single email
 */
@com.squareup.moshi.JsonClass(generateAdapter = true)
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B7\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\u0015\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\bH\u00c6\u0003J?\u0010\u0015\u001a\u00020\u00002\b\b\u0003\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001J\t\u0010\u001b\u001a\u00020\u0003H\u00d6\u0001R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f\u00a8\u0006\u001c"}, d2 = {"Lcom/teracrafts/huefy/SendEmailRequest;", "", "templateKey", "", "data", "", "recipient", "provider", "Lcom/teracrafts/huefy/EmailProvider;", "(Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;Lcom/teracrafts/huefy/EmailProvider;)V", "getData", "()Ljava/util/Map;", "getProvider", "()Lcom/teracrafts/huefy/EmailProvider;", "getRecipient", "()Ljava/lang/String;", "getTemplateKey", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "huefy-android"})
public final class SendEmailRequest {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String templateKey = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Object> data = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String recipient = null;
    @org.jetbrains.annotations.Nullable()
    private final com.teracrafts.huefy.EmailProvider provider = null;
    
    public SendEmailRequest(@com.squareup.moshi.Json(name = "template_key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String templateKey, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> data, @org.jetbrains.annotations.NotNull()
    java.lang.String recipient, @org.jetbrains.annotations.Nullable()
    com.teracrafts.huefy.EmailProvider provider) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTemplateKey() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Object> getData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRecipient() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.teracrafts.huefy.EmailProvider getProvider() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Object> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.teracrafts.huefy.EmailProvider component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.teracrafts.huefy.SendEmailRequest copy(@com.squareup.moshi.Json(name = "template_key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String templateKey, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> data, @org.jetbrains.annotations.NotNull()
    java.lang.String recipient, @org.jetbrains.annotations.Nullable()
    com.teracrafts.huefy.EmailProvider provider) {
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