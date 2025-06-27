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

    private EditText editTextLargerBaseA;
    private EditText editTextSmallerBaseB;
    private EditText editTextSideC_Trapezoid;
    private EditText editTextDiagonalD;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextLargerBaseA = findViewById(R.id.editTextLargerBaseA);
        editTextSmallerBaseB = findViewById(R.id.editTextSmallerBaseB);
        editTextSideC_Trapezoid = findViewById(R.id.editTextSideC_Trapezoid);
        editTextDiagonalD = findViewById(R.id.editTextDiagonalD);
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
                calculateTrapezoidCircumcircleRadius();
            }
        });
    }

    // Вспомогательный метод для вычисления площади треугольника по формуле Герона
    private double calculateHeronArea(double s1, double s2, double s3) {
        if (s1 + s2 <= s3 || s1 + s3 <= s2 || s2 + s3 <= s1) {
            return -1; // Невозможно образовать треугольник
        }
        double p = (s1 + s2 + s3) / 2.0;
        double areaSquared = p * (p - s1) * (p - s2) * (p - s3);
        if (areaSquared < -1e-9) return -1; // Учет ошибок округления
        if (areaSquared < 0) areaSquared = 0;
        return Math.sqrt(areaSquared);
    }

    private void calculateTrapezoidCircumcircleRadius() {
        String strA = editTextLargerBaseA.getText().toString();
        String strB = editTextSmallerBaseB.getText().toString(); // Меньшее основание b
        String strC = editTextSideC_Trapezoid.getText().toString(); // Боковая сторона c
        String strD = editTextDiagonalD.getText().toString(); // Диагональ d

        if (strA.isEmpty() || strB.isEmpty() || strC.isEmpty() || strD.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите все четыре значения.");
            return;
        }

        try {
            double a = Double.parseDouble(strA); // Большее основание
            double b = Double.parseDouble(strB); // Меньшее основание
            double c_side = Double.parseDouble(strC); // Боковая сторона
            double d_diag = Double.parseDouble(strD); // Диагональ

            if (a <= 0 || b <= 0 || c_side <= 0 || d_diag <= 0) {
                textViewResult.setText("Результат: Все длины должны быть положительными числами.");
                return;
            }
            if (a <= b) {
                 textViewResult.setText("Результат: Большее основание (a) должно быть больше меньшего (b).");
                return;
            }

            // Для описанной окружности трапеция должна быть равнобокой.
            // Мы используем треугольник со сторонами: большее основание (a), боковая сторона (c_side), диагональ (d_diag)
            // Площадь этого треугольника S_acd
            double areaTriangle = calculateHeronArea(a, c_side, d_diag);

            if (areaTriangle <= 0) {
                textViewResult.setText("Результат: Невозможно построить треугольник из большего основания (a), боковой стороны (c) и диагонали (d). Проверьте введенные значения.");
                return;
            }

            // Радиус описанной окружности R = (x*y*z) / (4*S_triangle)
            // где x,y,z - стороны треугольника a, c_side, d_diag
            double radius = (a * c_side * d_diag) / (4 * areaTriangle);

            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Радиус описанной окружности R = %.2f", radius));

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректные числовые значения.");
        }
    }
}
