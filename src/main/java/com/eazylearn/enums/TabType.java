package com.eazylearn.enums;

public enum TabType { // todo maybe get rid of it

    HOME("home"),
    CATEGORY("category"),
    RECENT("recent");

    private final String tabName;

    TabType(String tabName) {
        this.tabName = tabName;
    }

    @Override
    public String toString() {
        return this.tabName;
    }
}
