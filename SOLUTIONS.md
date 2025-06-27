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
