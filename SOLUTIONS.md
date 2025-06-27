# Решения Заданий

Здесь будут собраны решения для выполненных заданий.

## Задание 1: Площадь треугольника по формуле Герона

### `activity_main.xml`

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

### `MainActivity.java`

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

## Задание 2: Корни квадратного уравнения

### `activity_main.xml`

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
        android:text="Задание 2: Корни квадратного уравнения"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextCoefficientA"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Коэффициент a"
        android:inputType="numberSigned|numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="number" />

    <EditText
        android:id="@+id/editTextCoefficientB"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Коэффициент b"
        android:inputType="numberSigned|numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/editTextCoefficientA"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <EditText
        android:id="@+id/editTextCoefficientC"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Коэффициент c"
        android:inputType="numberSigned|numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/editTextCoefficientB"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Вычислить корни"
        app:layout_constraintTop_toBottomOf="@id/editTextCoefficientC"
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

### `MainActivity.java`

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
```

## Задание 3: Площадь трапеции

### `activity_main.xml`

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
        android:text="Задание 3: Площадь трапеции"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextBaseA"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Основание a"
        android:inputType="numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="number" />

    <EditText
        android:id="@+id/editTextBaseB"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Основание b"
        android:inputType="numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/editTextBaseA"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <EditText
        android:id="@+id/editTextHeight"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Высота h"
        android:inputType="numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/editTextBaseB"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Вычислить площадь"
        app:layout_constraintTop_toBottomOf="@id/editTextHeight"
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

### `MainActivity.java`

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
```

## Задание 4: Объем цилиндра

### `activity_main.xml`

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
        android:text="Задание 4: Объем цилиндра"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextRadius"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Радиус основания (r)"
        android:inputType="numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="number" />

    <EditText
        android:id="@+id/editTextHeightCylinder"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Высота цилиндра (h)"
        android:inputType="numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/editTextRadius"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Вычислить объем"
        app:layout_constraintTop_toBottomOf="@id/editTextHeightCylinder"
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

### `MainActivity.java`

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
```
