# Comprueba si ya existe el entorno virtual
if (!(Test-Path -Path "./venv")) {
    Write-Host "Creando entorno virtual..."
    python -m venv venv
}

# Activa el entorno virtual
Write-Host "Activando entorno virtual..."
. .\venv\Scripts\Activate.ps1

# Instala las dependencias
Write-Host "Instalando dependencias desde requirements.txt..."
python -m pip install --upgrade pip
pip install -r requirements.txt

Write-Host "Añadiendo variables de entorno..."
# Cargar variables de entorno

# Ejecutar la aplicación
Write-Host "Ejecutando app.py..."
locust -f app.py --host http://localhost:8080 --users 1 --spawn-rate 10 --run-time 1m --profile dev --headless