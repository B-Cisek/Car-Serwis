package car.serwis.helpers;

/**
 * Typ wyliczeniowy uprawnień użytkownika
 */
public enum UsersPermissions {
    /** Dostęp do wszystkich paneli aplikacji */
    ADMIN,

    /** Dostęp do  paneli przeznaczonych dla mechanika */
    MECHANIK,

    /** Dostęp do  paneli przeznaczonych dla magazyniera */
    MAGAZYNIER,

    /** Dostęp do  paneli przeznaczonych dla obsługi klienta */
    OBSLUGA_KLIENTA
}
