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
    private EditText editTextInt3;
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
        editTextInt3 = findViewById(R.id.editTextInt3);
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
                calculateGCDofThreeNumbers();
            }
        });
    }

    // Метод для вычисления НОД двух чисел (алгоритм Евклида)
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

    private void calculateGCDofThreeNumbers() {
        String strInt1 = editTextInt1.getText().toString();
        String strInt2 = editTextInt2.getText().toString();
        String strInt3 = editTextInt3.getText().toString();

        if (strInt1.isEmpty() || strInt2.isEmpty() || strInt3.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите все три числа.");
            return;
        }

        try {
            int num1 = Integer.parseInt(strInt1);
            int num2 = Integer.parseInt(strInt2);
            int num3 = Integer.parseInt(strInt3);

            if (num1 == 0 && num2 == 0 && num3 == 0) {
                textViewResult.setText("Результат: НОД(0,0,0) не определен (или 0 по соглашению). Введите хотя бы одно ненулевое число.");
                return;
            }

            // НОД(a, b, c) = НОД(НОД(a, b), c)
            // Также НОД(0, x) = |x|
            int resultGcd;
            if (num1 == 0 && num2 == 0) {
                resultGcd = Math.abs(num3);
            } else if (num1 == 0 && num3 == 0) {
                resultGcd = Math.abs(num2);
            } else if (num2 == 0 && num3 == 0) {
                resultGcd = Math.abs(num1);
            } else {
                 resultGcd = gcd(gcd(num1, num2), num3);
            }


            textViewResult.setText(String.format(Locale.getDefault(), "Результат: НОД(%d, %d, %d) = %d", num1, num2, num3, resultGcd));

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректные целые числа.");
        }
    }
}
