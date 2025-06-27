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

    private EditText editTextInt1;
    private EditText editTextInt2;
    // private EditText editTextInt3; // Удален, так как НОК для двух чисел
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextInt1 = findViewById(R.id.editTextInt1);
        editTextInt2 = findViewById(R.id.editTextInt2);
        // editTextInt3 = findViewById(R.id.editTextInt3); // Удален
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
                calculateLCMofTwoNumbers();
            }
        });
    }

    // Метод для вычисления НОД двух чисел (алгоритм Евклида)
    // Используется для вычисления НОК: НОК(a,b) = (|a*b|) / НОД(a,b)
    private int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private void calculateLCMofTwoNumbers() {
        String strInt1 = editTextInt1.getText().toString();
        String strInt2 = editTextInt2.getText().toString();

        if (strInt1.isEmpty() || strInt2.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите оба числа.");
            return;
        }

        try {
            int num1 = Integer.parseInt(strInt1);
            int num2 = Integer.parseInt(strInt2);

            if (num1 == 0 || num2 == 0) {
                // НОК(a, 0) = 0 и НОК(0, b) = 0
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: НОК(%d, %d) = 0", num1, num2));
                return;
            }

            // НОК(a,b) = (|a*b|) / НОД(a,b)
            // Важно использовать long для произведения, чтобы избежать переполнения перед делением
            long product = (long) Math.abs(num1) * Math.abs(num2);
            int commonDivisor = gcd(num1, num2);

            long lcmResult = product / commonDivisor;

            textViewResult.setText(String.format(Locale.getDefault(), "Результат: НОК(%d, %d) = %d", num1, num2, lcmResult));

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректные целые числа.");
        }
    }
}
