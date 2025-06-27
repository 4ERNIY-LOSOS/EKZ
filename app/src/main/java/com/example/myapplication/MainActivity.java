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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextNumber1;
    private EditText editTextNumber2;
    private Button buttonAdd;
    private Button buttonSubtract;
    private Button buttonMultiply;
    private Button buttonDivide;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonSubtract = findViewById(R.id.buttonSubtract);
        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonDivide = findViewById(R.id.buttonDivide);
        textViewResult = findViewById(R.id.textViewResult);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Установка слушателей
        buttonAdd.setOnClickListener(this);
        buttonSubtract.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String strNum1 = editTextNumber1.getText().toString();
        String strNum2 = editTextNumber2.getText().toString();

        if (strNum1.isEmpty() || strNum2.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите оба числа.");
            return;
        }

        try {
            double num1 = Double.parseDouble(strNum1);
            double num2 = Double.parseDouble(strNum2);
            double result = 0;
            String operationSymbol = "";

            int viewId = v.getId();
            if (viewId == R.id.buttonAdd) {
                result = num1 + num2;
                operationSymbol = "+";
            } else if (viewId == R.id.buttonSubtract) {
                result = num1 - num2;
                operationSymbol = "-";
            } else if (viewId == R.id.buttonMultiply) {
                result = num1 * num2;
                operationSymbol = "*";
            } else if (viewId == R.id.buttonDivide) {
                if (num2 == 0) {
                    textViewResult.setText("Результат: Деление на ноль невозможно.");
                    return;
                }
                result = num1 / num2;
                operationSymbol = "/";
            }

            if (!operationSymbol.isEmpty()) {
                 if (result == (long) result) { // Если результат - целое число
                    textViewResult.setText(String.format(Locale.getDefault(), "Результат: %d %s %d = %d", (long)num1, operationSymbol, (long)num2, (long)result));
                } else { // Если результат - дробное число
                    textViewResult.setText(String.format(Locale.getDefault(), "Результат: %.2f %s %.2f = %.2f", num1, operationSymbol, num2, result));
                }
            }

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректные числовые значения.");
        }
    }
}
