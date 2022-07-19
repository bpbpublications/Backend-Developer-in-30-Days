import time
import random

records = list()
index = list()

def measure(message, func):
  start = time.time()
  record = func()
  end = time.time()

  print("\n{}: \t{:.3f}ms".format(message, (end-start) *1000))
  print(record)

def insert(price):
  product_name = "Product"+str(random.randint(0,100000))
  records.append({"name":product_name, "price":price})
  index.append((price, len(records)-1))

def linear_search(arr, value):
  for el in arr:
    if el["price"] == value:
      return el


def binary_search(arr, value, i=0, j=-1):
  if j == -1:
    j = len(arr)
  mid = round(((j - i) / 2) + i)

  prev_price, prev_ind = arr[mid-1]
  cur_price, cur_ind = arr[mid]
  next_price, next_ind = arr[mid+1]

  if cur_price == value and prev_price < value:
    return records[cur_ind]
  elif mid == j and prev_price < value < cur_price:
    return  None
  elif mid == j and cur_price < value < next_price:
    return None
  elif cur_price < value:
    return binary_search(arr, value, mid+1, j)
  else:
    return binary_search(arr, value, i, mid-1)

for i in range(int(1e6)):
  product_price = random.uniform(100, 1000)
  insert(product_price)

insert(120)
insert(120)

index.sort()

measure("Query time without index:", lambda: linear_search(records, 120))
measure("Query time with index:", lambda: binary_search(index, 120))