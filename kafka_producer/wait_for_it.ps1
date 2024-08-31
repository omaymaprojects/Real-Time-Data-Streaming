param (
    [string]$host,
    [int]$port = 9092,
    [int]$timeout = 30
)

$endtime = (Get-Date).AddSeconds($timeout)

while ((Get-Date) -lt $endtime) {
    try {
        $tcpClient = New-Object System.Net.Sockets.TcpClient
        $tcpClient.Connect($host, $port)
        if ($tcpClient.Connected) {
            Write-Host "Kafka is up - executing command"
            exit 0
        }
    } catch {
        Write-Host "Kafka is unavailable - sleeping"
        Start-Sleep -Seconds 1
    }
}

Write-Host "Timed out waiting for Kafka to start"
exit 1
