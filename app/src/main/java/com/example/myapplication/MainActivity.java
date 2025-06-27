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

    private EditText editTextCoefficientA;
    private EditText editTextCoefficientB;
    private EditText editTextCoefficientC;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextCoefficientA = findViewById(R.id.editTextCoefficientA);
        editTextCoefficientB = findViewById(R.id.editTextCoefficientB);
        editTextCoefficientC = findViewById(R.id.editTextCoefficientC);
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
                calculateQuadraticEquationRoots();
            }
        });
    }

    private void calculateQuadraticEquationRoots() {
        String strA = editTextCoefficientA.getText().toString();
        String strB = editTextCoefficientB.getText().toString();
        String strC = editTextCoefficientC.getText().toString();

        if (strA.isEmpty() || strB.isEmpty() || strC.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите все три коэффициента.");
            return;
        }

        try {
            double a = Double.parseDouble(strA);
            double b = Double.parseDouble(strB);
            double c = Double.parseDouble(strC);

            if (a == 0) {
                // Это линейное уравнение bx + c = 0
                if (b == 0) {
                    if (c == 0) {
                        textViewResult.setText("Результат: Бесконечное множество решений (0 = 0).");
                    } else {
                        textViewResult.setText("Результат: Решений нет (c = 0, где c != 0).");
                    }
                } else {
                    double x = -c / b;
                    textViewResult.setText(String.format(Locale.getDefault(), "Результат: Линейное уравнение. Корень x = %.2f", x));
                }
                return;
            }

            // Вычисление дискриминанта
            double discriminant = b * b - 4 * a * c;

            if (discriminant > 0) {
                double x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                double x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: Два корня: x1 = %.2f, x2 = %.2f", x1, x2));
            } else if (discriminant == 0) {
                double x = -b / (2 * a);
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: Один корень: x = %.2f", x));
            } else {
                textViewResult.setText("Результат: Действительных корней нет (дискриминант < 0).");
            }

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректные числовые значения для коэффициентов.");
        }
    }
}
