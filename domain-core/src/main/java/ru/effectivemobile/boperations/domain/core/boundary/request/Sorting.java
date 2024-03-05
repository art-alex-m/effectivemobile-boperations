package ru.effectivemobile.boperations.domain.core.boundary.request;

/**
 * Интерфейс сортировки
 */
public interface Sorting {

    int ASC = 1;

    int DESC = -1;

    /**
     * Имя поля сортировки
     */
    String getField();

    /**
     * Возвращает направление сортировки
     * Если больше нуля, то сортировка по возрастанию. Если ноль или меньше, то по убыванию
     *
     * @return положительное или отрицательное число
     */
    int getAsc();
}
