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

    private EditText editTextArrayInput; // Переименовано с editTextSortedArrayInput
    private EditText editTextSearchElement;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextArrayInput = findViewById(R.id.editTextArrayInput); // Используем новый ID
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
                performLinearSearch();
            }
        });
    }

    private void performLinearSearch() {
        String arrayText = editTextArrayInput.getText().toString();
        String elementText = editTextSearchElement.getText().toString();

        if (arrayText.isEmpty()) {
            textViewResult.setText("Результат: Поле для массива пустое. Введите массив.");
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

        // Алгоритм последовательного поиска
        int index = -1;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == searchElement) {
                index = i; // Найдено первое вхождение
                break;
            }
        }

        if (index != -1) {
            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Элемент %d найден на позиции %d (индекс).", searchElement, index));
        } else {
            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Элемент %d не найден в массиве.", searchElement));
        }
    }
}
