package car.serwis.helpers;

public enum ScenePath {

    APP("/fxml/app.fxml"),
    PULPIT("/fxml/pulpit.fxml"),
    ZLECENIA("/fxml/zlecenia.fxml"),
    WARSZTAT("/fxml/warsztat.fxml"),
    KSIEKOWOSC("/fxml/ksiegowosc.fxml"),
    MAGAZYN("/fxml/magazyn.fxml"),
    USTAWIENIA("/fxml/ustawienia.fxml"),
    POMOC("/fxml/pomoc.fxml"),
    LOGIN("/fxml/login.fxml"),
    ADD_STANOWISKO("/fxml/addStanowisko.fxml"),
    ADD_PRACOWNIK("/fxml/addPracownik.fxml");

    private final String path;

    ScenePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
