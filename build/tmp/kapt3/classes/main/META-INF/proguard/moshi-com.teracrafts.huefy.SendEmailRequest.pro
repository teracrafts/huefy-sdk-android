-if class com.teracrafts.huefy.SendEmailRequest
-keepnames class com.teracrafts.huefy.SendEmailRequest
-if class com.teracrafts.huefy.SendEmailRequest
-keep class com.teracrafts.huefy.SendEmailRequestJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.teracrafts.huefy.SendEmailRequest
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-if class com.teracrafts.huefy.SendEmailRequest
-keepclassmembers class com.teracrafts.huefy.SendEmailRequest {
    public synthetic <init>(java.lang.String,java.util.Map,java.lang.String,com.teracrafts.huefy.EmailProvider,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
