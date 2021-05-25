package eazy.learn.enums;

public enum TabType {
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
