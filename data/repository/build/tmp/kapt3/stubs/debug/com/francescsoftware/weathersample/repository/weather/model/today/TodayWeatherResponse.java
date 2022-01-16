package com.francescsoftware.weathersample.repository.weather.model.today;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b:\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 c2\u00020\u0001:\u0002bcB\u00c7\u0001\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0001\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0001\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0001\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0001\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0001\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0001\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0001\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\u0010\b\u0001\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012\u0012\n\b\u0001\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u0012\n\b\u0001\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0001\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0001\u0010\u0018\u001a\u0004\u0018\u00010\u0015\u0012\n\b\u0001\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u0012\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c\u00a2\u0006\u0002\u0010\u001dB\u00b3\u0001\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\u0010\b\u0002\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0015\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u00a2\u0006\u0002\u0010\u001eJ\u000b\u0010F\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010G\u001a\u0004\u0018\u00010\u0015H\u00c6\u0003J\u0010\u0010H\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010(J\u0010\u0010I\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010(J\u000b\u0010J\u001a\u0004\u0018\u00010\u0015H\u00c6\u0003J\u000b\u0010K\u001a\u0004\u0018\u00010\u001aH\u00c6\u0003J\u0010\u0010L\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010(J\u0010\u0010M\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010(J\u000b\u0010N\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u000b\u0010O\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000b\u0010P\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u0010\u0010Q\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010(J\u000b\u0010R\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003J\u0011\u0010S\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012H\u00c6\u0003J\u00bc\u0001\u0010T\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0010\b\u0002\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00122\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00152\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u00c6\u0001\u00a2\u0006\u0002\u0010UJ\u0013\u0010V\u001a\u00020W2\b\u0010X\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010Y\u001a\u00020\u0003H\u00d6\u0001J\t\u0010Z\u001a\u00020\u0015H\u00d6\u0001J!\u0010[\u001a\u00020\\2\u0006\u0010]\u001a\u00020\u00002\u0006\u0010^\u001a\u00020_2\u0006\u0010`\u001a\u00020aH\u00c7\u0001R\u001e\u0010\u0018\u001a\u0004\u0018\u00010\u00158\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\u001e\u0010\n\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b#\u0010 \u001a\u0004\b$\u0010%R \u0010\u0016\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u0010)\u0012\u0004\b&\u0010 \u001a\u0004\b\'\u0010(R\u001e\u0010\u000f\u001a\u0004\u0018\u00010\u00108\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b*\u0010 \u001a\u0004\b+\u0010,R \u0010\u000e\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u0010)\u0012\u0004\b-\u0010 \u001a\u0004\b.\u0010(R \u0010\u0017\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u0010)\u0012\u0004\b/\u0010 \u001a\u0004\b0\u0010(R\u001e\u0010\b\u001a\u0004\u0018\u00010\t8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b1\u0010 \u001a\u0004\b2\u00103R\u001e\u0010\u0014\u001a\u0004\u0018\u00010\u00158\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b4\u0010 \u001a\u0004\b5\u0010\"R\u001e\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b6\u0010 \u001a\u0004\b7\u00108R\u001e\u0010\f\u001a\u0004\u0018\u00010\r8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b9\u0010 \u001a\u0004\b:\u0010;R \u0010\u0007\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u0010)\u0012\u0004\b<\u0010 \u001a\u0004\b=\u0010(R \u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u0010)\u0012\u0004\b>\u0010 \u001a\u0004\b?\u0010(R$\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00128\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b@\u0010 \u001a\u0004\bA\u0010BR\u001e\u0010\u0019\u001a\u0004\u0018\u00010\u001a8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\bC\u0010 \u001a\u0004\bD\u0010E\u00a8\u0006d"}, d2 = {"Lcom/francescsoftware/weathersample/repository/weather/model/today/TodayWeatherResponse;", "", "seen1", "", "rain", "Lcom/francescsoftware/weathersample/repository/weather/model/Rain;", "visibility", "timezone", "main", "Lcom/francescsoftware/weathersample/repository/weather/model/Main;", "clouds", "Lcom/francescsoftware/weathersample/repository/weather/model/Clouds;", "sys", "Lcom/francescsoftware/weathersample/repository/weather/model/Sys;", "dt", "coord", "Lcom/francescsoftware/weathersample/repository/weather/model/Coord;", "weather", "", "Lcom/francescsoftware/weathersample/repository/weather/model/WeatherItem;", "name", "", "cod", "id", "base", "wind", "Lcom/francescsoftware/weathersample/repository/weather/model/Wind;", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILcom/francescsoftware/weathersample/repository/weather/model/Rain;Ljava/lang/Integer;Ljava/lang/Integer;Lcom/francescsoftware/weathersample/repository/weather/model/Main;Lcom/francescsoftware/weathersample/repository/weather/model/Clouds;Lcom/francescsoftware/weathersample/repository/weather/model/Sys;Ljava/lang/Integer;Lcom/francescsoftware/weathersample/repository/weather/model/Coord;Ljava/util/List;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Lcom/francescsoftware/weathersample/repository/weather/model/Wind;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Lcom/francescsoftware/weathersample/repository/weather/model/Rain;Ljava/lang/Integer;Ljava/lang/Integer;Lcom/francescsoftware/weathersample/repository/weather/model/Main;Lcom/francescsoftware/weathersample/repository/weather/model/Clouds;Lcom/francescsoftware/weathersample/repository/weather/model/Sys;Ljava/lang/Integer;Lcom/francescsoftware/weathersample/repository/weather/model/Coord;Ljava/util/List;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Lcom/francescsoftware/weathersample/repository/weather/model/Wind;)V", "getBase$annotations", "()V", "getBase", "()Ljava/lang/String;", "getClouds$annotations", "getClouds", "()Lcom/francescsoftware/weathersample/repository/weather/model/Clouds;", "getCod$annotations", "getCod", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getCoord$annotations", "getCoord", "()Lcom/francescsoftware/weathersample/repository/weather/model/Coord;", "getDt$annotations", "getDt", "getId$annotations", "getId", "getMain$annotations", "getMain", "()Lcom/francescsoftware/weathersample/repository/weather/model/Main;", "getName$annotations", "getName", "getRain$annotations", "getRain", "()Lcom/francescsoftware/weathersample/repository/weather/model/Rain;", "getSys$annotations", "getSys", "()Lcom/francescsoftware/weathersample/repository/weather/model/Sys;", "getTimezone$annotations", "getTimezone", "getVisibility$annotations", "getVisibility", "getWeather$annotations", "getWeather", "()Ljava/util/List;", "getWind$annotations", "getWind", "()Lcom/francescsoftware/weathersample/repository/weather/model/Wind;", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Lcom/francescsoftware/weathersample/repository/weather/model/Rain;Ljava/lang/Integer;Ljava/lang/Integer;Lcom/francescsoftware/weathersample/repository/weather/model/Main;Lcom/francescsoftware/weathersample/repository/weather/model/Clouds;Lcom/francescsoftware/weathersample/repository/weather/model/Sys;Ljava/lang/Integer;Lcom/francescsoftware/weathersample/repository/weather/model/Coord;Ljava/util/List;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Lcom/francescsoftware/weathersample/repository/weather/model/Wind;)Lcom/francescsoftware/weathersample/repository/weather/model/today/TodayWeatherResponse;", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "repository_debug"})
@kotlinx.serialization.Serializable()
public final class TodayWeatherResponse {
    @org.jetbrains.annotations.NotNull()
    public static final com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse.Companion Companion = null;
    @org.jetbrains.annotations.Nullable()
    private final com.francescsoftware.weathersample.repository.weather.model.Rain rain = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer visibility = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer timezone = null;
    @org.jetbrains.annotations.Nullable()
    private final com.francescsoftware.weathersample.repository.weather.model.Main main = null;
    @org.jetbrains.annotations.Nullable()
    private final com.francescsoftware.weathersample.repository.weather.model.Clouds clouds = null;
    @org.jetbrains.annotations.Nullable()
    private final com.francescsoftware.weathersample.repository.weather.model.Sys sys = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer dt = null;
    @org.jetbrains.annotations.Nullable()
    private final com.francescsoftware.weathersample.repository.weather.model.Coord coord = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.francescsoftware.weathersample.repository.weather.model.WeatherItem> weather = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer cod = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer id = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String base = null;
    @org.jetbrains.annotations.Nullable()
    private final com.francescsoftware.weathersample.repository.weather.model.Wind wind = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse copy(@org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Rain rain, @org.jetbrains.annotations.Nullable()
    java.lang.Integer visibility, @org.jetbrains.annotations.Nullable()
    java.lang.Integer timezone, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Main main, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Clouds clouds, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Sys sys, @org.jetbrains.annotations.Nullable()
    java.lang.Integer dt, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Coord coord, @org.jetbrains.annotations.Nullable()
    java.util.List<com.francescsoftware.weathersample.repository.weather.model.WeatherItem> weather, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.Integer cod, @org.jetbrains.annotations.Nullable()
    java.lang.Integer id, @org.jetbrains.annotations.Nullable()
    java.lang.String base, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Wind wind) {
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
    com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse self, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.encoding.CompositeEncoder output, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.descriptors.SerialDescriptor serialDesc) {
    }
    
    public TodayWeatherResponse() {
        super();
    }
    
    public TodayWeatherResponse(@org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Rain rain, @org.jetbrains.annotations.Nullable()
    java.lang.Integer visibility, @org.jetbrains.annotations.Nullable()
    java.lang.Integer timezone, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Main main, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Clouds clouds, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Sys sys, @org.jetbrains.annotations.Nullable()
    java.lang.Integer dt, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Coord coord, @org.jetbrains.annotations.Nullable()
    java.util.List<com.francescsoftware.weathersample.repository.weather.model.WeatherItem> weather, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.Integer cod, @org.jetbrains.annotations.Nullable()
    java.lang.Integer id, @org.jetbrains.annotations.Nullable()
    java.lang.String base, @org.jetbrains.annotations.Nullable()
    com.francescsoftware.weathersample.repository.weather.model.Wind wind) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Rain component1() {
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
    public final java.lang.Integer component2() {
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
    public final java.lang.Integer component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getTimezone() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "timezone")
    @java.lang.Deprecated()
    public static void getTimezone$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Main component4() {
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
    public final com.francescsoftware.weathersample.repository.weather.model.Clouds component5() {
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
    public final com.francescsoftware.weathersample.repository.weather.model.Sys component6() {
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
    public final java.lang.Integer component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getDt() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "dt")
    @java.lang.Deprecated()
    public static void getDt$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Coord component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Coord getCoord() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "coord")
    @java.lang.Deprecated()
    public static void getCoord$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.francescsoftware.weathersample.repository.weather.model.WeatherItem> component9() {
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
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getName() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "name")
    @java.lang.Deprecated()
    public static void getName$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getCod() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "cod")
    @java.lang.Deprecated()
    public static void getCod$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getId() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "id")
    @java.lang.Deprecated()
    public static void getId$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBase() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "base")
    @java.lang.Deprecated()
    public static void getBase$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.francescsoftware.weathersample.repository.weather.model.Wind component14() {
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
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00c6\u0001\u00a8\u0006\u0006"}, d2 = {"Lcom/francescsoftware/weathersample/repository/weather/model/today/TodayWeatherResponse$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/francescsoftware/weathersample/repository/weather/model/today/TodayWeatherResponse;", "repository_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final kotlinx.serialization.KSerializer<com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse> serializer() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tH\u00d6\u0001\u00a2\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002H\u00d6\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VX\u00d6\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"com/francescsoftware/weathersample/repository/weather/model/today/TodayWeatherResponse.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/francescsoftware/weathersample/repository/weather/model/today/TodayWeatherResponse;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "repository_debug"})
    @java.lang.Deprecated()
    public static final class $serializer implements kotlinx.serialization.internal.GeneratedSerializer<com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse> {
        @org.jetbrains.annotations.NotNull()
        public static final com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse.$serializer INSTANCE = null;
        
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
        public com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse deserialize(@org.jetbrains.annotations.NotNull()
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
        com.francescsoftware.weathersample.repository.weather.model.today.TodayWeatherResponse value) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.KSerializer<?>[] typeParametersSerializers() {
            return null;
        }
    }
}