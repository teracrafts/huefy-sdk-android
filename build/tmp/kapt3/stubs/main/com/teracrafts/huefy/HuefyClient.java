package com.teracrafts.huefy;

/**
 * Main Huefy SDK client for sending template-based emails
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u00013B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J<\u0010\f\u001a\u0002H\r\"\u0004\b\u0000\u0010\r2\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\r0\u0013H\u0082@\u00a2\u0006\u0002\u0010\u0014J\u000e\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u001a\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0002J\u000e\u0010\u001d\u001a\u00020\u001eH\u0086@\u00a2\u0006\u0002\u0010\u0017J>\u0010\u001f\u001a\u0002H\r\"\u0004\b\u0000\u0010\r2\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\r0\u0013H\u0082@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010 \u001a\u00020!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#H\u0086@\u00a2\u0006\u0002\u0010%J>\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020\u00032\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010*2\u0006\u0010+\u001a\u00020\u00032\n\b\u0002\u0010,\u001a\u0004\u0018\u00010-H\u0086@\u00a2\u0006\u0002\u0010.J*\u0010/\u001a\u0002002\u0006\u0010(\u001a\u00020\u00032\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010*H\u0086@\u00a2\u0006\u0002\u00102R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00064"}, d2 = {"Lcom/teracrafts/huefy/HuefyClient;", "", "apiKey", "", "(Ljava/lang/String;)V", "configuration", "Lcom/teracrafts/huefy/HuefyConfiguration;", "(Lcom/teracrafts/huefy/HuefyConfiguration;)V", "httpClient", "Lokhttp3/OkHttpClient;", "moshi", "Lcom/squareup/moshi/Moshi;", "executeRequest", "T", "endpoint", "method", "Lcom/teracrafts/huefy/HuefyClient$HttpMethod;", "body", "responseClass", "Ljava/lang/Class;", "(Ljava/lang/String;Lcom/teracrafts/huefy/HuefyClient$HttpMethod;Ljava/lang/Object;Ljava/lang/Class;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProviders", "Lcom/teracrafts/huefy/ProvidersResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "handleHttpError", "", "statusCode", "", "responseBody", "healthCheck", "Lcom/teracrafts/huefy/HealthResponse;", "performRequest", "sendBulkEmails", "Lcom/teracrafts/huefy/BulkEmailResponse;", "emails", "", "Lcom/teracrafts/huefy/SendEmailRequest;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendEmail", "Lcom/teracrafts/huefy/SendEmailResponse;", "templateKey", "data", "", "recipient", "provider", "Lcom/teracrafts/huefy/EmailProvider;", "(Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;Lcom/teracrafts/huefy/EmailProvider;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "validateTemplate", "Lcom/teracrafts/huefy/ValidateTemplateResponse;", "testData", "(Ljava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "HttpMethod", "huefy-android"})
public final class HuefyClient {
    @org.jetbrains.annotations.NotNull()
    private final com.teracrafts.huefy.HuefyConfiguration configuration = null;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient httpClient = null;
    @org.jetbrains.annotations.NotNull()
    private final com.squareup.moshi.Moshi moshi = null;
    
    public HuefyClient(@org.jetbrains.annotations.NotNull()
    com.teracrafts.huefy.HuefyConfiguration configuration) {
        super();
    }
    
    /**
     * Convenience constructor with API key
     */
    public HuefyClient(@org.jetbrains.annotations.NotNull()
    java.lang.String apiKey) {
        super();
    }
    
    /**
     * Send a single email using a template
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String templateKey, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> data, @org.jetbrains.annotations.NotNull()
    java.lang.String recipient, @org.jetbrains.annotations.Nullable()
    com.teracrafts.huefy.EmailProvider provider, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.teracrafts.huefy.SendEmailResponse> $completion) {
        return null;
    }
    
    /**
     * Send multiple emails in bulk
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendBulkEmails(@org.jetbrains.annotations.NotNull()
    java.util.List<com.teracrafts.huefy.SendEmailRequest> emails, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.teracrafts.huefy.BulkEmailResponse> $completion) {
        return null;
    }
    
    /**
     * Check API health status
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object healthCheck(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.teracrafts.huefy.HealthResponse> $completion) {
        return null;
    }
    
    /**
     * Validate a template with test data
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object validateTemplate(@org.jetbrains.annotations.NotNull()
    java.lang.String templateKey, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> testData, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.teracrafts.huefy.ValidateTemplateResponse> $completion) {
        return null;
    }
    
    /**
     * Get available email providers
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getProviders(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.teracrafts.huefy.ProvidersResponse> $completion) {
        return null;
    }
    
    private final <T extends java.lang.Object>java.lang.Object performRequest(java.lang.String endpoint, com.teracrafts.huefy.HuefyClient.HttpMethod method, java.lang.Object body, java.lang.Class<T> responseClass, kotlin.coroutines.Continuation<? super T> $completion) {
        return null;
    }
    
    private final <T extends java.lang.Object>java.lang.Object executeRequest(java.lang.String endpoint, com.teracrafts.huefy.HuefyClient.HttpMethod method, java.lang.Object body, java.lang.Class<T> responseClass, kotlin.coroutines.Continuation<? super T> $completion) {
        return null;
    }
    
    private final java.lang.Void handleHttpError(int statusCode, java.lang.String responseBody) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/teracrafts/huefy/HuefyClient$HttpMethod;", "", "(Ljava/lang/String;I)V", "GET", "POST", "PUT", "DELETE", "huefy-android"})
    static enum HttpMethod {
        /*public static final*/ GET /* = new GET() */,
        /*public static final*/ POST /* = new POST() */,
        /*public static final*/ PUT /* = new PUT() */,
        /*public static final*/ DELETE /* = new DELETE() */;
        
        HttpMethod() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.teracrafts.huefy.HuefyClient.HttpMethod> getEntries() {
            return null;
        }
    }
}