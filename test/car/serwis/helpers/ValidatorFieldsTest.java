package car.serwis.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidatorFieldsTest {

    @Test
    void isKodPocztowy() {
        assertEquals(true,ValidatorFields.isKodPocztowy("36-050"));
        assertEquals(false,ValidatorFields.isKodPocztowy("36050"));
        assertEquals(false,ValidatorFields.isKodPocztowy("00000"));
        assertEquals(true,ValidatorFields.isKodPocztowy("99-999"));
    }

    @Test
    void isText() {
        assertEquals(true,ValidatorFields.isText("Zażółć gęślą jaźń"));
        assertEquals(true,ValidatorFields.isText("Zażółćgęśląjaźń"));
        assertEquals(false,ValidatorFields.isText("Zażółć gęśląjaźń123"));
    }

    @Test
    void isPesel() {
        assertEquals(true,ValidatorFields.isPesel("99092100000"));
        assertEquals(false,ValidatorFields.isPesel("990921"));
        assertEquals(false,ValidatorFields.isPesel("99092132344444"));
    }

    @Test
    void isCorrectPassword() {
        assertEquals(true,ValidatorFields.isCorrectPassword("admin"));
        assertEquals(false,ValidatorFields.isCorrectPassword("test"));
        assertEquals(false,ValidatorFields.isCorrectPassword("admin dasd"));
        assertEquals(true,ValidatorFields.isCorrectPassword("Adm!n123"));
        assertEquals(false,ValidatorFields.isCorrectPassword("!@#"));
        assertEquals(false,ValidatorFields.isCorrectPassword("dhvldyernd34ksdcv213kdjkerwerewdasdir"));
        assertEquals(true,ValidatorFields.isCorrectPassword("1233487!@#"));
    }
}