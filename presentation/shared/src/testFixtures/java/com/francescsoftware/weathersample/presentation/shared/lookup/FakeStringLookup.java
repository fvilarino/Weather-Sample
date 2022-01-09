package com.francescsoftware.weathersample.presentation.shared.lookup;

import org.jetbrains.annotations.NotNull;

public class FakeStringLookup implements StringLookup {
    public String result = "";

    @NotNull
    @Override
    public String getString(int id) {
        return result;
    }

    @NotNull
    @Override
    public String getString(int id, @NotNull Object... formatArgs) {
        return result;
    }
}
