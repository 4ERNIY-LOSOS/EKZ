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

    private EditText editTextIntegerInput;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextIntegerInput = findViewById(R.id.editTextIntegerInput);
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
                reverseNumberDigits();
            }
        });
    }

    private void reverseNumberDigits() {
        String strInput = editTextIntegerInput.getText().toString();

        if (strInput.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите число.");
            return;
        }

        // Проверка на наличие только знака "-"
        if (strInput.equals("-")) {
            textViewResult.setText("Результат: Пожалуйста, введите корректное целое число.");
            return;
        }

        try {
            long number = Long.parseLong(strInput);
            long originalNumber = number; // Сохраняем для вывода
            long reversedNumber = 0;
            boolean isNegative = false;

            if (number == 0) {
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: Реверс числа %d = 0", originalNumber));
                return;
            }

            if (number < 0) {
                isNegative = true;
                number = -number; // Работаем с положительным числом для реверса
            }

            while (number > 0) {
                long digit = number % 10;
                // Проверка на переполнение перед умножением
                if (reversedNumber > (Long.MAX_VALUE - digit) / 10) {
                    textViewResult.setText("Результат: Переполнение при реверсе числа.");
                    return;
                }
                reversedNumber = reversedNumber * 10 + digit;
                number /= 10;
            }

            if (isNegative) {
                reversedNumber = -reversedNumber;
            }

            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Реверс числа %d = %d", originalNumber, reversedNumber));

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректное целое число.");
        }
    }
}
