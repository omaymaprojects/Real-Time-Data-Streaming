FROM python:3.9-slim

WORKDIR /app

COPY requirements.txt requirements.txt

# Upgrade pip before installing dependencies
RUN pip install --upgrade pip

# Install the dependencies from requirements.txt
RUN pip install -r requirements.txt

COPY . .

# Use PowerShell as the default shell to run the script
SHELL ["pwsh", "-Command"]

# Execute the PowerShell script to wait for Kafka and then run the producer
CMD ["./wait_for_it.ps1", "kafka", "python producer.py"]

