package com.spring.mvc.tabs;

public enum TabsEnum {
    HOME("home"),
    CATEGORY("category"),
    RECENT("recent");
    private final String tabName;

    TabsEnum(String tabName) {
        this.tabName = tabName;
    }

    @Override
    public String toString() {
        return this.tabName;
    }
}
