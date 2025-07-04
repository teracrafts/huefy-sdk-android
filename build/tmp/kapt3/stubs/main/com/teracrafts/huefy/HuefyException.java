package com.teracrafts.huefy;

/**
 * Base exception class for all Huefy SDK errors
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00060\u0001j\u0002`\u0002:\u000b\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012B\u001b\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007\u0082\u0001\u000b\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u00a8\u0006\u001e"}, d2 = {"Lcom/teracrafts/huefy/HuefyException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "message", "", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "DecodingError", "EncodingError", "InvalidApiKey", "InvalidUrl", "NetworkError", "ProviderError", "RateLimitExceeded", "ServerError", "TemplateNotFound", "Unknown", "ValidationError", "Lcom/teracrafts/huefy/HuefyException$DecodingError;", "Lcom/teracrafts/huefy/HuefyException$EncodingError;", "Lcom/teracrafts/huefy/HuefyException$InvalidApiKey;", "Lcom/teracrafts/huefy/HuefyException$InvalidUrl;", "Lcom/teracrafts/huefy/HuefyException$NetworkError;", "Lcom/teracrafts/huefy/HuefyException$ProviderError;", "Lcom/teracrafts/huefy/HuefyException$RateLimitExceeded;", "Lcom/teracrafts/huefy/HuefyException$ServerError;", "Lcom/teracrafts/huefy/HuefyException$TemplateNotFound;", "Lcom/teracrafts/huefy/HuefyException$Unknown;", "Lcom/teracrafts/huefy/HuefyException$ValidationError;", "huefy-android"})
public abstract class HuefyException extends java.lang.Exception {
    
    private HuefyException(java.lang.String message, java.lang.Throwable cause) {
        super();
    }
    
    /**
     * Failed to decode response
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/teracrafts/huefy/HuefyException$DecodingError;", "Lcom/teracrafts/huefy/HuefyException;", "message", "", "(Ljava/lang/String;)V", "huefy-android"})
    public static final class DecodingError extends com.teracrafts.huefy.HuefyException {
        
        public DecodingError(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
        }
    }
    
    /**
     * Failed to encode request
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/teracrafts/huefy/HuefyException$EncodingError;", "Lcom/teracrafts/huefy/HuefyException;", "message", "", "(Ljava/lang/String;)V", "huefy-android"})
    public static final class EncodingError extends com.teracrafts.huefy.HuefyException {
        
        public EncodingError(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
        }
    }
    
    /**
     * Invalid API key provided
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/teracrafts/huefy/HuefyException$InvalidApiKey;", "Lcom/teracrafts/huefy/HuefyException;", "()V", "huefy-android"})
    public static final class InvalidApiKey extends com.teracrafts.huefy.HuefyException {
        @org.jetbrains.annotations.NotNull()
        public static final com.teracrafts.huefy.HuefyException.InvalidApiKey INSTANCE = null;
        
        private InvalidApiKey() {
        }
    }
    
    /**
     * Invalid URL configuration
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/teracrafts/huefy/HuefyException$InvalidUrl;", "Lcom/teracrafts/huefy/HuefyException;", "()V", "huefy-android"})
    public static final class InvalidUrl extends com.teracrafts.huefy.HuefyException {
        @org.jetbrains.annotations.NotNull()
        public static final com.teracrafts.huefy.HuefyException.InvalidUrl INSTANCE = null;
        
        private InvalidUrl() {
        }
    }
    
    /**
     * Network error occurred
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/teracrafts/huefy/HuefyException$NetworkError;", "Lcom/teracrafts/huefy/HuefyException;", "cause", "", "(Ljava/lang/Throwable;)V", "huefy-android"})
    public static final class NetworkError extends com.teracrafts.huefy.HuefyException {
        
        public NetworkError(@org.jetbrains.annotations.NotNull()
        java.lang.Throwable cause) {
        }
    }
    
    /**
     * Provider error
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\t"}, d2 = {"Lcom/teracrafts/huefy/HuefyException$ProviderError;", "Lcom/teracrafts/huefy/HuefyException;", "provider", "", "code", "(Ljava/lang/String;Ljava/lang/String;)V", "getCode", "()Ljava/lang/String;", "getProvider", "huefy-android"})
    public static final class ProviderError extends com.teracrafts.huefy.HuefyException {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String provider = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String code = null;
        
        public ProviderError(@org.jetbrains.annotations.NotNull()
        java.lang.String provider, @org.jetbrains.annotations.Nullable()
        java.lang.String code) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getProvider() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getCode() {
            return null;
        }
    }
    
    /**
     * Rate limit exceeded
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\b"}, d2 = {"Lcom/teracrafts/huefy/HuefyException$RateLimitExceeded;", "Lcom/teracrafts/huefy/HuefyException;", "retryAfter", "", "(Ljava/lang/Long;)V", "getRetryAfter", "()Ljava/lang/Long;", "Ljava/lang/Long;", "huefy-android"})
    public static final class RateLimitExceeded extends com.teracrafts.huefy.HuefyException {
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long retryAfter = null;
        
        public RateLimitExceeded(@org.jetbrains.annotations.Nullable()
        java.lang.Long retryAfter) {
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long getRetryAfter() {
            return null;
        }
        
        public RateLimitExceeded() {
        }
    }
    
    /**
     * Server error
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/teracrafts/huefy/HuefyException$ServerError;", "Lcom/teracrafts/huefy/HuefyException;", "statusCode", "", "responseMessage", "", "(ILjava/lang/String;)V", "getResponseMessage", "()Ljava/lang/String;", "getStatusCode", "()I", "huefy-android"})
    public static final class ServerError extends com.teracrafts.huefy.HuefyException {
        private final int statusCode = 0;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String responseMessage = null;
        
        public ServerError(int statusCode, @org.jetbrains.annotations.Nullable()
        java.lang.String responseMessage) {
        }
        
        public final int getStatusCode() {
            return 0;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getResponseMessage() {
            return null;
        }
    }
    
    /**
     * Template not found
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/teracrafts/huefy/HuefyException$TemplateNotFound;", "Lcom/teracrafts/huefy/HuefyException;", "templateKey", "", "(Ljava/lang/String;)V", "huefy-android"})
    public static final class TemplateNotFound extends com.teracrafts.huefy.HuefyException {
        
        public TemplateNotFound(@org.jetbrains.annotations.NotNull()
        java.lang.String templateKey) {
        }
    }
    
    /**
     * Unknown error
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/teracrafts/huefy/HuefyException$Unknown;", "Lcom/teracrafts/huefy/HuefyException;", "message", "", "(Ljava/lang/String;)V", "huefy-android"})
    public static final class Unknown extends com.teracrafts.huefy.HuefyException {
        
        public Unknown(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
        }
    }
    
    /**
     * Validation error
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\b\u0002\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0007R\u001f\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2 = {"Lcom/teracrafts/huefy/HuefyException$ValidationError;", "Lcom/teracrafts/huefy/HuefyException;", "message", "", "details", "", "", "(Ljava/lang/String;Ljava/util/Map;)V", "getDetails", "()Ljava/util/Map;", "huefy-android"})
    public static final class ValidationError extends com.teracrafts.huefy.HuefyException {
        @org.jetbrains.annotations.Nullable()
        private final java.util.Map<java.lang.String, java.lang.Object> details = null;
        
        public ValidationError(@org.jetbrains.annotations.NotNull()
        java.lang.String message, @org.jetbrains.annotations.Nullable()
        java.util.Map<java.lang.String, ? extends java.lang.Object> details) {
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.util.Map<java.lang.String, java.lang.Object> getDetails() {
            return null;
        }
    }
}