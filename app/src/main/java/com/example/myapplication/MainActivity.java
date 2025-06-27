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
                calculateSumOfDigits();
            }
        });
    }

    private void calculateSumOfDigits() {
        String strInput = editTextIntegerInput.getText().toString();

        if (strInput.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите число.");
            return;
        }

        try {
            long number = Long.parseLong(strInput); // Используем long для поддержки более широкого диапазона чисел
            long originalNumber = number; // Сохраняем исходное число для вывода

            number = Math.abs(number); // Работаем с модулем числа

            if (originalNumber == 0) {
                 textViewResult.setText(String.format(Locale.getDefault(), "Результат: Сумма цифр числа %d = 0", originalNumber));
                 return;
            }

            long sum = 0;
            long tempNumber = number; // Используем временную переменную для цикла

            while (tempNumber > 0) {
                sum += tempNumber % 10; // Добавляем последнюю цифру к сумме
                tempNumber /= 10;      // Удаляем последнюю цифру
            }

            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Сумма цифр числа %d = %d", originalNumber, sum));

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректное целое число.");
        }
    }
}
