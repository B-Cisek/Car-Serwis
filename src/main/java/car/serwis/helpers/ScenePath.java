package car.serwis.helpers;

public enum ScenePath {

    PULPIT("/fxml/pulpit.fxml"),
    ZLECENIA("/fxml/zlecenia.fxml"),
    WARSZTAT("/fxml/warsztat.fxml"),
    KSIEKOWOSC("/fxml/ksiegowosc.fxml"),
    MAGAZYN("/fxml/magazyn.fxml"),
    USTAWIENIA("/fxml/ustawienia.fxml"),
    POMOC("/fxml/pomoc.fxml"),
    LOGIN("/fxml/login.fxml"),
    ADD_STANOWISKO("/fxml/addStanowisko.fxml"),
    ADD_PRACOWNIK("/fxml/addPracownik.fxml"),
    ADD_ZLECENIE("/fxml/addZlecenie.fxml"),
    ADD_SAMOCHOD("/fxml/addSamochod.fxml"),
    ADD_KATEGORIA("/fxml/addKategoria.fxml"),
    ADD_JEDNOSTKA("/fxml/addJednostka.fxml"),
    ADD_KONTRAHENT("/fxml/addKontrahent.fxml"),
    ADD_CZESC("/fxml/addCzesc.fxml"),
    UPDATE_STATUS("/fxml/updateStatus.fxml");

    private final String path;

    ScenePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
