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

    private EditText editTextSideA_Task16;
    private EditText editTextSideB_Task16;
    private EditText editTextAngleDegrees;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextSideA_Task16 = findViewById(R.id.editTextSideA_Task16);
        editTextSideB_Task16 = findViewById(R.id.editTextSideB_Task16);
        editTextAngleDegrees = findViewById(R.id.editTextAngleDegrees);
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
                calculateTriangleAreaBySidesAndAngle();
            }
        });
    }

    private void calculateTriangleAreaBySidesAndAngle() {
        String strSideA = editTextSideA_Task16.getText().toString();
        String strSideB = editTextSideB_Task16.getText().toString();
        String strAngle = editTextAngleDegrees.getText().toString();

        if (strSideA.isEmpty() || strSideB.isEmpty() || strAngle.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите обе стороны и угол.");
            return;
        }

        try {
            double a = Double.parseDouble(strSideA);
            double b = Double.parseDouble(strSideB);
            double angleDegrees = Double.parseDouble(strAngle);

            if (a <= 0 || b <= 0) {
                textViewResult.setText("Результат: Длины сторон должны быть положительными числами.");
                return;
            }
            if (angleDegrees <= 0 || angleDegrees >= 180) {
                textViewResult.setText("Результат: Угол должен быть больше 0 и меньше 180 градусов.");
                return;
            }

            double angleRadians = Math.toRadians(angleDegrees);
            double area = 0.5 * a * b * Math.sin(angleRadians);

            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Площадь треугольника = %.2f", area));

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректные числовые значения.");
        }
    }
}
