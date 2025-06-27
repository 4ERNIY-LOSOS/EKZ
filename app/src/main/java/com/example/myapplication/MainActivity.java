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

    private EditText editTextArrayInput;
    // private EditText editTextSearchElement; // Удален, не нужен для сортировки
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextArrayInput = findViewById(R.id.editTextArrayInput);
        // editTextSearchElement = findViewById(R.id.editTextSearchElement); // Удален
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
                performInsertionSort();
            }
        });
    }

    private void performInsertionSort() {
        String inputText = editTextArrayInput.getText().toString();
        if (inputText.isEmpty()) {
            textViewResult.setText("Результат: Поле ввода пустое. Введите числа.");
            return;
        }

        String[] stringArray = inputText.split("[,\\s]+");
        if (stringArray.length == 0 || (stringArray.length == 1 && stringArray[0].isEmpty())) {
            textViewResult.setText("Результат: Введите числа для сортировки.");
            return;
        }

        List<Integer> numbersList = new ArrayList<>();
        for (String s : stringArray) {
            if (s.trim().isEmpty()) continue;
            try {
                numbersList.add(Integer.parseInt(s.trim()));
            } catch (NumberFormatException e) {
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: Ошибка в числе '%s'. Введите корректные целые числа.", s));
                return;
            }
        }

        if (numbersList.isEmpty()) {
            textViewResult.setText("Результат: Не найдено чисел для сортировки.");
            return;
        }

        Integer[] numbers = numbersList.toArray(new Integer[0]);

        // Алгоритм сортировки вставкой
        for (int i = 1; i < numbers.length; i++) {
            int key = numbers[i];
            int j = i - 1;

            // Перемещаем элементы numbers[0..i-1], которые больше key,
            // на одну позицию вперед их текущей позиции
            while (j >= 0 && numbers[j] > key) {
                numbers[j + 1] = numbers[j];
                j = j - 1;
            }
            numbers[j + 1] = key;
        }

        textViewResult.setText(String.format(Locale.getDefault(),"Результат: %s", Arrays.toString(numbers)));
    }
}
