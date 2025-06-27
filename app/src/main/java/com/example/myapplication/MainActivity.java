package com.example.myapplication;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Locale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextSortedArrayInput;
    private EditText editTextSearchElement;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextSortedArrayInput = findViewById(R.id.editTextSortedArrayInput);
        editTextSearchElement = findViewById(R.id.editTextSearchElement);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performBinarySearch();
            }
        });
    }

    private void performBinarySearch() {
        String arrayText = editTextSortedArrayInput.getText().toString();
        String elementText = editTextSearchElement.getText().toString();

        if (arrayText.isEmpty()) {
            textViewResult.setText("Результат: Поле для массива пустое. Введите отсортированный массив.");
            return;
        }
        if (elementText.isEmpty()) {
            textViewResult.setText("Результат: Поле для искомого элемента пустое. Введите элемент.");
            return;
        }

        String[] stringArray = arrayText.split("[,\\s]+");
        if (stringArray.length == 0 || (stringArray.length == 1 && stringArray[0].isEmpty())) {
            textViewResult.setText("Результат: Введите числа для массива.");
            return;
        }

        List<Integer> numbersList = new ArrayList<>();
        for (String s : stringArray) {
            if (s.trim().isEmpty()) continue;
            try {
                numbersList.add(Integer.parseInt(s.trim()));
            } catch (NumberFormatException e) {
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: Ошибка в массиве: '%s'. Введите корректные целые числа.", s));
                return;
            }
        }

        if (numbersList.isEmpty()) {
            textViewResult.setText("Результат: Массив не содержит чисел.");
            return;
        }

        Integer[] numbers = numbersList.toArray(new Integer[0]);
        int searchElement;
        try {
            searchElement = Integer.parseInt(elementText.trim());
        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Искомый элемент введен некорректно.");
            return;
        }

        // Проверка на отсортированность (опционально, но хорошо бы иметь)
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i+1]) {
                textViewResult.setText("Результат: Массив не отсортирован! Бинарный поиск требует отсортированного массива.\nПожалуйста, отсортируйте: " + Arrays.toString(numbers));
                // Можно добавить вызов сортировки, если есть
                // Arrays.sort(numbers); // или вызвать performBubbleSort если он был бы здесь
                // textViewResult.append("\nОтсортированный массив для поиска: " + Arrays.toString(numbers));
                return;
            }
        }


        // Алгоритм бинарного поиска
        int low = 0;
        int high = numbers.length - 1;
        int index = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2; // Чтобы избежать переполнения (low + high) / 2
            if (numbers[mid] == searchElement) {
                index = mid;
                break;
            } else if (numbers[mid] < searchElement) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (index != -1) {
            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Элемент %d найден на позиции %d (индекс).", searchElement, index));
        } else {
            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Элемент %d не найден в массиве.", searchElement));
        }
    }
}
