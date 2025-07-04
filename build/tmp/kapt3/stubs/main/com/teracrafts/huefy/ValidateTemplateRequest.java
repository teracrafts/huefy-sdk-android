package com.teracrafts.huefy;

/**
 * Request to validate a template
 */
@com.squareup.moshi.JsonClass(generateAdapter = true)
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B%\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0014\b\u0001\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\u0015\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005H\u00c6\u0003J)\u0010\r\u001a\u00020\u00002\b\b\u0003\u0010\u0002\u001a\u00020\u00032\u0014\b\u0003\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/teracrafts/huefy/ValidateTemplateRequest;", "", "templateKey", "", "testData", "", "(Ljava/lang/String;Ljava/util/Map;)V", "getTemplateKey", "()Ljava/lang/String;", "getTestData", "()Ljava/util/Map;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "huefy-android"})
public final class ValidateTemplateRequest {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String templateKey = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Object> testData = null;
    
    public ValidateTemplateRequest(@com.squareup.moshi.Json(name = "template_key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String templateKey, @com.squareup.moshi.Json(name = "test_data")
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> testData) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTemplateKey() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Object> getTestData() {
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
    public final com.teracrafts.huefy.ValidateTemplateRequest copy(@com.squareup.moshi.Json(name = "template_key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String templateKey, @com.squareup.moshi.Json(name = "test_data")
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> testData) {
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