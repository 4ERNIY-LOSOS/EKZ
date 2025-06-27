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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextFibonacciCount;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextFibonacciCount = findViewById(R.id.editTextFibonacciCount);
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
                generateFibonacciSequence();
            }
        });
    }

    private void generateFibonacciSequence() {
        String strN = editTextFibonacciCount.getText().toString();

        if (strN.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите количество чисел.");
            return;
        }

        try {
            int n = Integer.parseInt(strN);

            if (n < 0) {
                textViewResult.setText("Результат: Количество чисел не может быть отрицательным.");
                return;
            }
            if (n == 0) {
                textViewResult.setText("Результат: Последовательность пуста (0 чисел).");
                return;
            }
            // Ограничение, чтобы избежать слишком больших чисел и долгого вывода
            // Число Фибоначчи F(93) уже превышает Long.MAX_VALUE
            if (n > 92) {
                textViewResult.setText("Результат: Слишком большое N. Пожалуйста, введите N <= 92, чтобы избежать переполнения long.");
                return;
            }


            List<Long> fibonacciSequence = new ArrayList<>();
            if (n >= 1) {
                fibonacciSequence.add(0L);
            }
            if (n >= 2) {
                fibonacciSequence.add(1L);
            }

            for (int i = 2; i < n; i++) {
                long nextFib = fibonacciSequence.get(i - 1) + fibonacciSequence.get(i - 2);
                fibonacciSequence.add(nextFib);
            }

            StringBuilder sb = new StringBuilder("Результат: ");
            for (int i = 0; i < fibonacciSequence.size(); i++) {
                sb.append(fibonacciSequence.get(i));
                if (i < fibonacciSequence.size() - 1) {
                    sb.append(", ");
                }
            }
            textViewResult.setText(sb.toString());

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректное целое число для N.");
        }
    }
}
