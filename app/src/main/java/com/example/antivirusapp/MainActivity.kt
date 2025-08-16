package com.example.antivirusapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.antivirusapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: AntivirusViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel = ViewModelProvider(this)[AntivirusViewModel::class.java]
        
        setupUI()
        observeViewModel()
    }
    
    private fun setupUI() {
        // Настройка кнопки сканирования
        binding.btnScan.setOnClickListener {
            viewModel.startScan()
        }
        
        // Настройка кнопки обновления
        binding.btnUpdate.setOnClickListener {
            viewModel.updateVirusDatabase()
        }
        
        // Настройка кнопки настроек
        binding.btnSettings.setOnClickListener {
            Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
        }
        
        // Настройка кнопки информации
        binding.btnInfo.setOnClickListener {
            Toast.makeText(this, "Информация о приложении", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun observeViewModel() {
        viewModel.scanProgress.observe(this) { progress ->
            binding.progressBar.progress = progress
            binding.tvScanStatus.text = "Сканирование... $progress%"
        }
        
        viewModel.isScanning.observe(this) { isScanning ->
            binding.btnScan.isEnabled = !isScanning
            binding.progressBar.visibility = if (isScanning) View.VISIBLE else View.GONE
            binding.tvScanStatus.visibility = if (isScanning) View.VISIBLE else View.GONE
        }
        
        viewModel.threatsFound.observe(this) { threats ->
            binding.tvThreatsCount.text = "Угроз: $threats"
            binding.tvThreatsCount.setTextColor(
                if (threats > 0) getColor(R.color.orange) else getColor(R.color.green)
            )
        }
        
        viewModel.lastScanTime.observe(this) { time ->
            binding.tvLastScan.text = "Последнее сканирование: $time"
        }
        
        viewModel.databaseVersion.observe(this) { version ->
            binding.tvDatabaseVersion.text = "База данных: v$version"
        }
    }
}