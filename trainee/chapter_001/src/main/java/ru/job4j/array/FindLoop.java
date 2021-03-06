package ru.job4j.array;

/**
 * Класс поиска значения в массиве.
 * @author Elena Kartashova (kartashova.ee@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class FindLoop {

    /**
     * Метод простейшего поиска значения в массиве.
     * @param data массив, в котором производится поиск.
     * @param elem искомое значение.
     * @return индекс, по которому элемент найден в массиве, -1 если не найден.
     */
    public int indexOf(int[] data, int elem) {
        int res = -1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == elem) {
                res = i;
                break;
            }
        }
        return res;
    }
}