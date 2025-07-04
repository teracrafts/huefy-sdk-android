package com.teracrafts.huefy;

/**
 * Response from sending bulk emails
 */
@com.squareup.moshi.JsonClass(generateAdapter = true)
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B1\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0001\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0001\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0001\u0010\b\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\tJ\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0006H\u00c6\u0003J7\u0010\u0014\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0003\u0010\u0005\u001a\u00020\u00062\b\b\u0003\u0010\u0007\u001a\u00020\u00062\b\b\u0003\u0010\b\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0006H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000b\u00a8\u0006\u001b"}, d2 = {"Lcom/teracrafts/huefy/BulkEmailResponse;", "", "results", "", "Lcom/teracrafts/huefy/BulkEmailResult;", "successCount", "", "failureCount", "totalCount", "(Ljava/util/List;III)V", "getFailureCount", "()I", "getResults", "()Ljava/util/List;", "getSuccessCount", "getTotalCount", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "huefy-android"})
public final class BulkEmailResponse {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.teracrafts.huefy.BulkEmailResult> results = null;
    private final int successCount = 0;
    private final int failureCount = 0;
    private final int totalCount = 0;
    
    public BulkEmailResponse(@org.jetbrains.annotations.NotNull()
    java.util.List<com.teracrafts.huefy.BulkEmailResult> results, @com.squareup.moshi.Json(name = "success_count")
    int successCount, @com.squareup.moshi.Json(name = "failure_count")
    int failureCount, @com.squareup.moshi.Json(name = "total_count")
    int totalCount) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.teracrafts.huefy.BulkEmailResult> getResults() {
        return null;
    }
    
    public final int getSuccessCount() {
        return 0;
    }
    
    public final int getFailureCount() {
        return 0;
    }
    
    public final int getTotalCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.teracrafts.huefy.BulkEmailResult> component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.teracrafts.huefy.BulkEmailResponse copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.teracrafts.huefy.BulkEmailResult> results, @com.squareup.moshi.Json(name = "success_count")
    int successCount, @com.squareup.moshi.Json(name = "failure_count")
    int failureCount, @com.squareup.moshi.Json(name = "total_count")
    int totalCount) {
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