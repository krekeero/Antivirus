package com.example.antivirusapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class AntivirusViewModel : ViewModel() {
    
    private val _scanProgress = MutableLiveData<Int>()
    val scanProgress: LiveData<Int> = _scanProgress
    
    private val _isScanning = MutableLiveData<Boolean>()
    val isScanning: LiveData<Boolean> = _isScanning
    
    private val _threatsFound = MutableLiveData<Int>()
    val threatsFound: LiveData<Int> = _threatsFound
    
    private val _lastScanTime = MutableLiveData<String>()
    val lastScanTime: LiveData<String> = _lastScanTime
    
    private val _databaseVersion = MutableLiveData<String>()
    val databaseVersion: LiveData<String> = _databaseVersion
    
    private var scanJob: Job? = null
    
    init {
        _threatsFound.value = 0
        _lastScanTime.value = "Никогда"
        _databaseVersion.value = "2024.1.0"
    }
    
    fun startScan() {
        if (_isScanning.value == true) return
        
        _isScanning.value = true
        _scanProgress.value = 0
        
        scanJob = CoroutineScope(Dispatchers.Main).launch {
            // Имитация сканирования
            for (i in 0..100 step 2) {
                delay(100) // Задержка для имитации работы
                _scanProgress.value = i
            }
            
            // Завершение сканирования
            _isScanning.value = false
            _scanProgress.value = 100
            
            // Имитация случайного количества угроз
            val randomThreats = (0..3).random()
            _threatsFound.value = randomThreats
            
            // Обновление времени последнего сканирования
            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
            _lastScanTime.value = sdf.format(Date())
            
            // Сброс прогресса
            delay(1000)
            _scanProgress.value = 0
        }
    }
    
    fun updateVirusDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            // Имитация обновления базы данных
            delay(2000)
            
            // Обновление версии базы данных
            val currentVersion = _databaseVersion.value ?: "2024.1.0"
            val versionParts = currentVersion.split(".")
            val newMinor = (versionParts[2].toInt() + 1).toString()
            _databaseVersion.value = "${versionParts[0]}.${versionParts[1]}.$newMinor"
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        scanJob?.cancel()
    }
}