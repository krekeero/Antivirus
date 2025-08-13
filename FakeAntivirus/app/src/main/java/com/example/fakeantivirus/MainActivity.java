package com.example.fakeantivirus;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    
    private TextView statusText;
    private TextView threatCountText;
    private TextView scanProgressText;
    private ProgressBar scanProgressBar;
    private Button scanButton;
    private Button quickScanButton;
    private Button fullScanButton;
    private CardView statusCard;
    private Handler handler;
    private Random random;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Инициализация переменных
        handler = new Handler(Looper.getMainLooper());
        random = new Random();
        
        // Инициализация UI элементов
        initViews();
        setupClickListeners();
        updateStatus("Система защищена", "green");
    }
    
    private void initViews() {
        statusText = findViewById(R.id.statusText);
        threatCountText = findViewById(R.id.threatCountText);
        scanProgressText = findViewById(R.id.scanProgressText);
        scanProgressBar = findViewById(R.id.scanProgressBar);
        scanButton = findViewById(R.id.scanButton);
        quickScanButton = findViewById(R.id.quickScanButton);
        fullScanButton = findViewById(R.id.fullScanButton);
        statusCard = findViewById(R.id.statusCard);
    }
    
    private void setupClickListeners() {
        scanButton.setOnClickListener(v -> startQuickScan());
        quickScanButton.setOnClickListener(v -> startQuickScan());
        fullScanButton.setOnClickListener(v -> startFullScan());
    }
    
    private void startQuickScan() {
        scanButton.setEnabled(false);
        quickScanButton.setEnabled(false);
        fullScanButton.setEnabled(false);
        
        updateStatus("Быстрое сканирование...", "orange");
        scanProgressBar.setVisibility(View.VISIBLE);
        scanProgressText.setVisibility(View.VISIBLE);
        
        // Имитация быстрого сканирования
        simulateScan(5000, "Быстрое сканирование завершено");
    }
    
    private void startFullScan() {
        scanButton.setEnabled(false);
        quickScanButton.setEnabled(false);
        fullScanButton.setEnabled(false);
        
        updateStatus("Полное сканирование...", "orange");
        scanProgressBar.setVisibility(View.VISIBLE);
        scanProgressText.setVisibility(View.VISIBLE);
        
        // Имитация полного сканирования
        simulateScan(15000, "Полное сканирование завершено");
    }
    
    private void simulateScan(int duration, String completionMessage) {
        final int totalSteps = 100;
        final int stepDelay = duration / totalSteps;
        
        scanProgressBar.setMax(totalSteps);
        scanProgressBar.setProgress(0);
        
        for (int i = 0; i <= totalSteps; i++) {
            final int progress = i;
            handler.postDelayed(() -> {
                scanProgressBar.setProgress(progress);
                scanProgressText.setText("Сканирование: " + progress + "%");
                
                if (progress == totalSteps) {
                    // Сканирование завершено
                    scanProgressBar.setVisibility(View.INVISIBLE);
                    scanProgressText.setVisibility(View.INVISIBLE);
                    
                    // Показываем случайные результаты
                    int threats = random.nextInt(3);
                    if (threats == 0) {
                        updateStatus("Угроз не обнаружено", "green");
                        threatCountText.setText("Угроз: 0");
                        Toast.makeText(MainActivity.this, "Система безопасна!", Toast.LENGTH_SHORT).show();
                    } else {
                        updateStatus("Обнаружены угрозы!", "red");
                        threatCountText.setText("Угроз: " + threats);
                        Toast.makeText(MainActivity.this, "Обнаружено " + threats + " угроз!", Toast.LENGTH_SHORT).show();
                    }
                    
                    // Восстанавливаем кнопки
                    scanButton.setEnabled(true);
                    quickScanButton.setEnabled(true);
                    fullScanButton.setEnabled(true);
                }
            }, i * stepDelay);
        }
    }
    
    private void updateStatus(String status, String color) {
        statusText.setText(status);
        
        // Изменяем цвет карточки статуса
        int backgroundColor;
        switch (color) {
            case "green":
                backgroundColor = getResources().getColor(android.R.color.holo_green_dark);
                break;
            case "orange":
                backgroundColor = getResources().getColor(android.R.color.holo_orange_dark);
                break;
            case "red":
                backgroundColor = getResources().getColor(android.R.color.holo_red_dark);
                break;
            default:
                backgroundColor = getResources().getColor(android.R.color.holo_green_dark);
        }
        
        statusCard.setCardBackgroundColor(backgroundColor);
    }
}