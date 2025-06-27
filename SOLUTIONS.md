# Решения Заданий

Здесь будут собраны решения для выполненных заданий.

## Задание 1: Площадь треугольника по формуле Герона

### `app/src/main/res/layout/activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/textViewTitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Задание 1: Площадь треугольника (Формула Герона)"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextSideA"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Сторона a"
        android:inputType="numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="number" />

    <EditText
        android:id="@+id/editTextSideB"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Сторона b"
        android:inputType="numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/editTextSideA"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <EditText
        android:id="@+id/editTextSideC"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Сторона c"
        android:inputType="numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/editTextSideB"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Вычислить"
        app:layout_constraintTop_toBottomOf="@id/editTextSideC"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"/>

    <TextView
        android:id="@+id/textViewResult"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Результат:"
        android:textSize="18sp"
        app:layout_constraintTop_toBottomOf="@id/buttonCalculate"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

### `app/src/main/java/com/example/myapplication/MainActivity.java`

```java
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

    private EditText editTextSideA;
    private EditText editTextSideB;
    private EditText editTextSideC;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextSideA = findViewById(R.id.editTextSideA);
        editTextSideB = findViewById(R.id.editTextSideB);
        editTextSideC = findViewById(R.id.editTextSideC);
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
                calculateTriangleArea();
            }
        });
    }

    private void calculateTriangleArea() {
        String strA = editTextSideA.getText().toString();
        String strB = editTextSideB.getText().toString();
        String strC = editTextSideC.getText().toString();

        if (strA.isEmpty() || strB.isEmpty() || strC.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите все три стороны.");
            return;
        }

        try {
            double a = Double.parseDouble(strA);
            double b = Double.parseDouble(strB);
            double c = Double.parseDouble(strC);

            if (a <= 0 || b <= 0 || c <= 0) {
                textViewResult.setText("Результат: Стороны должны быть положительными числами.");
                return;
            }

            // Проверка неравенства треугольника
            if (a + b <= c || a + c <= b || b + c <= a) {
                textViewResult.setText("Результат: Сумма двух сторон должна быть больше третьей стороны.");
                return;
            }

            // Вычисление полупериметра
            double p = (a + b + c) / 2;

            // Вычисление площади по формуле Герона
            double area = Math.sqrt(p * (p - a) * (p - b) * (p - c));

            if (Double.isNaN(area) || Double.isInfinite(area)) {
                textViewResult.setText("Результат: Невозможно вычислить площадь с данными сторонами.");
            } else {
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: Площадь = %.2f", area));
            }

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректные числовые значения для сторон.");
        }
    }
}
```
