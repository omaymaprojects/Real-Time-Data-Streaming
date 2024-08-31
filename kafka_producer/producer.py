from kafka import KafkaProducer
import json
import random
import schedule
import time
from faker import Faker

faker = Faker()

producer = KafkaProducer(
    bootstrap_servers=['kafka:9092'],
    value_serializer=lambda v: json.dumps(v).encode('utf-8')
)

def generate_data():
    data = {
        'city': faker.city(),
        'temperature': random.uniform(10.0, 110.0)
    }
    producer.send('weather', value=data)
    producer.flush()

schedule.every(10).seconds.do(generate_data)

while True:
    schedule.run_pending()
    time.sleep(5)
