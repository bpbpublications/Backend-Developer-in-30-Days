import sqlite3
import time
import random
import threading
import matplotlib.pyplot as plt

def insert_data(con, cur, times):
  con = sqlite3.connect('example.db')

  cur = con.cursor()
  try:
    for i in range(times):
      product_name = "Product"+str(random.randint(0,100000))
      product_price = random.uniform(100, 1000)
      cur.execute("INSERT INTO products VALUES ('{}', {})".format(product_name, product_price))
      cur.execute("INSERT INTO indexed_products VALUES ('{}', {})".format(product_name, product_price))

  except:
    print("Error inserting records")
  con.commit()
  cur.close()
  con.close()


def measure_query(query, message=""):
  con = sqlite3.connect('example.db')

  cur = con.cursor()
  start = time.time()
  results = cur.execute(query)
  end = time.time()

  cur.close()
  con.close()

  #for result in results:
  #  print(result)

  print(message + str(round((end - start) * 1000, 4)) + "ms")

  return (end - start) * 1000

con = sqlite3.connect('example.db')

cur = con.cursor()

cur.execute("DROP TABLE IF EXISTS products")
cur.execute("DROP TABLE IF EXISTS indexed_products")
# Create table
cur.execute('''CREATE TABLE IF NOT EXISTS products
               (title text, price real)''')
cur.execute('''CREATE TABLE IF NOT EXISTS indexed_products
               (title text, price real)''')
cur.execute("CREATE INDEX IF NOT EXISTS price_index ON indexed_products(price)")
cur.close()
con.close()

threads = list()
non_indexed = list()
indexed = list()

iterations = 100
records_per_iteration = 1000


for i in range(iterations):
  print("Inserting records {}/{}".format(i, iterations))
  for j in range(6):
    x = threading.Thread(target=insert_data, args=(con, cur, records_per_iteration))
    threads.append(x)
    x.start()

  for index, thread in enumerate(threads):
      thread.join()

  print("=== Query times: ===")
  ni = measure_query("select * from products p where p.price = 100", "\tWithout index\t")
  non_indexed.append(ni)

  ind = measure_query("select * from indexed_products p where p.price = 100", "\tWith index\t")
  indexed.append(ind)
# We can also close the connection if we are done with it.
# Just be sure any changes have been committed or they will be lost.
plt.plot(non_indexed, label="Non-indexed")
plt.plot(indexed, label="Indexed")
plt.ylabel('Query time (ms)')
plt.xlabel("Records (x{})".format(records_per_iteration))
plt.legend()
plt.show(block=True)