import csv
import random
from faker import Faker
from datetime import datetime, timedelta

fake = Faker()

def generate_short_name(product_name):
    if len(product_name) <= 4:
        return product_name
    else:
        return ''.join(word[0] for word in product_name.split())

def generate_product_price():
    return round(random.uniform(1.00, 99999999.99), 2)

with open('products.csv', mode='w', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(["productName", "shortName", "productPrice"])
    for _ in range(300):
        product_name = fake.unique.word()
        short_name = generate_short_name(product_name)
        product_price = generate_product_price()

        writer.writerow([product_name, short_name, product_price])

print("CSV file generated successfully!")

