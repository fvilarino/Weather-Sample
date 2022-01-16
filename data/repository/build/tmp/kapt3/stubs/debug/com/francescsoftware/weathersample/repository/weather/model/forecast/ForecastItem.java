package com.francescsoftware.weathersample.repository.weather.model.forecast;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b3\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 Z2\u00020\u0001:\u0002YZB\u00a3\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0001\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0001\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0001\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0001\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0010\b\u0001\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b\u0012\n\b\u0001\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0001\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0001\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u0012\n\b\u0001\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u0012\n\b\u0001\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u0012\n\b\u0001\u0010\u0017\u001a\u0004\u0018\u00010\u0018\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u00a2\u0006\u0002\u0010\u001bB\u008f\u0001\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018\u00a2\u0006\u0002\u0010\u001cJ\u0010\u0010@\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010&J\u000b\u0010A\u001a\u0004\u0018\u00010\u0016H\u00c6\u0003J\u000b\u0010B\u001a\u0004\u0018\u00010\u0018H\u00c6\u0003J\u0010\u0010C\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010-J\u0010\u0010D\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010&J\u000b\u0010E\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u0011\u0010F\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bH\u00c6\u0003J\u000b\u0010G\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\u000b\u0010H\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003J\u000b\u0010I\u001a\u0004\u0018\u00010\u0012H\u00c6\u0003J\u000b\u0010J\u001a\u0004\u0018\u00010\u0014H\u00c6\u0003J\u0098\u0001\u0010K\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00162\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u00c6\u0001\u00a2\u0006\u0002\u0010LJ\u0013\u0010M\u001a\u00020N2\b\u0010O\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010P\u001a\u00020\u0003H\u00d6\u0001J\t\u0010Q\u001a\u00020\tH\u00d6\u0001J!\u0010R\u001a\u00020S2\u0006\u0010T\u001a\u00020\u00002\u0006\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00020XH\u00c7\u0001R\u001e\u0010\u000f\u001a\u0004\u0018\u00010\u00108\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u001e\u0010\b\u001a\u0004\u0018\u00010\t8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b!\u0010\u001e\u001a\u0004\b\"\u0010#R \u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u0010\'\u0012\u0004\b$\u0010\u001e\u001a\u0004\b%\u0010&R\u001e\u0010\r\u001a\u0004\u0018\u00010\u000e8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b(\u0010\u001e\u001a\u0004\b)\u0010*R \u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u0010.\u0012\u0004\b+\u0010\u001e\u001a\u0004\b,\u0010-R\u001e\u0010\u0015\u001a\u0004\u0018\u00010\u00168\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b/\u0010\u001e\u001a\u0004\b0\u00101R\u001e\u0010\u0017\u001a\u0004\u0018\u00010\u00188\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b2\u0010\u001e\u001a\u0004\b3\u00104R\u001e\u0010\u0011\u001a\u0004\u0018\u00010\u00128\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b5\u0010\u001e\u001a\u0004\b6\u00107R \u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u0010\'\u0012\u0004\b8\u0010\u001e\u001a\u0004\b9\u0010&R$\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b:\u0010\u001e\u001a\u0004\b;\u0010<R\u001e\u0010\u0013\u001a\u0004\u0018\u00010\u00148\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b=\u0010\u001e\u001a\u0004\b>\u0010?\u00a8\u0006["}, d2 = {"Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastItem;", "", "seen1", "", "epoch", "pop", "", "visibility", "dtTxt", "", "weather", "", "Lcom/francescsoftware/weathersample/repository/weather/model/WeatherItem;", "main", "Lcom/francescsoftware/weathersample/repository/weather/model/Main;", "clouds", "Lcom/francescsoftware/weathersample/repository/weather/model/Clouds;", "sys", "Lcom/francescsoftware/weathersample/repository/weather/model/Sys;", "wind", "Lcom/francescsoftware/weathersample/repository/weather/model/Wind;", "rain", "Lcom/francescsoftware/weathersample/repository/weather/model/Rain;", "snow", "Lcom/francescsoftware/weathersample/repository/weather/model/Snow;", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Integer;Ljava/lang/Double;Ljava/lang/Integer;Ljava/lang/String;Ljava/util/List;Lcom/francescsoftware/weathersample/repository/weather/model/Main;Lcom/francescsoftware/weathersample/repository/weather/model/Clouds;Lcom/francescsoftware/weathersample/repository/weather/model/Sys;Lcom/francescsoftware/weathersample/repository/weather/model/Wind;Lcom/francescsoftware/weathersample/repository/weather/model/Rain;Lcom/francescsoftware/weathersample/repository/weather/model/Snow;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Integer;Ljava/lang/Double;Ljava/lang/Integer;Ljava/lang/String;Ljava/util/List;Lcom/francescsoftware/weathersample/repository/weather/model/Main;Lcom/francescsoftware/weathersample/repository/weather/model/Clouds;Lcom/francescsoftware/weathersample/repository/weather/model/Sys;Lcom/francescsoftware/weathersample/repository/weather/model/Wind;Lcom/francescsoftware/weathersample/repository/weather/model/Rain;Lcom/francescsoftware/weathersample/repository/weather/model/Snow;)V", "getClouds$annotations", "()V", "getClouds", "()Lcom/francescsoftware/weathersample/repository/weather/model/Clouds;", "getDtTxt$annotations", "getDtTxt", "()Ljava/lang/String;", "getEpoch$annotations", "getEpoch", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getMain$annotations", "getMain", "()Lcom/francescsoftware/weathersample/repository/weather/model/Main;", "getPop$annotations", "getPop", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getRain$annotations", "getRain", "()Lcom/francescsoftware/weathersample/repository/weather/model/Rain;", "getSnow$annotations", "getSnow", "()Lcom/francescsoftware/weathersample/repository/weather/model/Snow;", "getSys$annotations", "getSys", "()Lcom/francescsoftware/weathersample/repository/weather/model/Sys;", "getVisibility$annotations", "getVisibility", "getWeather$annotations", "getWeather", "()Ljava/util/List;", "getWind$annotations", "getWind", "()Lcom/francescsoftware/weathersample/repository/weather/model/Wind;", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/Double;Ljava/lang/Integer;Ljava/lang/String;Ljava/util/List;Lcom/francescsoftware/weathersample/repository/weather/model/Main;Lcom/francescsoftware/weathersample/repository/weather/model/Clouds;Lcom/francescsoftware/weathersample/repository/weather/model/Sys;Lcom/francescsoftware/weathersample/repository/weather/model/Wind;Lcom/francescsoftware/weathersample/repository/weather/model/Rain;Lcom/francescsoftware/weathersample/repository/weather/model/Snow;)Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastItem;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "repository_debug"})
@kotlinx.serialization.Serializable()
public final class ForecastItem {
    @org.jetbrains.annotations.NotNull()
    public static final com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem.Companion Companion = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer epoch = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double pop = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer visibility = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String dtTxt = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.francescsoftware.weathersample.repository.weather.model.WeatherItem> weather = null;
    @org.jetbrains.annotations.Nullable()
    private final com.francescsoftware.weathersample.repository.weather.model.Main main = null;
    @org.jetbrains.annotations.Nullable()
    private final com.francescsoftware.weathersample.repository.weather.model.Clouds clouds = null;
    @org.jetbrains.annotations.Nullable()
    private final com.francescsoftware.weathersample.repository.weather.model.Sys sys = null;
    @org.jetbrains.annotations.Nullable()
    private final com.francescsoftware.weathersample.repository.weather.model.Wind wind = null;
    @org.jetbrains.annotations.Nullable()
    private final com.francescsoftware.weathersample.repository.weather.model.Rain rain = null;
    @org.jetbrains.annotations.Nullable()
    private final com.francescsoftware.weathersample.repository.weather.model.Snow snow = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem copy(@org.jetbrains.annotations.Nullable()
    java.lang.Integer epoch, @org.jetbrains.annotations.Nullable()
    java.lang.Double pop, @org.jetbrains.annotations.Nullable()
    java.lang.Integer visibility, @org.jetbrains.annotations.Nullable()
    java.lang.String dtTxt, @org.jetbrains.annotations.Nullable()
    java.util.List<com.francescsoftware.weathersample.repository.weather.model.WeatherItem> weather, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Main main, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Clouds clouds, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Sys sys, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Wind wind, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Rain rain, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Snow snow) {
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
    com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem self, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.encoding.CompositeEncoder output, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.descriptors.SerialDescriptor serialDesc) {
    }
    
    public ForecastItem() {
        super();
    }
    
    public ForecastItem(@org.jetbrains.annotations.Nullable()
    java.lang.Integer epoch, @org.jetbrains.annotations.Nullable()
    java.lang.Double pop, @org.jetbrains.annotations.Nullable()
    java.lang.Integer visibility, @org.jetbrains.annotations.Nullable()
    java.lang.String dtTxt, @org.jetbrains.annotations.Nullable()
    java.util.List<com.francescsoftware.weathersample.repository.weather.model.WeatherItem> weather, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Main main, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Clouds clouds, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Sys sys, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Wind wind, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Rain rain, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Snow snow) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getEpoch() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "dt")
    @java.lang.Deprecated()
    public static void getEpoch$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPop() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "pop")
    @java.lang.Deprecated()
    public static void getPop$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getVisibility() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "visibility")
    @java.lang.Deprecated()
    public static void getVisibility$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDtTxt() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "dt_txt")
    @java.lang.Deprecated()
    public static void getDtTxt$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.francescsoftware.weathersample.repository.weather.model.WeatherItem> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.francescsoftware.weathersample.repository.weather.model.WeatherItem> getWeather() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "weather")
    @java.lang.Deprecated()
    public static void getWeather$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Main component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Main getMain() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "main")
    @java.lang.Deprecated()
    public static void getMain$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Clouds component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Clouds getClouds() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "clouds")
    @java.lang.Deprecated()
    public static void getClouds$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Sys component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Sys getSys() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "sys")
    @java.lang.Deprecated()
    public static void getSys$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Wind component9() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Wind getWind() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "wind")
    @java.lang.Deprecated()
    public static void getWind$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Rain component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Rain getRain() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "rain")
    @java.lang.Deprecated()
    public static void getRain$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Snow component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Snow getSnow() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "snow")
    @java.lang.Deprecated()
    public static void getSnow$annotations() {
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00c6\u0001\u00a8\u0006\u0006"}, d2 = {"Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastItem$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastItem;", "repository_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final kotlinx.serialization.KSerializer<com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem> serializer() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tH\u00d6\u0001\u00a2\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002H\u00d6\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VX\u00d6\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"com/francescsoftware/weathersample/repository/weather/model/forecast/ForecastItem.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/francescsoftware/weathersample/repository/weather/model/forecast/ForecastItem;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "repository_debug"})
    @java.lang.Deprecated()
    public static final class $serializer implements kotlinx.serialization.internal.GeneratedSerializer<com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem> {
        @org.jetbrains.annotations.NotNull()
        public static final com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem.$serializer INSTANCE = null;
        
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
        public com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem deserialize(@org.jetbrains.annotations.NotNull()
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
        com.francescsoftware.weathersample.repository.weather.model.forecast.ForecastItem value) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.KSerializer<?>[] typeParametersSerializers() {
            return null;
        }
    }
}