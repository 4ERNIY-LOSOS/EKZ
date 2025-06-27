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

    private EditText editTextBaseA;
    private EditText editTextBaseB;
    private EditText editTextHeight;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextBaseA = findViewById(R.id.editTextBaseA);
        editTextBaseB = findViewById(R.id.editTextBaseB);
        editTextHeight = findViewById(R.id.editTextHeight);
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
                calculateTrapezoidArea();
            }
        });
    }

    private void calculateTrapezoidArea() {
        String strBaseA = editTextBaseA.getText().toString();
        String strBaseB = editTextBaseB.getText().toString();
        String strHeight = editTextHeight.getText().toString();

        if (strBaseA.isEmpty() || strBaseB.isEmpty() || strHeight.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите оба основания и высоту.");
            return;
        }

        try {
            double baseA = Double.parseDouble(strBaseA);
            double baseB = Double.parseDouble(strBaseB);
            double height = Double.parseDouble(strHeight);

            if (baseA <= 0 || baseB <= 0 || height <= 0) {
                textViewResult.setText("Результат: Основания и высота должны быть положительными числами.");
                return;
            }

            // Вычисление площади трапеции
            double area = ((baseA + baseB) / 2) * height;

            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Площадь трапеции = %.2f", area));

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректные числовые значения.");
        }
    }
}
