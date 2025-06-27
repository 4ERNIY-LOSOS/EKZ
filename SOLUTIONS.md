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

## Задание 5: Калькулятор

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
        android:text="Задание 5: Калькулятор"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextNumber1"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Число 1"
        android:inputType="numberSigned|numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="number" />

    <EditText
        android:id="@+id/editTextNumber2"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Число 2"
        android:inputType="numberSigned|numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/editTextNumber1"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <LinearLayout
        android:id="@+id/buttonsLayout"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:layout_marginTop="24dp"
        app:layout_constraintTop_toBottomOf="@id/editTextNumber2"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:gravity="center">

        <Button
            android:id="@+id/buttonAdd"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="+"
            android:textSize="24sp"
            android:layout_marginEnd="8dp"/>

        <Button
            android:id="@+id/buttonSubtract"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="-"
            android:textSize="24sp"
            android:layout_marginStart="8dp"
            android:layout_marginEnd="8dp"/>

        <Button
            android:id="@+id/buttonMultiply"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="*"
            android:textSize="24sp"
            android:layout_marginStart="8dp"
            android:layout_marginEnd="8dp"/>

        <Button
            android:id="@+id/buttonDivide"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="/"
            android:textSize="24sp"
            android:layout_marginStart="8dp"/>
    </LinearLayout>

    <TextView
        android:id="@+id/textViewResult"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Результат:"
        android:textSize="18sp"
        app:layout_constraintTop_toBottomOf="@id/buttonsLayout"
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
```

## Задание 6: НОД трех чисел

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
        android:text="Задание 6: НОД трех чисел"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextInt1"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Число 1"
        android:inputType="numberSigned"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="number" />

    <EditText
        android:id="@+id/editTextInt2"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Число 2"
        android:inputType="numberSigned"
        app:layout_constraintTop_toBottomOf="@id/editTextInt1"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <EditText
        android:id="@+id/editTextInt3"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Число 3"
        android:inputType="numberSigned"
        app:layout_constraintTop_toBottomOf="@id/editTextInt2"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Вычислить НОД"
        app:layout_constraintTop_toBottomOf="@id/editTextInt3"
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
```

## Задание 7: НОК двух чисел

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
        android:text="Задание 7: НОК двух чисел"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextInt1"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Число 1"
        android:inputType="numberSigned"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="number" />

    <EditText
        android:id="@+id/editTextInt2"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Число 2"
        android:inputType="numberSigned"
        app:layout_constraintTop_toBottomOf="@id/editTextInt1"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Вычислить НОК"
        app:layout_constraintTop_toBottomOf="@id/editTextInt2"
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextInt1;
    private EditText editTextInt2;
    // private EditText editTextInt3; // Удален, так как НОК для двух чисел
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
        // editTextInt3 = findViewById(R.id.editTextInt3); // Удален
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
                calculateLCMofTwoNumbers();
            }
        });
    }

    // Метод для вычисления НОД двух чисел (алгоритм Евклида)
    // Используется для вычисления НОК: НОК(a,b) = (|a*b|) / НОД(a,b)
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

    private void calculateLCMofTwoNumbers() {
        String strInt1 = editTextInt1.getText().toString();
        String strInt2 = editTextInt2.getText().toString();

        if (strInt1.isEmpty() || strInt2.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите оба числа.");
            return;
        }

        try {
            int num1 = Integer.parseInt(strInt1);
            int num2 = Integer.parseInt(strInt2);

            if (num1 == 0 || num2 == 0) {
                // НОК(a, 0) = 0 и НОК(0, b) = 0
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: НОК(%d, %d) = 0", num1, num2));
                return;
            }

            // НОК(a,b) = (|a*b|) / НОД(a,b)
            // Важно использовать long для произведения, чтобы избежать переполнения перед делением
            long product = (long) Math.abs(num1) * Math.abs(num2);
            int commonDivisor = gcd(num1, num2);

            long lcmResult = product / commonDivisor;

            textViewResult.setText(String.format(Locale.getDefault(), "Результат: НОК(%d, %d) = %d", num1, num2, lcmResult));

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректные целые числа.");
        }
    }
}
```

## Задание 8: Сумма цифр числа

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
        android:text="Задание 8: Сумма цифр числа"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextIntegerInput"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Введите целое число"
        android:inputType="numberSigned"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="number" />

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Вычислить сумму цифр"
        app:layout_constraintTop_toBottomOf="@id/editTextIntegerInput"
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

    private EditText editTextIntegerInput;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextIntegerInput = findViewById(R.id.editTextIntegerInput);
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
                calculateSumOfDigits();
            }
        });
    }

    private void calculateSumOfDigits() {
        String strInput = editTextIntegerInput.getText().toString();

        if (strInput.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите число.");
            return;
        }

        try {
            long number = Long.parseLong(strInput); // Используем long для поддержки более широкого диапазона чисел
            long originalNumber = number; // Сохраняем исходное число для вывода

            number = Math.abs(number); // Работаем с модулем числа

            if (originalNumber == 0) {
                 textViewResult.setText(String.format(Locale.getDefault(), "Результат: Сумма цифр числа %d = 0", originalNumber));
                 return;
            }

            long sum = 0;
            long tempNumber = number; // Используем временную переменную для цикла

            while (tempNumber > 0) {
                sum += tempNumber % 10; // Добавляем последнюю цифру к сумме
                tempNumber /= 10;      // Удаляем последнюю цифру
            }

            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Сумма цифр числа %d = %d", originalNumber, sum));

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректное целое число.");
        }
    }
}
```

## Задание 9: Реверс числа

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
        android:text="Задание 9: Реверс числа"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextIntegerInput"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Введите целое число"
        android:inputType="numberSigned"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="number" />

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Реверс числа"
        app:layout_constraintTop_toBottomOf="@id/editTextIntegerInput"
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

    private EditText editTextIntegerInput;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextIntegerInput = findViewById(R.id.editTextIntegerInput);
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
                reverseNumberDigits();
            }
        });
    }

    private void reverseNumberDigits() {
        String strInput = editTextIntegerInput.getText().toString();

        if (strInput.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите число.");
            return;
        }

        // Проверка на наличие только знака "-"
        if (strInput.equals("-")) {
            textViewResult.setText("Результат: Пожалуйста, введите корректное целое число.");
            return;
        }

        try {
            long number = Long.parseLong(strInput);
            long originalNumber = number; // Сохраняем для вывода
            long reversedNumber = 0;
            boolean isNegative = false;

            if (number == 0) {
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: Реверс числа %d = 0", originalNumber));
                return;
            }

            if (number < 0) {
                isNegative = true;
                number = -number; // Работаем с положительным числом для реверса
            }

            while (number > 0) {
                long digit = number % 10;
                // Проверка на переполнение перед умножением
                if (reversedNumber > (Long.MAX_VALUE - digit) / 10) {
                    textViewResult.setText("Результат: Переполнение при реверсе числа.");
                    return;
                }
                reversedNumber = reversedNumber * 10 + digit;
                number /= 10;
            }

            if (isNegative) {
                reversedNumber = -reversedNumber;
            }

            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Реверс числа %d = %d", originalNumber, reversedNumber));

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректное целое число.");
        }
    }
}
```

## Задание 10: Сортировка пузырьком

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
        android:text="Задание 10: Сортировка пузырьком"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextArrayInput"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Введите числа через запятую (напр., 5,1,4,2,8)"
        android:inputType="text"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="numbers" />

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Сортировать"
        app:layout_constraintTop_toBottomOf="@id/editTextArrayInput"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"/>

    <TextView
        android:id="@+id/textViewResult"
        android:layout_width="0dp"
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextArrayInput;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextArrayInput = findViewById(R.id.editTextArrayInput);
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
                performBubbleSort();
            }
        });
    }

    private void performBubbleSort() {
        String inputText = editTextArrayInput.getText().toString();
        if (inputText.isEmpty()) {
            textViewResult.setText("Результат: Поле ввода пустое. Введите числа.");
            return;
        }

        String[] stringArray = inputText.split("[,\\s]+"); // Разделение по запятым и/или пробелам
        if (stringArray.length == 0 || (stringArray.length == 1 && stringArray[0].isEmpty())) {
             textViewResult.setText("Результат: Введите числа для сортировки.");
            return;
        }

        List<Integer> numbersList = new ArrayList<>();
        for (String s : stringArray) {
            if (s.trim().isEmpty()) continue; // Пропускаем пустые строки после разделения
            try {
                numbersList.add(Integer.parseInt(s.trim()));
            } catch (NumberFormatException e) {
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: Ошибка в числе '%s'. Введите корректные целые числа через запятую или пробел.", s));
                return;
            }
        }

        if (numbersList.isEmpty()) {
            textViewResult.setText("Результат: Не найдено чисел для сортировки.");
            return;
        }

        Integer[] numbers = numbersList.toArray(new Integer[0]);

        // Алгоритм сортировки пузырьком
        int n = numbers.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    // Обмен значениями
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                    swapped = true;
                }
            }
            // Если во внутреннем цикле не было обменов, массив уже отсортирован
            if (!swapped) {
                break;
            }
        }

        textViewResult.setText(String.format(Locale.getDefault(),"Результат: %s", Arrays.toString(numbers)));
    }
}
```

## Задание 11: Перестановка первой и последней цифры

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
        android:text="Задание 11: Перестановка первой и последней цифры"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextIntegerInput"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Введите целое число"
        android:inputType="numberSigned"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="number" />

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Переставить цифры"
        app:layout_constraintTop_toBottomOf="@id/editTextIntegerInput"
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

    private EditText editTextIntegerInput;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextIntegerInput = findViewById(R.id.editTextIntegerInput);
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
                swapFirstAndLastDigits();
            }
        });
    }

    private void swapFirstAndLastDigits() {
        String strInput = editTextIntegerInput.getText().toString();

        if (strInput.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите число.");
            return;
        }
        if (strInput.equals("-")) {
            textViewResult.setText("Результат: Пожалуйста, введите корректное целое число.");
            return;
        }

        try {
            long number = Long.parseLong(strInput);
            long originalNumber = number;
            boolean isNegative = false;

            if (number < 0) {
                isNegative = true;
                number = -number; // Работаем с положительным числом
            }

            if (number < 10) { // Однозначные числа (включая 0, если не было знака минус) не меняются
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: %d (не изменилось)", originalNumber));
                return;
            }

            long lastDigit = number % 10;
            long powerOf10 = 1;
            long temp = number;
            while (temp >= 10) {
                temp /= 10;
                powerOf10 *= 10;
            }
            long firstDigit = number / powerOf10;

            long middlePartWithLastDigit = number % powerOf10;
            long middlePart = middlePartWithLastDigit / 10;

            long newNumber = lastDigit * powerOf10 + middlePart * 10 + firstDigit;

            if (isNegative) {
                newNumber = -newNumber;
            }

            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Исходное: %d, Новое: %d", originalNumber, newNumber));

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректное целое число.");
        }
    }
}
```

## Задание 12: Числа Фибоначчи

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
        android:text="Задание 12: Числа Фибоначчи"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextFibonacciCount"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Количество чисел Фибоначчи (N)"
        android:inputType="number"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="number" />

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Сгенерировать"
        app:layout_constraintTop_toBottomOf="@id/editTextFibonacciCount"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"/>

    <TextView
        android:id="@+id/textViewResult"
        android:layout_width="0dp"
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextFibonacciCount;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextFibonacciCount = findViewById(R.id.editTextFibonacciCount);
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
                generateFibonacciSequence();
            }
        });
    }

    private void generateFibonacciSequence() {
        String strN = editTextFibonacciCount.getText().toString();

        if (strN.isEmpty()) {
            textViewResult.setText("Результат: Пожалуйста, введите количество чисел.");
            return;
        }

        try {
            int n = Integer.parseInt(strN);

            if (n < 0) {
                textViewResult.setText("Результат: Количество чисел не может быть отрицательным.");
                return;
            }
            if (n == 0) {
                textViewResult.setText("Результат: Последовательность пуста (0 чисел).");
                return;
            }
            // Ограничение, чтобы избежать слишком больших чисел и долгого вывода
            // Число Фибоначчи F(93) уже превышает Long.MAX_VALUE
            if (n > 92) {
                textViewResult.setText("Результат: Слишком большое N. Пожалуйста, введите N <= 92, чтобы избежать переполнения long.");
                return;
            }


            List<Long> fibonacciSequence = new ArrayList<>();
            if (n >= 1) {
                fibonacciSequence.add(0L);
            }
            if (n >= 2) {
                fibonacciSequence.add(1L);
            }

            for (int i = 2; i < n; i++) {
                long nextFib = fibonacciSequence.get(i - 1) + fibonacciSequence.get(i - 2);
                fibonacciSequence.add(nextFib);
            }

            StringBuilder sb = new StringBuilder("Результат: ");
            for (int i = 0; i < fibonacciSequence.size(); i++) {
                sb.append(fibonacciSequence.get(i));
                if (i < fibonacciSequence.size() - 1) {
                    sb.append(", ");
                }
            }
            textViewResult.setText(sb.toString());

        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Пожалуйста, введите корректное целое число для N.");
        }
    }
}
```

## Задание 13: Бинарный поиск

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
        android:text="Задание 13: Бинарный поиск"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextSortedArrayInput"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Отсортированный массив (напр., 1,2,5,8,12)"
        android:inputType="text"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="numbers" />

    <EditText
        android:id="@+id/editTextSearchElement"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Искомый элемент"
        android:inputType="numberSigned"
        app:layout_constraintTop_toBottomOf="@id/editTextSortedArrayInput"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Найти элемент"
        app:layout_constraintTop_toBottomOf="@id/editTextSearchElement"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"/>

    <TextView
        android:id="@+id/textViewResult"
        android:layout_width="0dp"
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextSortedArrayInput;
    private EditText editTextSearchElement;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextSortedArrayInput = findViewById(R.id.editTextSortedArrayInput);
        editTextSearchElement = findViewById(R.id.editTextSearchElement);
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
                performBinarySearch();
            }
        });
    }

    private void performBinarySearch() {
        String arrayText = editTextSortedArrayInput.getText().toString();
        String elementText = editTextSearchElement.getText().toString();

        if (arrayText.isEmpty()) {
            textViewResult.setText("Результат: Поле для массива пустое. Введите отсортированный массив.");
            return;
        }
        if (elementText.isEmpty()) {
            textViewResult.setText("Результат: Поле для искомого элемента пустое. Введите элемент.");
            return;
        }

        String[] stringArray = arrayText.split("[,\\s]+");
        if (stringArray.length == 0 || (stringArray.length == 1 && stringArray[0].isEmpty())) {
            textViewResult.setText("Результат: Введите числа для массива.");
            return;
        }

        List<Integer> numbersList = new ArrayList<>();
        for (String s : stringArray) {
            if (s.trim().isEmpty()) continue;
            try {
                numbersList.add(Integer.parseInt(s.trim()));
            } catch (NumberFormatException e) {
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: Ошибка в массиве: '%s'. Введите корректные целые числа.", s));
                return;
            }
        }

        if (numbersList.isEmpty()) {
            textViewResult.setText("Результат: Массив не содержит чисел.");
            return;
        }

        Integer[] numbers = numbersList.toArray(new Integer[0]);
        int searchElement;
        try {
            searchElement = Integer.parseInt(elementText.trim());
        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Искомый элемент введен некорректно.");
            return;
        }

        // Проверка на отсортированность (опционально, но хорошо бы иметь)
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] > numbers[i+1]) {
                textViewResult.setText("Результат: Массив не отсортирован! Бинарный поиск требует отсортированного массива.\nПожалуйста, отсортируйте: " + Arrays.toString(numbers));
                // Можно добавить вызов сортировки, если есть
                // Arrays.sort(numbers); // или вызвать performBubbleSort если он был бы здесь
                // textViewResult.append("\nОтсортированный массив для поиска: " + Arrays.toString(numbers));
                return;
            }
        }


        // Алгоритм бинарного поиска
        int low = 0;
        int high = numbers.length - 1;
        int index = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2; // Чтобы избежать переполнения (low + high) / 2
            if (numbers[mid] == searchElement) {
                index = mid;
                break;
            } else if (numbers[mid] < searchElement) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (index != -1) {
            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Элемент %d найден на позиции %d (индекс).", searchElement, index));
        } else {
            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Элемент %d не найден в массиве.", searchElement));
        }
    }
}
```

## Задание 14: Последовательный поиск

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
        android:text="Задание 14: Последовательный поиск"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextArrayInput"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Массив чисел (напр., 5,1,8,2,9)"
        android:inputType="text"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="numbers" />

    <EditText
        android:id="@+id/editTextSearchElement"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Искомый элемент"
        android:inputType="numberSigned"
        app:layout_constraintTop_toBottomOf="@id/editTextArrayInput"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Найти (послед.)"
        app:layout_constraintTop_toBottomOf="@id/editTextSearchElement"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"/>

    <TextView
        android:id="@+id/textViewResult"
        android:layout_width="0dp"
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextArrayInput;
    private EditText editTextSearchElement;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextArrayInput = findViewById(R.id.editTextArrayInput);
        editTextSearchElement = findViewById(R.id.editTextSearchElement);
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
                performLinearSearch();
            }
        });
    }

    private void performLinearSearch() {
        String arrayText = editTextArrayInput.getText().toString();
        String elementText = editTextSearchElement.getText().toString();

        if (arrayText.isEmpty()) {
            textViewResult.setText("Результат: Поле для массива пустое. Введите массив.");
            return;
        }
        if (elementText.isEmpty()) {
            textViewResult.setText("Результат: Поле для искомого элемента пустое. Введите элемент.");
            return;
        }

        String[] stringArray = arrayText.split("[,\\s]+");
        if (stringArray.length == 0 || (stringArray.length == 1 && stringArray[0].isEmpty())) {
            textViewResult.setText("Результат: Введите числа для массива.");
            return;
        }

        List<Integer> numbersList = new ArrayList<>();
        for (String s : stringArray) {
            if (s.trim().isEmpty()) continue;
            try {
                numbersList.add(Integer.parseInt(s.trim()));
            } catch (NumberFormatException e) {
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: Ошибка в массиве: '%s'. Введите корректные целые числа.", s));
                return;
            }
        }

        if (numbersList.isEmpty()) {
            textViewResult.setText("Результат: Массив не содержит чисел.");
            return;
        }

        Integer[] numbers = numbersList.toArray(new Integer[0]);
        int searchElement;
        try {
            searchElement = Integer.parseInt(elementText.trim());
        } catch (NumberFormatException e) {
            textViewResult.setText("Результат: Искомый элемент введен некорректно.");
            return;
        }

        // Алгоритм последовательного поиска
        int index = -1;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == searchElement) {
                index = i; // Найдено первое вхождение
                break;
            }
        }

        if (index != -1) {
            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Элемент %d найден на позиции %d (индекс).", searchElement, index));
        } else {
            textViewResult.setText(String.format(Locale.getDefault(), "Результат: Элемент %d не найден в массиве.", searchElement));
        }
    }
}
```

## Задание 15: Сортировка вставкой

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
        android:text="Задание 15: Сортировка вставкой"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextArrayInput"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Массив (напр., 5,1,4,2,8)"
        android:inputType="text"
        app:layout_constraintTop_toBottomOf="@id/textViewTitle"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"
        android:autofillHints="numbers" />

    <!-- Удаляем editTextSearchElement, если он был от предыдущего задания -->

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Сортировать вставкой"
        app:layout_constraintTop_toBottomOf="@id/editTextArrayInput"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"/>

    <TextView
        android:id="@+id/textViewResult"
        android:layout_width="0dp"
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextArrayInput;
    // private EditText editTextSearchElement; // Удален, не нужен для сортировки
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        editTextArrayInput = findViewById(R.id.editTextArrayInput);
        // editTextSearchElement = findViewById(R.id.editTextSearchElement); // Удален
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
                performInsertionSort();
            }
        });
    }

    private void performInsertionSort() {
        String inputText = editTextArrayInput.getText().toString();
        if (inputText.isEmpty()) {
            textViewResult.setText("Результат: Поле ввода пустое. Введите числа.");
            return;
        }

        String[] stringArray = inputText.split("[,\\s]+");
        if (stringArray.length == 0 || (stringArray.length == 1 && stringArray[0].isEmpty())) {
            textViewResult.setText("Результат: Введите числа для сортировки.");
            return;
        }

        List<Integer> numbersList = new ArrayList<>();
        for (String s : stringArray) {
            if (s.trim().isEmpty()) continue;
            try {
                numbersList.add(Integer.parseInt(s.trim()));
            } catch (NumberFormatException e) {
                textViewResult.setText(String.format(Locale.getDefault(), "Результат: Ошибка в числе '%s'. Введите корректные целые числа.", s));
                return;
            }
        }

        if (numbersList.isEmpty()) {
            textViewResult.setText("Результат: Не найдено чисел для сортировки.");
            return;
        }

        Integer[] numbers = numbersList.toArray(new Integer[0]);

        // Алгоритм сортировки вставкой
        for (int i = 1; i < numbers.length; i++) {
            int key = numbers[i];
            int j = i - 1;

            // Перемещаем элементы numbers[0..i-1], которые больше key,
            // на одну позицию вперед их текущей позиции
            while (j >= 0 && numbers[j] > key) {
                numbers[j + 1] = numbers[j];
                j = j - 1;
            }
            numbers[j + 1] = key;
        }

        textViewResult.setText(String.format(Locale.getDefault(),"Результат: %s", Arrays.toString(numbers)));
    }
}
```

## Задание 16: Площадь треугольника (2 стороны, угол)

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
        android:text="Задание 16: Площадь треугольника (2 стороны, угол)"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/editTextSideA_Task16"
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
        android:id="@+id/editTextSideB_Task16"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Сторона b"
        android:inputType="numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/editTextSideA_Task16"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <EditText
        android:id="@+id/editTextAngleDegrees"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Угол γ (в градусах)"
        android:inputType="numberDecimal"
        app:layout_constraintTop_toBottomOf="@id/editTextSideB_Task16"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"
        android:autofillHints="number" />

    <Button
        android:id="@+id/buttonCalculate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Вычислить площадь"
        app:layout_constraintTop_toBottomOf="@id/editTextAngleDegrees"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="24dp"/>

    <TextView
        android:id="@+id/textViewResult"
        android:layout_width="0dp"
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
```
