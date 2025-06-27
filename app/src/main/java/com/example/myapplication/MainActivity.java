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
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextArrayInput = findViewById(R.id.editTextArrayInput);
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
                performBubbleSort();
            }
        });
    }

    private void performBubbleSort() {
        String inputText = editTextArrayInput.getText().toString();
        if (inputText.isEmpty()) {
            textViewResult.setText("Результат: Поле ввода пустое. Введите числа.");
            return;
        }

        String[] stringArray = inputText.split("[,\\s]+"); // Разделение по запятым и/или пробелам
        if (stringArray.length == 0 || (stringArray.length == 1 && stringArray[0].isEmpty())) {
             textViewResult.setText("Результат: Введите числа для сортировки.");
            return;
        }

        List<Integer> numbersList = new ArrayList<>();
        for (String s : stringArray) {
            if (s.trim().isEmpty()) continue; // Пропускаем пустые строки после разделения
            try {
                numbersList.add(Integer.parseInt(s.trim()));
            } catch (NumberFormatException e) {
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: Ошибка в числе '%s'. Введите корректные целые числа через запятую или пробел.", s));
                return;
            }
        }

        if (numbersList.isEmpty()) {
            textViewResult.setText("Результат: Не найдено чисел для сортировки.");
            return;
        }

        Integer[] numbers = numbersList.toArray(new Integer[0]);

        // Алгоритм сортировки пузырьком
        int n = numbers.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    // Обмен значениями
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                    swapped = true;
                }
            }
            // Если во внутреннем цикле не было обменов, массив уже отсортирован
            if (!swapped) {
                break;
            }
        }

        textViewResult.setText(String.format(Locale.getDefault(),"Результат: %s", Arrays.toString(numbers)));
    }
}
