package car.serwis.helpers;

/**
 * Typ wyliczeniowy ścieżek do plików FXML
 */
public enum ScenePath {
    PULPIT("/fxml/pulpit.fxml"),
    ZLECENIA("/fxml/zlecenia.fxml"),
    WARSZTAT("/fxml/warsztat.fxml"),
    KSIEGOWOSC("/fxml/ksiegowosc.fxml"),
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
    ADD_NEW_CZESC("/fxml/addNewCzesc.fxml"),
    UPDATE_STATUS("/fxml/updateStatus.fxml"),
    ADD_FAKTURA("/fxml/addFaktura.fxml"),
    ADD_POZYCJA("/fxml/addPozycja.fxml"),
    SHOW_FAKTURA("/fxml/showFaktura.fxml"),
    UPDATE_CZESC("/fxml/updateCzesc.fxml"),
    ADD_CZESC("/fxml/addCzesc.fxml"),
    SHOW_ZLECENIE("/fxml/showZlecenie.fxml"),
    SHOW_KONTRAHENT("/fxml/showKontrahent.fxml"),
    SHOW_ZLECENIE_FOR_MECHANIK("/fxml/showZlecenieForMechanik.fxml");

    private final String path;

    ScenePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
