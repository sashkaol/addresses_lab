package com.university.arrays.matrix.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.university.arrays.matrix.utils.ArrayOperations;

import java.util.*;

@Slf4j
@Service
public class MatrixService {

    ArrayOperations arrayOperations = new ArrayOperations();

    public int getNonZeroLineCount(List<List<Double>>  matrix) {
        int rows = matrix.size();
        int cols = matrix.get(0).size();

        int result = 0;
        boolean flag = false;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // если встречаем первый 0 по строке выходим из цикла
                if (matrix.get(i).get(j) == 0) {
                    flag = true;
                    break;
                }
            }

            // проверяем, если нулей не было - +1 к количеству строк без нулец
            if (!flag) {
                log.info("Строка {} не содержит нулей", i);

                result++;
            }

            flag = false;

        }

        return result;

    }

    public double getMaxNumber(List<List<Double>> matrix) {
        // составляем мапу, где ключ - [i,j] элемент матрицы, значение - число его вхождений в матрицу
        HashMap<Double, Integer> numbersMap = makeKeyEntranceCountMap(matrix);
        // достаем список элементов встречающихся в матрице более 1го раза
        ArrayList<Double> nonUniqValues = getNonUniqValueList(numbersMap);
        // достаем из списка максимальное значение
        double res = arrayOperations.max(nonUniqValues);

        log.info("Максимальное число в списке {} - {}", nonUniqValues, res);

        return res;
    }

    private ArrayList<Double> getNonUniqValueList(HashMap<Double, Integer> map) {
        ArrayList<Double> nonUniqValues = new ArrayList<>();

        // проходимся по парам ключ-значение и добавляем в список только те ключи, у которых значение > 1
        for (Map.Entry<Double, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 1) {
                Double key = entry.getKey();
                nonUniqValues.add(key);
                log.info("Ключ {} встречается в матрице более одного раза", key);
            }
        }

        return nonUniqValues;
    }

    private HashMap<Double, Integer> makeKeyEntranceCountMap(List<List<Double>> matrix) {

        int rows = matrix.size();
        int cols = matrix.get(0).size();

        HashMap<Double, Integer> numbersMap = new HashMap<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double key = matrix.get(i).get(j);
                if (numbersMap.containsKey(key)) {
                    // если в матрице уже есть данное число, инкрементим его значение
                    int value = numbersMap.get(key);
                    value++;
                    numbersMap.put(key, value);
                    log.info("Количество чисел под ключом {} теперь равно {}", key, value);
                } else {
                    // если нет, его число вхождений в исходную матрицу 1
                    log.info("Добавлен ключ {}", key);
                    numbersMap.put(key, 1);
                }
            }
        }

        return numbersMap;
    }
}
