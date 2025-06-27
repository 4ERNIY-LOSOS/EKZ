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

    private EditText editTextSideA_Task17; // Переиспользуем ID из Задания 17
    private EditText editTextSideB_Task17; // Переиспользуем ID из Задания 17
    private EditText editTextSideC_Task17; // Переиспользуем ID из Задания 17
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextSideA_Task17 = findViewById(R.id.editTextSideA_Task17);
        editTextSideB_Task17 = findViewById(R.id.editTextSideB_Task17);
        editTextSideC_Task17 = findViewById(R.id.editTextSideC_Task17);
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
                calculateIncircleRadius();
            }
        });
    }

    private double calculateHeronArea(double sA, double sB, double sC) {
        // Проверка неравенства треугольника
        if (sA + sB <= sC || sA + sC <= sB || sB + sC <= sA) {
            return -1; // Ошибка: стороны не образуют треугольник
        }
        double p = (sA + sB + sC) / 2.0; // Полупериметр
        double areaSquared = p * (p - sA) * (p - sB) * (p - sC);
        // Добавим небольшую погрешность для areaSquared, чтобы избежать -0.0 из-за ошибок округления
        if (areaSquared < -1e-9) return -1;
        if (areaSquared < 0) areaSquared = 0; // Если очень близко к нулю, но отрицательное
        return Math.sqrt(areaSquared);
    }

    private void calculateIncircleRadius() {
        String strSideA = editTextSideA_Task17.getText().toString();
        String strSideB = editTextSideB_Task17.getText().toString();
        String strSideC = editTextSideC_Task17.getText().toString();

        if (strSideA.isEmpty() || strSideB.isEmpty() || strSideC.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите все три стороны.");
            return;
        }

        try {
            double a = Double.parseDouble(strSideA);
            double b = Double.parseDouble(strSideB);
            double c = Double.parseDouble(strSideC);

            if (a <= 0 || b <= 0 || c <= 0) {
                textViewResult.setText("Результат: Длины сторон должны быть положительными числами.");
                return;
            }

            double area = calculateHeronArea(a, b, c);

            if (area < 0) { // Ошибка от calculateHeronArea (не треугольник)
                textViewResult.setText("Результат: С данными сторонами невозможно образовать треугольник.");
                return;
            }
            if (area == 0) { // Вырожденный треугольник
                 textViewResult.setText("Результат: Площадь треугольника равна нулю (вырожденный треугольник), радиус вписанной окружности не определен / равен 0.");
                return;
            }


            double semiPerimeter = (a + b + c) / 2.0;

            if (semiPerimeter == 0) { // Теоретически не должно произойти, если area > 0
                 textViewResult.setText("Результат: Полупериметр равен нулю, невозможно вычислить радиус.");
                return;
            }

            // r = S / p
            double radius = area / semiPerimeter;

            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Радиус вписанной окружности r = %.2f", radius));

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректные числовые значения для сторон.");
        }
    }
}
