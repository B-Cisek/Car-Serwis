package car.serwis.helpers;

public enum ScenePath {

    APP("/fxml/app.fxml"),
    LOGIN("/fxml/login.fxml"),
    ADD_STANOWISKO("/fxml/addStanowisko.fxml");

    private final String path;

    ScenePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
