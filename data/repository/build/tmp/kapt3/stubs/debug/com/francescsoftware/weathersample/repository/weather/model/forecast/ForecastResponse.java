package com.francescsoftware.weathersample.repository.weather.model.forecast;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 52\u00020\u0001:\u000245B[\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0001\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0001\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0001\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\u0010\b\u0001\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\u0002\u0010\u000fBG\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\u0010J\u000b\u0010!\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010\"\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0017J\u000b\u0010#\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\u0010\u0010$\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0017J\u0011\u0010%\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bH\u00c6\u0003JP\u0010&\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bH\u00c6\u0001\u00a2\u0006\u0002\u0010\'J\u0013\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010+\u001a\u00020\u0003H\u00d6\u0001J\t\u0010,\u001a\u00020\bH\u00d6\u0001J!\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u00002\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u00c7\u0001R\u001e\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R \u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u0010\u0018\u0012\u0004\b\u0015\u0010\u0012\u001a\u0004\b\u0016\u0010\u0017R\u001e\u0010\u0007\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0019\u0010\u0012\u001a\u0004\b\u001a\u0010\u001bR$\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u001c\u0010\u0012\u001a\u0004\b\u001d\u0010\u001eR \u0010\t\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u0010\u0018\u0012\u0004\b\u001f\u0010\u0012\u001a\u0004\b \u0010\u0017\u00a8\u00066"}, d2 = {"Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastResponse;", "", "seen1", "", "city", "Lcom/francescsoftware/weathersample/repository/weather/model/forecast/City;", "cnt", "cod", "", "message", "forecast", "", "Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastItem;", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILcom/francescsoftware/weathersample/repository/weather/model/forecast/City;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Lcom/francescsoftware/weathersample/repository/weather/model/forecast/City;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/util/List;)V", "getCity$annotations", "()V", "getCity", "()Lcom/francescsoftware/weathersample/repository/weather/model/forecast/City;", "getCnt$annotations", "getCnt", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getCod$annotations", "getCod", "()Ljava/lang/String;", "getForecast$annotations", "getForecast", "()Ljava/util/List;", "getMessage$annotations", "getMessage", "component1", "component2", "component3", "component4", "component5", "copy", "(Lcom/francescsoftware/weathersample/repository/weather/model/forecast/City;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/util/List;)Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastResponse;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "repository_debug"})
@kotlinx.serialization.Serializable()
public final class ForecastResponse {
    @org.jetbrains.annotations.NotNull()
    public static final com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastResponse.Companion Companion = null;
    @org.jetbrains.annotations.Nullable()
    private final com.francescsoftware.weathersample.repository.weather.model.forecast.City city = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer cnt = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String cod = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer message = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem> forecast = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastResponse copy(@org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.forecast.City city, @org.jetbrains.annotations.Nullable()
    java.lang.Integer cnt, @org.jetbrains.annotations.Nullable()
    java.lang.String cod, @org.jetbrains.annotations.Nullable()
    java.lang.Integer message, @org.jetbrains.annotations.Nullable()
    java.util.List<com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem> forecast) {
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
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.jvm.JvmStatic()
    public static final void write$Self(@org.jetbrains.annotations.NotNull()
    com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastResponse self, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.encoding.CompositeEncoder output, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.descriptors.SerialDescriptor serialDesc) {
    }
    
    public ForecastResponse() {
        super();
    }
    
    public ForecastResponse(@org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.forecast.City city, @org.jetbrains.annotations.Nullable()
    java.lang.Integer cnt, @org.jetbrains.annotations.Nullable()
    java.lang.String cod, @org.jetbrains.annotations.Nullable()
    java.lang.Integer message, @org.jetbrains.annotations.Nullable()
    java.util.List<com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem> forecast) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.forecast.City component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.forecast.City getCity() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "city")
    @java.lang.Deprecated()
    public static void getCity$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getCnt() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "cnt")
    @java.lang.Deprecated()
    public static void getCnt$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCod() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "cod")
    @java.lang.Deprecated()
    public static void getCod$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getMessage() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "message")
    @java.lang.Deprecated()
    public static void getMessage$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem> getForecast() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "list")
    @java.lang.Deprecated()
    public static void getForecast$annotations() {
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00c6\u0001\u00a8\u0006\u0006"}, d2 = {"Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastResponse$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastResponse;", "repository_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final kotlinx.serialization.KSerializer<com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastResponse> serializer() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tH\u00d6\u0001\u00a2\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002H\u00d6\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VX\u00d6\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"com/francescsoftware/weathersample/repository/weather/model/forecast/ForecastResponse.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastResponse;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "repository_debug"})
    @java.lang.Deprecated()
    public static final class $serializer implements kotlinx.serialization.internal.GeneratedSerializer<com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastResponse> {
        @org.jetbrains.annotations.NotNull()
        public static final com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastResponse.$serializer INSTANCE = null;
        
        private $serializer() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public kotlinx.serialization.KSerializer<?>[] childSerializers() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastResponse deserialize(@org.jetbrains.annotations.NotNull()
        kotlinx.serialization.encoding.Decoder decoder) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public kotlinx.serialization.descriptors.SerialDescriptor getDescriptor() {
            return null;
        }
        
        @java.lang.Override()
        public void serialize(@org.jetbrains.annotations.NotNull()
        kotlinx.serialization.encoding.Encoder encoder, @org.jetbrains.annotations.NotNull()
        com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastResponse value) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.KSerializer<?>[] typeParametersSerializers() {
            return null;
        }
    }
}