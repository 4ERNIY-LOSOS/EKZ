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

public class MainActivity extends AppCompatActivity {

    private EditText editTextIntegerInput; // Изменено с editTextArrayInput
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextIntegerInput = findViewById(R.id.editTextIntegerInput); // Изменено
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
                swapFirstAndLastDigits(); // Изменен вызов метода
            }
        });
    }

    private void swapFirstAndLastDigits() {
        String strInput = editTextIntegerInput.getText().toString();

        if (strInput.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите число.");
            return;
        }
        if (strInput.equals("-")) {
            textViewResult.setText("Результат: Пожалуйста, введите корректное целое число.");
            return;
        }

        try {
            long number = Long.parseLong(strInput);
            long originalNumber = number;
            boolean isNegative = false;

            if (number < 0) {
                isNegative = true;
                number = -number; // Работаем с положительным числом
            }

            if (number < 10) { // Однозначные числа (включая 0, если не было знака минус) не меняются
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: %d (не изменилось)", originalNumber));
                return;
            }

            long lastDigit = number % 10;
            long powerOf10 = 1;
            long temp = number;
            while (temp >= 10) {
                temp /= 10;
                powerOf10 *= 10;
            }
            long firstDigit = number / powerOf10;

            // Если число состоит только из двух цифр, powerOf10 будет 10.
            // (number % powerOf10) будет lastDigit. (number % powerOf10) / 10 будет 0.
            // Пример: 78. powerOf10 = 10. lastDigit = 8. firstDigit = 7.
            // middlePart = (78 % 10) / 10 = 8 / 10 = 0.
            // newNumber = 8 * 10 + 0 * 10 + 7 = 80 + 0 + 7 = 87. Корректно.

            // Пример: 123. powerOf10 = 100. lastDigit = 3. firstDigit = 1.
            // middlePart = (123 % 100) / 10 = 23 / 10 = 2.
            // newNumber = 3 * 100 + 2 * 10 + 1 = 300 + 20 + 1 = 321. Корректно.

            long middlePartWithLastDigit = number % powerOf10; // число без первой цифры
            long middlePart = middlePartWithLastDigit / 10;    // число без первой и последней цифры

            long newNumber = lastDigit * powerOf10 + middlePart * 10 + firstDigit;

            // Проверка на случай, если powerOf10 == 1 (однозначное число), но мы уже обработали это через number < 10
            // Для двузначных чисел: firstDigit * 10 + lastDigit.
            // lastDigit * powerOf10 (powerOf10=10) + firstDigit. (middlePart = 0)
            // 78 -> 8*10 + 0*10 + 7 = 87.
            // Если число, например, 10. last=0, p10=10, first=1. middle=(10%10)/10 = 0. new = 0*10+0*10+1 = 1.
            // Это правильно, 10 -> 01, т.е. 1.

            if (isNegative) {
                newNumber = -newNumber;
            }

            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Исходное: %d, Новое: %d", originalNumber, newNumber));

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректное целое число.");
        }
    }
}
