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

    private EditText editTextRadius;
    private EditText editTextHeightCylinder;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextRadius = findViewById(R.id.editTextRadius);
        editTextHeightCylinder = findViewById(R.id.editTextHeightCylinder);
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
                calculateCylinderVolume();
            }
        });
    }

    private void calculateCylinderVolume() {
        String strRadius = editTextRadius.getText().toString();
        String strHeight = editTextHeightCylinder.getText().toString();

        if (strRadius.isEmpty() || strHeight.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите радиус и высоту.");
            return;
        }

        try {
            double radius = Double.parseDouble(strRadius);
            double height = Double.parseDouble(strHeight);

            if (radius <= 0 || height <= 0) {
                textViewResult.setText("Результат: Радиус и высота должны быть положительными числами.");
                return;
            }

            // Вычисление объема цилиндра
            // V = π * r^2 * h
            double volume = Math.PI * radius * radius * height;

            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Объем цилиндра = %.2f", volume));

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректные числовые значения.");
        }
    }
}
