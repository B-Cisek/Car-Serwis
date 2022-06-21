package car.serwis.helpers;

/**
 * Typ wyliczeniowy statusu zlecenia
 */
public enum ZlecenieStatus {
    /** Nowe zlecenie */
    NOWE,

    /** Zlecenie oczekujące na mechanika */
    OCZEKUJACE,

    /** Zlecenie w trakcie naprawy */
    W_TRAKCIE,

    /** Zlecenie gotowe: samochód gotowy do odbioru */
    GOTOWE;
}
